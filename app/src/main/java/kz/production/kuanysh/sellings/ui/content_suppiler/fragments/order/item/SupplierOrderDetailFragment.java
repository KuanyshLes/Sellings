package kz.production.kuanysh.sellings.ui.content_suppiler.fragments.order.item;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kz.production.kuanysh.sellings.R;
import kz.production.kuanysh.sellings.data.network.model.supplier.orderdetail.Result;
import kz.production.kuanysh.sellings.di.component.ActivityComponent;
import kz.production.kuanysh.sellings.ui.base.BaseFragment;
import kz.production.kuanysh.sellings.ui.content_owner.utils.Colors;
import kz.production.kuanysh.sellings.ui.content_suppiler.fragments.order.all.SupplierOrdersFragment;
import kz.production.kuanysh.sellings.ui.content_suppiler.utils.adapters.SupplierOrderDetailAdapter;
import kz.production.kuanysh.sellings.utils.AppConstants;

import static kz.production.kuanysh.sellings.ui.content_suppiler.activity.SupplierActivity.SUPPLIER_TAG_MAIN;

/**
 * A simple {@link Fragment} subclass.
 */
public class SupplierOrderDetailFragment extends BaseFragment implements SupplierOrderDetailMvpView{

    @Inject
    SupplierOrderDetailPresenter<SupplierOrderDetailMvpView> mPresenter;

    @BindView(R.id.supplier_order_detail_name)
    TextView name;

    @BindView(R.id.supplier_order_detail_status)
    TextView status;

    @BindView(R.id.supplier_order_detail_cash)
    TextView cash;

    @BindView(R.id.supplier_order_detail_consignment)
    TextView consignment;

    @BindView(R.id.supplier_order_detail_cancell_btn)
    Button cancell;

    @BindView(R.id.supplier_order_detail_all_price)
    TextView price;

    @BindView(R.id.supplier_order_detail_approve_btn)
    Button approve;

    @BindView(R.id.supplier_order_detail_list)
    RecyclerView orders;

    @BindView(R.id.supplier_detail_orders_toolbar_drawer)
    ImageView back;

    @Inject
    SupplierOrderDetailAdapter supplierOrderDetailAdapter;

    private LinearLayoutManager linearLayoutManager;
    private static int ORDER_ID;
    private static String ORDER_OWNER_NAME;
    private static int ORDER_ALL_PRICE;
    private static Result result;
    private static List<kz.production.kuanysh.sellings.data.network.model.supplier.orderdetail.Product> productList;

    public SupplierOrderDetailFragment() {
        // Required empty public constructor
    }
    public static SupplierOrderDetailFragment newInstance() {
        Bundle args = new Bundle();
        SupplierOrderDetailFragment fragment = new SupplierOrderDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_supplier_order_detail, container, false);

        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            setUnBinder(ButterKnife.bind(this, view));
            mPresenter.onAttach(this);
        }

        final Bundle bundle = getArguments();
        if (bundle != null){
            ORDER_ID = bundle.getInt(SupplierOrdersFragment.TAG_ORDER_DETAIL);
            ORDER_OWNER_NAME = bundle.getString(SupplierOrdersFragment.TAG_ORDER_DETAIL_NAME);
        }



        return view;

    }


    @OnClick(R.id.supplier_order_detail_approve_btn)
    public void setApprove(){
        mPresenter.onStatusClick(ORDER_ID,AppConstants.SUPPLIER_ORDER_STATUS_CONFIRM);
    }

    @OnClick(R.id.supplier_order_detail_cancell_btn)
    public void setCancell(){
        mPresenter.onStatusClick(ORDER_ID,AppConstants.SUPPLIER_ORDER_STATUS_CANCELL);
    }



    @OnClick(R.id.supplier_detail_orders_toolbar_drawer)
    public void goBackSuppplierOrderDetail(){
        mPresenter.onBackClick();
    }


    @Override
    public void openOrders() {
        getActivity().getSupportFragmentManager().popBackStack();
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.supplier_content_frame, SupplierOrdersFragment.newInstance(), SUPPLIER_TAG_MAIN)
                .commit();

    }

    @Override
    public void updateOrder(Result resultCome) {
        result=resultCome;
        productList.addAll(result.getProducts());

        name.setText(ORDER_OWNER_NAME.replace("\"",""));
        cash.setText(result.getPaymentType());

        if(result.getConsignment()!=null){
            if(Integer.valueOf(result.getConsignment())== AppConstants.CONSIGNMENT_NO){
                consignment.setText("");
            }else if(Integer.valueOf(result.getConsignment())== AppConstants.CONSIGNMENT_YES){
                consignment.setText(AppConstants.CONSIGNMENT_TEXT);
                consignment.setTextColor(Color.parseColor(Colors.CANCELLED_RED));
            }
        }

        if(result.getStatus().equals(AppConstants.SUPPLIER_ORDER_STATUS_CONFIRM)){
            status.setTextColor(Color.parseColor(Colors.CONFIRMED_GREEN));
            status.setText(AppConstants.SUPPLIER_ORDER_STATUS_CONFIRM_TEXT);
        }else if (result.getStatus().equals(AppConstants.SUPPLIER_ORDER_STATUS_CANCELL)){
            status.setTextColor(Color.parseColor(Colors.CANCELLED_RED));
            status.setText(AppConstants.SUPPLIER_ORDER_STATUS_CANCELL_TEXT);
        }else{
            cancell.setVisibility(View.VISIBLE);
            approve.setVisibility(View.VISIBLE);
            cancell.setEnabled(true);
            approve.setEnabled(true);
            status.setText(AppConstants.SUPPLIER_ORDER_STATUS_WAITING_TEXT);
        }

        supplierOrderDetailAdapter.addItems(productList);

        for(int i=00;i<productList.size();i++){
            ORDER_ALL_PRICE +=Integer.valueOf(productList.get(i).getNumberOfStock())*Integer.valueOf(productList.get(i).getPrice());
        }
        price.setText(ORDER_ALL_PRICE+AppConstants.MONEY_TYPE);
    }

    @Override
    protected void setUp(View view) {
        result=new Result();
        productList=new ArrayList<>();
        linearLayoutManager=new LinearLayoutManager(getActivity());
        orders.setLayoutManager(linearLayoutManager);
        orders.setAdapter(supplierOrderDetailAdapter);

        mPresenter.onViewPrepared(ORDER_ID);



    }

    @Override
    public void onDestroyView() {
        mPresenter.onDetach();
        super.onDestroyView();
    }
}
