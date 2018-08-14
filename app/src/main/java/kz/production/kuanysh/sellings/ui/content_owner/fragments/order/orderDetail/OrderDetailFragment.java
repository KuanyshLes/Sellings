package kz.production.kuanysh.sellings.ui.content_owner.fragments.order.orderDetail;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kz.production.kuanysh.sellings.R;
import kz.production.kuanysh.sellings.data.model.OrderItem;
import kz.production.kuanysh.sellings.data.model.Product;
import kz.production.kuanysh.sellings.data.network.model.owner.orderdetail.Result;
import kz.production.kuanysh.sellings.di.component.ActivityComponent;
import kz.production.kuanysh.sellings.ui.base.BaseFragment;
import kz.production.kuanysh.sellings.ui.content_owner.fragments.order.orders.OwnerOrdersFragment;
import kz.production.kuanysh.sellings.ui.content_owner.utils.Colors;
import kz.production.kuanysh.sellings.ui.content_owner.utils.adapters.OrderItemsAdapter;
import kz.production.kuanysh.sellings.utils.AppConstants;

import static kz.production.kuanysh.sellings.ui.content_owner.main.MainActivity.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderDetailFragment extends BaseFragment implements OrderDetailMvpView {

    @Inject
    OrderDetailPresenter<OrderDetailMvpView> mPresenter;

    @BindView(R.id.order_detail_name)
    TextView name;

    @BindView(R.id.orders_detail_toolbar_title)
    TextView toolbar_title;

    @BindView(R.id.order_detail_status)
    TextView status;

    @BindView(R.id.order_detail_price_all)
    TextView priceAll;

    @BindView(R.id.order_detail_repeat_order_btn)
    TextView repeatOrderBtn;

    @BindView(R.id.order_detail_products_items)
    RecyclerView recyclerView;

    @BindView(R.id.orders_detail_toolbar_back)
    ImageView backImage;

    @Inject
    OrderItemsAdapter orderItemsAdapter;


    private static Result result;
    private static int amount;

    private int orderItem;
    private LinearLayoutManager linearLayoutManager;
    private List<Product> order_detail_items;
    private OwnerOrdersFragment ordersFragment;
    private FragmentTransaction fragmentTransaction;

    private static int AMOUNT;
    private static int PAYMENT_TYPE;
    private static int CONSIGMENT;
    private static String DATE;

    public OrderDetailFragment() {
        // Required empty public constructor
    }
    public static OrderDetailFragment newInstance() {
        OrderDetailFragment fragment = new OrderDetailFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_order_detail, container, false);

        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            setUnBinder(ButterKnife.bind(this, view));
            mPresenter.onAttach(this);
        }

        final Bundle bundle = getArguments();
        if (bundle != null){
            orderItem = bundle.getInt(OwnerOrdersFragment.TAG_ORDER);
        }

        return view;
    }

    @OnClick(R.id.orders_detail_toolbar_back)
    public void goBackOrderDetail(){
        mPresenter.onBackClick();
    }


    @OnClick(R.id.order_detail_repeat_order_btn)
    public void onRepeat(){
        if (result.getPaymentType().startsWith(AppConstants.PAYMENT_TEXT_1)){
            PAYMENT_TYPE=1;
        }else if(result.getPaymentType().startsWith(AppConstants.PAYMENT_TEXT_2)){
            PAYMENT_TYPE=2;
        }

        if(Integer.valueOf(result.getConsignment())==AppConstants.CONSIGNMENT_NO){
            mPresenter.onSendClick(Integer.valueOf(result.getProviderId()),Integer.valueOf(result.getConsignment()),
                    PAYMENT_TYPE,Integer.valueOf(result.getAmount()),result.getProducts());
        }else if(Integer.valueOf(result.getConsignment())==AppConstants.CONSIGNMENT_YES){
            mPresenter.onSendClickWithDate(Integer.valueOf(result.getProviderId()),Integer.valueOf(result.getConsignment()),
                    PAYMENT_TYPE,Integer.valueOf(result.getAmount()),result.getProducts(),result.getTillDate());

        }
        //mPresenter.onSendClick();
    }

    @Override
    public void openOrdersFragment() {
        getActivity().getSupportFragmentManager().popBackStack("OwnerOrders",
                FragmentManager.POP_BACK_STACK_INCLUSIVE);

        ordersFragment=new OwnerOrdersFragment();
        fragmentTransaction= ((AppCompatActivity)getActivity()).getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_frame,ordersFragment, TAG).commit();


    }

    @Override
    public void updateOrderDetailInfo(kz.production.kuanysh.sellings.data.network.model.owner.orderdetail.Result list) {

        result=list;
        orderItemsAdapter.addItems(result.getProducts());

        toolbar_title.setText("Заказ №"+orderItem);
        name.setText(result.getProviderName().toString().replace("\"",""));

        if(Integer.parseInt(result.getStatusId().toString())== AppConstants.SUPPLIER_ORDER_STATUS_CONFIRM){
            status.setText(AppConstants.OWNER_ORDER_STATUS_CONFIRM_TEXT);
            status.setTextColor(Color.parseColor(Colors.CONFIRMED_GREEN));
        }else if(Integer.parseInt(result.getStatusId().toString())== AppConstants.SUPPLIER_ORDER_STATUS_CANCELL){
            status.setText(AppConstants.OWNER_ORDER_STATUS_CANCELL_TEXT);
            status.setTextColor(Color.parseColor(Colors.CANCELLED_RED));
        }else if(Integer.parseInt(result.getStatusId().toString())== AppConstants.SUPPLIER_ORDER_STATUS_WAITING){
            status.setText(AppConstants.OWNER_ORDER_STATUS_WAITING_TEXT);

        }



        for(int i=0;i<result.getProducts().size();i++){
            amount+=Integer.parseInt(result.getProducts().get(i).getPrice())*
                    Integer.parseInt(result.getProducts().get(i).getNumberOfStock());
        }
        priceAll.setText(amount+"тг");

    }




    @Override
    protected void setUp(View view) {

        linearLayoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setAdapter(orderItemsAdapter);
        orderItemsAdapter.addContext(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        mPresenter.onViewPrepared(orderItem);


    }
}
