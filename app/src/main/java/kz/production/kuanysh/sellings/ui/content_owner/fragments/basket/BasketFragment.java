package kz.production.kuanysh.sellings.ui.content_owner.fragments.basket;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.Binds;
import kz.production.kuanysh.sellings.R;
import kz.production.kuanysh.sellings.data.model.Product;
import kz.production.kuanysh.sellings.data.network.model.owner.basket.Result;
import kz.production.kuanysh.sellings.data.network.model.owner.orderresponse.OrderResponse;
import kz.production.kuanysh.sellings.di.component.ActivityComponent;
import kz.production.kuanysh.sellings.ui.base.BaseFragment;
import kz.production.kuanysh.sellings.ui.content_owner.fragments.basketproviders.BasketProvidersFragment;
import kz.production.kuanysh.sellings.ui.content_owner.fragments.main.main.OwnerSupplierItemFragment;
import kz.production.kuanysh.sellings.ui.content_owner.main.MainActivity;
import kz.production.kuanysh.sellings.ui.content_owner.utils.adapters.BasketItemsAdapter;
import kz.production.kuanysh.sellings.utils.AppConstants;

import static kz.production.kuanysh.sellings.ui.content_owner.main.MainActivity.TAG_BASKET;

/**
 * A simple {@link Fragment} subclass.
 */
public class BasketFragment extends BaseFragment implements BasketFragmentMvpView ,DatePickerDialog.OnDateSetListener {

    @Inject
    BasketFragmentPresenter<BasketFragmentMvpView> mPresenter;

    @BindView(R.id.basket_toolbar_back)
    ImageView back;

    @BindView(R.id.basket_toolbar_title)
    TextView title;

    @BindView(R.id.basket_items)
    RecyclerView recyclerView;

    @BindView(R.id.basket_consignation)
    CheckBox consignation;

    @BindView(R.id.basket_radiogroup)
    RadioGroup payment_way;

    @BindView(R.id.payment_type_1)
    RadioButton type1;

    @BindView(R.id.payment_type_2)
    RadioButton type2;

    //@BindView(R.id.basket_all_price)
    private static TextView basket_price;

    @BindView(R.id.basket_send)
    TextView send;

    @BindView(R.id.owner_consignation_tilldate)
    TextView tilldate;

    @Inject
    BasketItemsAdapter basketItemsAdapter;

    private LinearLayoutManager linearLayoutManager;
    private List<Product> order_detail_items;
    private static DrawerLayout drawer;
    private static int PROVIDER_ID;
    private static String PROVIDER_TITLE;
    private static int PRODUCT_AMOUNT;
    private static List<Result> basketItems;
    private static int PAYMENT_INT;
    private static String dateString;


    public BasketFragment() {
        // Required empty public constructor
    }
    public static BasketFragment newInstance() {
        BasketFragment fragment = new BasketFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_basket, container, false);

        drawer = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);

        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            setUnBinder(ButterKnife.bind(this, view));
            mPresenter.onAttach(this);
        }

        final Bundle bundle = getArguments();
        if(bundle!=null){
            PROVIDER_ID=bundle.getInt(BasketProvidersFragment.KEY_BASKET);
            PROVIDER_TITLE=bundle.getString(BasketProvidersFragment.KEY_BASKET_1);
        }

        return view;
    }

    @Override
    public void updateBasketItems(List<Result> productList) {
        basketItems.clear();
        basketItems.addAll(productList);
        basketItemsAdapter.addItems(basketItems);


        PRODUCT_AMOUNT=0;
        for(int i=0;i<productList.size();i++){
            PRODUCT_AMOUNT+=Integer.parseInt(productList.get(i).getPrice())*
                    productList.get(i).getNumberOfStock();
        }
        basket_price.setText(PRODUCT_AMOUNT+"тг");
        title.setText(PROVIDER_TITLE.replace("\"",""));

    }

    @Override
    public void onAmountChangedClick(int amount,int position) {
        PRODUCT_AMOUNT=0;
        for(int i=0;i<basketItems.size();i++){
            PRODUCT_AMOUNT+=Integer.parseInt(basketItems.get(i).getPrice())*
                    basketItems.get(i).getNumberOfStock();
        }
        basket_price.setText(PRODUCT_AMOUNT+"тг");
    }

    @Override
    public void openProvidersFragment() {
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .disallowAddToBackStack()
                .replace(R.id.content_frame, BasketProvidersFragment.newInstance(), TAG_BASKET)
                .commit();


    }





    @OnClick(R.id.basket_send)
    public void send() {
        if(PRODUCT_AMOUNT>5000){
            if(type1.isChecked() || type2.isChecked()){
                switch (payment_way.getCheckedRadioButtonId()){
                    case R.id.payment_type_1:
                        PAYMENT_INT=1;
                        break;
                    case R.id.payment_type_2:
                        PAYMENT_INT=2;
                        break;
                }

                if(!consignation.isChecked()){
                    mPresenter.onSendClick(PROVIDER_ID, AppConstants.OWNER_CONSIGNMENT_NO,PAYMENT_INT,PRODUCT_AMOUNT,basketItems);
                }else{
                    mPresenter.onSendClickWithDate(PROVIDER_ID, AppConstants.OWNER_CONSIGNMENT_YES,PAYMENT_INT,PRODUCT_AMOUNT,basketItems,dateString);
                }
                Log.d("myTag",PROVIDER_ID+" PROVIDER ID");
                Log.d("myTag",PAYMENT_INT+" PAYMENT TYPE");
            }else{
                mPresenter.getMvpView().showMessage("Please one of the payment type");

            }
        }else {
            mPresenter.getMvpView().showMessage("Order must be greater than 5000 tenge");
        }

    }

    @OnClick(R.id.basket_toolbar_back)
    public void openProviders(){
        mPresenter.getMvpView().openProvidersFragment();
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

        dateString = year+"/"+(monthOfYear+1)+"/"+dayOfMonth;
        tilldate.setVisibility(View.VISIBLE);
        tilldate.setText(dateString);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public static void countAllPrice(int position,int count){
        PRODUCT_AMOUNT=0;
        for(int i=0;i<basketItems.size();i++){
            if (i==position){
                PRODUCT_AMOUNT+=Integer.parseInt(basketItems.get(i).getPrice())*
                        count;

            }else{
                PRODUCT_AMOUNT+=Integer.parseInt(basketItems.get(i).getPrice())*
                        basketItems.get(i).getNumberOfStock();

            }
        }
        basket_price.setText(PRODUCT_AMOUNT+"тг");



    }

    @Override
    protected void setUp(View view) {
        basket_price=(TextView) view.findViewById(R.id.basket_all_price);
        basketItems=new ArrayList<>();
        linearLayoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(basketItemsAdapter);
        mPresenter.onViewPrepared(PROVIDER_ID);
        basketItemsAdapter.addContext(getActivity());
        basketItemsAdapter.addPresenter(mPresenter,PROVIDER_ID);

        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                BasketFragment.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );

        dpd.setMinDate(now);
        dpd.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                consignation.setChecked(false);
                tilldate.setVisibility(View.GONE);
            }
        });


        consignation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                if(isChecked){
                    dpd.show(getActivity().getFragmentManager(), "Datepickerdialog");
                }else{
                    tilldate.setVisibility(View.INVISIBLE);
                }
            }
        }
        );
    }

    @Override
    public void onDestroyView() {
        mPresenter.onDetach();
        super.onDestroyView();
    }

}
