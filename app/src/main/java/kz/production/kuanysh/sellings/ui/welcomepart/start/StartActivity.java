package kz.production.kuanysh.sellings.ui.welcomepart.start;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kz.production.kuanysh.sellings.R;
import kz.production.kuanysh.sellings.ui.base.BaseActivity;
import kz.production.kuanysh.sellings.ui.content_owner.main.MainActivity;
import kz.production.kuanysh.sellings.ui.content_suppiler.activity.SupplierActivity;
import kz.production.kuanysh.sellings.ui.welcomepart.login.LoginActivity;
import kz.production.kuanysh.sellings.ui.welcomepart.registration.supplier.SupplierRegistrationActivity;
import kz.production.kuanysh.sellings.utils.AppConstants;

public class StartActivity extends BaseActivity implements StartMvpView{

    @Inject
    StartPresenter<StartMvpView> mPresenter;

    @BindView(R.id.owner)
    TextView owner;

    @BindView(R.id.supplier)
    TextView supplier;

    public static final String TAG_OWNER="owner";
    public static final String TAG_SUPPLIER="supplier";
    public static final String TAG_ENTER_AS="key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(StartActivity.this);
        setUp();

    }

    @OnClick(R.id.owner)
    public void ownerClick(){
        mPresenter.onOwnerClick(TAG_OWNER);
    }

    @OnClick(R.id.supplier)
    public void supplierClick(){
        mPresenter.onSupplierClick(TAG_SUPPLIER);
    }



    @Override
    protected void setUp() {
        if(mPresenter.getDataManager().getCurrentUserType()== AppConstants.USER_TYPE_OWNER){
            mPresenter.getMvpView().openMainActivity();
        }else if(mPresenter.getDataManager().getCurrentUserType()==AppConstants.USER_TYPE_SUPPLIER){
            mPresenter.getMvpView().openSupplierActivity();
        }

    }

    @Override
    public void openLoginActivity(String key) {
        Intent intent =new Intent(StartActivity.this, LoginActivity.class);
        intent.putExtra(TAG_ENTER_AS,key);
        startActivity(intent);
    }

    @Override
    public void openMainActivity() {
        Intent intent =new Intent(StartActivity.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void openSupplierActivity() {
        Intent intent =new Intent(StartActivity.this, SupplierActivity.class);
        intent.putExtra(LoginActivity.TAG_SUPPLIER_KEY, SupplierRegistrationActivity.TAG_SUPPLIER_CURRENT);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDetach();
        super.onDestroy();
    }
}
