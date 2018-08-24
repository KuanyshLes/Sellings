package kz.production.kuanysh.sellings.ui.content_suppiler.fragments.consignment.all;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
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
import kz.production.kuanysh.sellings.data.model.ConsignmentItem;
import kz.production.kuanysh.sellings.data.network.model.supplier.consignment.Result;
import kz.production.kuanysh.sellings.di.component.ActivityComponent;
import kz.production.kuanysh.sellings.ui.base.BaseFragment;
import kz.production.kuanysh.sellings.ui.content_owner.utils.Listener;
import kz.production.kuanysh.sellings.ui.content_suppiler.activity.SupplierActivity;
import kz.production.kuanysh.sellings.ui.content_suppiler.fragments.consignment.item.ConsignmentItemControlFragment;
import kz.production.kuanysh.sellings.ui.content_suppiler.fragments.statistics.StatisticsFragment;
import kz.production.kuanysh.sellings.ui.content_suppiler.utils.adapters.SupplierConsignmentAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class SupplierConsignmentFragment extends BaseFragment implements SupplierConsignmentMvpView{

    public final String TAG_FRAGMENT_STACK=getClass().getSimpleName();


    @Inject
    SupplierConsignmentPresenter<SupplierConsignmentMvpView> mPresenter;

    @BindView(R.id.supplier_consignment_toolbar_drawer)
    ImageView back;

    @BindView(R.id.supplier_сonsignment_recycler)
    RecyclerView consignment_recycler;

    @BindView(R.id.id_nullable)
    View nullText;

    @Inject
    SupplierConsignmentAdapter supplierConsignmentAdapter;
    private LinearLayoutManager linearLayoutManager;
    private List<Result> consignmentList;
    private DrawerLayout drawerLayout;
    public static final String KEY_CONSIGNMENT_ID="just key";
    private Bundle bundle;


    public SupplierConsignmentFragment() {
        // Required empty public constructor
    }
    public static SupplierConsignmentFragment newInstance() {
        Bundle args = new Bundle();
        SupplierConsignmentFragment fragment = new SupplierConsignmentFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_supplier_consignment, container, false);

        drawerLayout=(DrawerLayout)getActivity().findViewById(R.id.supplier_drawer_layout);

        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            setUnBinder(ButterKnife.bind(this, view));
            mPresenter.onAttach(this);
        }

        return view;
    }


    @OnClick(R.id.supplier_consignment_toolbar_drawer)
    public void goOpenDrawer(){
        mPresenter.onDrawerClick();
    }

    @Override
    public void openDrawer() {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    @Override
    public void openItem(int position) {
        ConsignmentItemControlFragment consignmentItemControlFragment=new ConsignmentItemControlFragment();
        bundle = new Bundle();
        bundle.putParcelable(KEY_CONSIGNMENT_ID, consignmentList.get(position));
        consignmentItemControlFragment.setArguments(bundle);
        FragmentTransaction transaction= ((AppCompatActivity)getActivity()).getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.supplier_content_frame,consignmentItemControlFragment,
                SupplierActivity.SUPPLIER_VISIBLE_FRAGMENT_TAG)
                .hide(this)
                .addToBackStack(TAG_FRAGMENT_STACK)
                .commit();
    }

    @Override
    public void updateConsignmentList(List<Result> resultList) {
        consignment_recycler.setVisibility(View.VISIBLE);
        nullText.setVisibility(View.GONE);
        consignmentList.addAll(resultList);
        supplierConsignmentAdapter.addItems(consignmentList);

    }

    @Override
    protected void setUp(View view) {
        consignmentList=new ArrayList<>();

        linearLayoutManager=new LinearLayoutManager(getActivity());

        consignment_recycler.setLayoutManager(linearLayoutManager);
        consignment_recycler.setItemAnimator(new DefaultItemAnimator());
        consignment_recycler.setAdapter(supplierConsignmentAdapter);
        supplierConsignmentAdapter.addContext(getActivity());

        supplierConsignmentAdapter.setListener(new Listener() {
            @Override
            public void onClick(int position) {
                mPresenter.onItemClick(position);
            }
        });
        mPresenter.onViewPrepared();
    }

    @Override
    public void onDestroyView() {
        mPresenter.onDetach();
        super.onDestroyView();
    }
}
