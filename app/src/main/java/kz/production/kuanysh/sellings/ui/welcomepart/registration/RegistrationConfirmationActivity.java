package kz.production.kuanysh.sellings.ui.welcomepart.registration;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import kz.production.kuanysh.sellings.R;
import kz.production.kuanysh.sellings.ui.welcomepart.start.StartActivity;
import kz.production.kuanysh.sellings.ui.welcomepart.registration.owner.OwnerRegistrationActivity;
import kz.production.kuanysh.sellings.ui.welcomepart.registration.supplier.SupplierRegistrationActivity;

public class RegistrationConfirmationActivity extends AppCompatActivity {


    private Button go_to_register;
    private EditText confirmation_code;
    private String enterAs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_confirmation);

        go_to_register=(Button) findViewById(R.id.go_to_full_registration);
        confirmation_code=(EditText)findViewById(R.id.registration_confirm_code);

        enterAs=getIntent().getStringExtra(StartActivity.TAG_ENTER_AS).toString();

        go_to_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                go_to_full_registration(enterAs);
            }
        });
    }
    public void go_to_full_registration(String enterAs){
        if(enterAs.equals(StartActivity.TAG_OWNER)){
            Intent intent=new Intent(RegistrationConfirmationActivity.this,OwnerRegistrationActivity.class);
            startActivity(intent);
        }if(enterAs.equals(StartActivity.TAG_SUPPLIER)){
            Intent intent=new Intent(RegistrationConfirmationActivity.this,SupplierRegistrationActivity.class);
            startActivity(intent);
        }

    }
}