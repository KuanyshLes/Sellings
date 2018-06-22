package kz.production.kuanysh.sellings.ui.welcomepart.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import kz.production.kuanysh.sellings.StartActivity;
import kz.production.kuanysh.sellings.ui.content_owner.MainActivity;
import kz.production.kuanysh.sellings.R;
import kz.production.kuanysh.sellings.ui.content_suppiler.SupplierActivity;
import kz.production.kuanysh.sellings.ui.welcomepart.registration.RegistrationPhoneActivity;

public class LoginActivity extends AppCompatActivity   implements View.OnClickListener{
    private EditText phone,password;
    private Button enter;
    private TextView go_register,forget_password;
    public static final String TAG_SUPPLIER_KEY="key";
    public static final String TAG_SUPPLIER_LOGIN="login";

    private static final String TAG_ENTER="enter";
    private static final String TAG_FORGET="forget";
    private static final String TAG_REGISTRATION="registration";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //initialize
        phone=(EditText)findViewById(R.id.login_phone_number);
        password=(EditText)findViewById(R.id.login_password);
        enter=(Button) findViewById(R.id.login_enter);
        go_register=(TextView) findViewById(R.id.login_to_registration);
        forget_password=(TextView) findViewById(R.id.login_forget_password);

        //set listener
        enter.setOnClickListener(this);
        forget_password.setOnClickListener(this);
        go_register.setOnClickListener(this);
    }


    //go to the next activity
    private void fire_to_activity(String status){
        if(status.equals(TAG_ENTER)){
            String enterAs=getIntent().getStringExtra(StartActivity.TAG_ENTER_AS);
            if(enterAs.equals(StartActivity.TAG_OWNER)){
                Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);
            }else if(enterAs.equals(StartActivity.TAG_SUPPLIER)){
                Intent intent=new Intent(LoginActivity.this,SupplierActivity.class);
                intent.putExtra(TAG_SUPPLIER_KEY,TAG_SUPPLIER_LOGIN);
                startActivity(intent);
            }
        }
        if(status.equals(TAG_FORGET)){
            //do something
        }
        if(status.equals(TAG_REGISTRATION)){
            Intent intent=new Intent(LoginActivity.this,RegistrationPhoneActivity.class);
            intent.putExtra(StartActivity.TAG_ENTER_AS,getIntent().getStringExtra(StartActivity.TAG_ENTER_AS));
            startActivity(intent);
        }
    }

    //click reaction
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_enter:
                fire_to_activity(TAG_ENTER);
                break;
            case R.id.login_forget_password:
                fire_to_activity(TAG_FORGET);
                break;
            case R.id.login_to_registration:
                fire_to_activity(TAG_REGISTRATION);
                break;
        }
    }
}
