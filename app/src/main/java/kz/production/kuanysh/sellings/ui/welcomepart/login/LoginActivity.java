package kz.production.kuanysh.sellings.ui.welcomepart.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.accountkit.Account;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccountKitCallback;
import com.facebook.accountkit.AccountKitError;
import com.facebook.accountkit.AccountKitLoginResult;
import com.facebook.accountkit.PhoneNumber;
import com.facebook.accountkit.ui.AccountKitActivity;
import com.facebook.accountkit.ui.AccountKitConfiguration;
import com.facebook.accountkit.ui.LoginType;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kz.production.kuanysh.sellings.ui.welcomepart.registration.owner.OwnerRegistrationActivity;
import kz.production.kuanysh.sellings.ui.welcomepart.registration.supplier.SupplierRegistrationActivity;
import kz.production.kuanysh.sellings.ui.welcomepart.start.StartActivity;
import kz.production.kuanysh.sellings.ui.base.BaseActivity;
import kz.production.kuanysh.sellings.ui.content_owner.main.MainActivity;
import kz.production.kuanysh.sellings.R;
import kz.production.kuanysh.sellings.ui.content_suppiler.activity.SupplierActivity;
import kz.production.kuanysh.sellings.ui.welcomepart.registration.RegistrationPhoneActivity;

public class LoginActivity extends BaseActivity implements LoginMvpView {

    @Inject
    LoginPresenter<LoginMvpView> mPresenter;

    @BindView(R.id.login_phone_number)
    EditText phone;

    @BindView(R.id.login_password)
    EditText password;

    @BindView(R.id.login_enter)
    Button enter;

    @BindView(R.id.login_forget_password)
    TextView forget_password;

    @BindView(R.id.login_to_registration)
    TextView go_register;

    public static final String TAG_SUPPLIER_KEY="key";
    public static final String TAG_SUPPLIER_LOGIN="login";

    private static final String TAG_ENTER="enter";
    private static final String TAG_FORGET="forget";
    private static final String TAG_REGISTRATION="registration";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(LoginActivity.this);

    }

    @Override
    protected void setUp() {


    }

    @OnClick(R.id.login_enter)
    public void enter(){
        if(getIntent().getStringExtra(StartActivity.TAG_ENTER_AS)!=null){
            if(getIntent().getStringExtra(StartActivity.TAG_ENTER_AS).equals(StartActivity.TAG_OWNER)){
                mPresenter.onLoginAsOwnerClick(phone.getText().toString().trim(),
                  password.getText().toString().trim());

            }else {
                mPresenter.onLoginAsSupplierClick(phone.getText().toString().trim(),password.getText().toString().trim());
            }
        }

    }

    @OnClick(R.id.login_forget_password)
    public void forgetPassword(){
        mPresenter.onForgetPasswordClick();
    }


    @OnClick(R.id.login_to_registration)
    public void registration(){
        mPresenter.onRegistrationClick();
    }

    @Override
    public void openMainActivity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void openSupplierActivity() {
        Intent intent=new Intent(LoginActivity.this,SupplierActivity.class);
        intent.putExtra(TAG_SUPPLIER_KEY,TAG_SUPPLIER_LOGIN);
        startActivity(intent);
    }

    @Override
    public void openRegistrationActivity() {
        mPresenter.onViewPrepared();
    }

    @Override
    public void openForgetPasswordActivity() {

    }

    @Override
    public void openSmsFlow() {
        mPresenter.getMvpView().hideLoading();
        final Intent intent = new Intent(this, AccountKitActivity.class);
        AccountKitConfiguration.AccountKitConfigurationBuilder configurationBuilder =
                new AccountKitConfiguration.AccountKitConfigurationBuilder(
                        LoginType.PHONE,
                        AccountKitActivity.ResponseType.TOKEN); // or .ResponseType.TOKEN
        // ... perform additional configuration ...
        intent.putExtra(
                AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION,
                configurationBuilder.build());
        startActivityForResult(intent, 99);
    }

    @Override
    public void afterPhoneConfirmation() {
        if(getIntent().getStringExtra(StartActivity.TAG_ENTER_AS).equals(StartActivity.TAG_OWNER)){
            Intent intent=new Intent(LoginActivity.this,OwnerRegistrationActivity.class);
            intent.putExtra(StartActivity.TAG_ENTER_AS,getIntent().getStringExtra(StartActivity.TAG_ENTER_AS));
            startActivity(intent);
        }else if(getIntent().getStringExtra(StartActivity.TAG_ENTER_AS).equals(StartActivity.TAG_SUPPLIER)){
            Intent intent=new Intent(LoginActivity.this,SupplierRegistrationActivity.class);
            intent.putExtra(StartActivity.TAG_ENTER_AS,getIntent().getStringExtra(StartActivity.TAG_ENTER_AS));
            startActivity(intent);
        }

    }


    @Override
    protected void onActivityResult(
            final int requestCode,
            final int resultCode,
            final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 99) { // confirm that this response matches your request
            AccountKitLoginResult loginResult = data.getParcelableExtra(AccountKitLoginResult.RESULT_KEY);
            String toastMessage= "qwerty";
            if (loginResult.getError() != null) {
                toastMessage = loginResult.getError().getErrorType().getMessage();
            } else if (loginResult.wasCancelled()) {
                toastMessage = "Login Cancelled";
            } else {
                if (loginResult.getAccessToken() != null) {
                    toastMessage = "Success:" + loginResult.getAccessToken().getAccountId();

                    afterPhoneConfirmation();
                    //getAccount();
                }
            }
            // Surface the result to your user in an appropriate way.

        }
    }


    @Override
    protected void onDestroy() {
        mPresenter.onDetach();
        super.onDestroy();
    }


}
