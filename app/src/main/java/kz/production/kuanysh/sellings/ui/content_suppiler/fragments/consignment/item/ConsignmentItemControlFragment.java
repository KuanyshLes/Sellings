package kz.production.kuanysh.sellings.ui.content_suppiler.fragments.consignment.item;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kz.production.kuanysh.sellings.R;
import kz.production.kuanysh.sellings.data.model.ConsignmentItem;
import kz.production.kuanysh.sellings.data.network.model.supplier.consignment.Result;
import kz.production.kuanysh.sellings.di.component.ActivityComponent;
import kz.production.kuanysh.sellings.ui.base.BaseFragment;
import kz.production.kuanysh.sellings.ui.content_suppiler.fragments.consignment.all.SupplierConsignmentFragment;
import kz.production.kuanysh.sellings.utils.AppConstants;

import static kz.production.kuanysh.sellings.ui.content_suppiler.activity.SupplierActivity.SUPPLIER_TAG_CONSIGNMENT;
import static kz.production.kuanysh.sellings.ui.content_suppiler.activity.SupplierActivity.SUPPLIER_VISIBLE_FRAGMENT_TAG;

public class ConsignmentItemControlFragment extends BaseFragment implements ConsignmentItemControlMvpView{

    @Inject
    ConsignmentItemControlPresenter<ConsignmentItemControlMvpView> mPresenter;

    @BindView(R.id.consignment_control_item_name)
    TextView control_name;

    @BindView(R.id.consignment_control_item_time)
    TextView control_time;

    @BindView(R.id.consignment_control_item_price)
    TextView control_price;

    @BindView(R.id.consignment_control_item_image)
    ImageView control_image;

    @BindView(R.id.consignment_control_toolbar_drawer)
    ImageView back;

    @BindView(R.id.consignment_control_item_summa)
    EditText control_summa;

    @BindView(R.id.consignment_control_item_confirm_btn)
    Button save;

    private Result result;


    public ConsignmentItemControlFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_consignment_item_control, container, false);

        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            setUnBinder(ButterKnife.bind(this, view));
            mPresenter.onAttach(this);
        }

        final Bundle bundle = getArguments();
        if (bundle != null){
            result = (Result) bundle.get(SupplierConsignmentFragment.KEY_CONSIGNMENT_ID);
        }



        return view;
    }


    @OnClick(R.id.consignment_control_toolbar_drawer)
    public void goAllConsignment(){
        mPresenter.onBackClick();
    }

    @OnClick(R.id.consignment_control_item_confirm_btn)
    public void goSaveConsignment(){
        mPresenter.onConfirmClick(result.getId(),Integer.parseInt(control_summa.getText().toString()));
    }


    @Override
    public void openAllConsignment() {
        getActivity().getSupportFragmentManager().popBackStack();
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.supplier_content_frame, SupplierConsignmentFragment.newInstance(), SUPPLIER_TAG_CONSIGNMENT)
                .commit();
    }

    @Override
    public void updateItem() {
        control_name.setText(result.getShopName().replace("\"",""));
        control_time.setText(result.getCredit());
        control_price.setText(result.getAmount()+ AppConstants.MONEY_TYPE);
        control_summa.setText(result.getCredit().toString());
        Glide.with(getActivity()).load(result.getProviderImage()).into(control_image);
    }

    @Override
    protected void setUp(View view) {
        mPresenter.onViewPrepared();
    }

    @Override
    public void onDestroyView() {
        mPresenter.onDetach();
        super.onDestroyView();
    }
}
