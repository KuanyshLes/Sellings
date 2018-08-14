package kz.production.kuanysh.sellings.ui.content_suppiler.fragments.order.all;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kz.production.kuanysh.sellings.R;
import kz.production.kuanysh.sellings.data.model.OwnerItem;
import kz.production.kuanysh.sellings.data.network.model.supplier.orders.Order;
import kz.production.kuanysh.sellings.di.component.ActivityComponent;
import kz.production.kuanysh.sellings.ui.base.BaseFragment;
import kz.production.kuanysh.sellings.ui.content_owner.utils.Listener;
import kz.production.kuanysh.sellings.ui.content_suppiler.fragments.order.item.SupplierOrderDetailFragment;
import kz.production.kuanysh.sellings.ui.content_suppiler.fragments.statistics.StatisticsFragment;
import kz.production.kuanysh.sellings.ui.content_suppiler.utils.adapters.SupplierOrderAdapter;

import static kz.production.kuanysh.sellings.ui.content_suppiler.activity.SupplierActivity.SUPPLIER_TAG_MAIN;
import static kz.production.kuanysh.sellings.ui.content_suppiler.activity.SupplierActivity.SUPPLIER_VISIBLE_FRAGMENT_TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class SupplierOrdersFragment extends BaseFragment implements SupplierOrdersMvpView{


    @Inject
    SupplierOrdersPresenter<SupplierOrdersMvpView> mPresenter;

    @BindView(R.id.supplier_orders_toolbar_drawer)
    ImageView back;

    @BindView(R.id.swipe_supplier_orders)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.supplier_orders)
    RecyclerView supplierOrdersList;

    @Inject
    SupplierOrderAdapter supplierOrderAdapter;

    @BindView(R.id.id_nullable)
    View nullText;

    private LinearLayoutManager linearLayoutManager;
    private static int PAGE_NUMBER=0;

    private static List<Order> orderList;
    private DrawerLayout drawerLayout;
    private List<OwnerItem> ownerItemList;
    private Bundle bundle;
    public static final String TAG_ORDER_DETAIL="key";
    public static final String TAG_ORDER_DETAIL_NAME="keyName";

    public SupplierOrdersFragment() {
        // Required empty public constructor
    }

    public static SupplierOrdersFragment newInstance() {
        Bundle args = new Bundle();
        SupplierOrdersFragment fragment = new SupplierOrdersFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_supplier_orders, container, false);

        drawerLayout=(DrawerLayout)getActivity().findViewById(R.id.supplier_drawer_layout);

        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            setUnBinder(ButterKnife.bind(this, view));
            mPresenter.onAttach(this);
        }

        return view;

    }

    @OnClick(R.id.supplier_orders_toolbar_drawer)
    public void openDrawerSupplierOrders(){
        mPresenter.onDrawerClick();
    }


    @Override
    public void openDrawer() {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    @Override
    public void openItem(int position) {
        SupplierOrderDetailFragment supplierOrderDetailFragment=new SupplierOrderDetailFragment();
        bundle=new Bundle();
        bundle.putInt(TAG_ORDER_DETAIL,orderList.get(position).getId());
        bundle.putString(TAG_ORDER_DETAIL_NAME,orderList.get(position).getTitle());
        supplierOrderDetailFragment.setArguments(bundle);

        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .disallowAddToBackStack()
                .replace(R.id.supplier_content_frame, supplierOrderDetailFragment, SUPPLIER_TAG_MAIN)
                .commit();
    }

    @Override
    public void updateOrders(List<Order> orders) {
        supplierOrdersList.setVisibility(View.VISIBLE);
        nullText.setVisibility(View.GONE);
        swipeRefreshLayout.setRefreshing(false);
        orderList.addAll(orders);
        supplierOrderAdapter.addItems(orderList);
    }

    @Override
    protected void setUp(View view) {
        orderList=new ArrayList<>();
        linearLayoutManager=new LinearLayoutManager(getActivity());
        supplierOrdersList=(RecyclerView)view.findViewById(R.id.supplier_orders);
        supplierOrdersList.setAdapter(supplierOrderAdapter);
        supplierOrdersList.setLayoutManager(linearLayoutManager);

        supplierOrderAdapter.setListener(new Listener() {
            @Override
            public void onClick(int position) {
                mPresenter.onItemClick(position);

            }
        });
        swipeRefreshLayout.setRefreshing(true);
        mPresenter.onViewPrepared(PAGE_NUMBER);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                orderList.clear();
                supplierOrderAdapter.notifyDataSetChanged();
                mPresenter.onViewPrepared(0);
            }
        });
    }

    @Override
    public void onDestroyView() {
        mPresenter.onDetach();
        super.onDestroyView();
    }
}


