package kz.production.kuanysh.sellings.ui.welcomepart.registration;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import kz.production.kuanysh.sellings.R;
import kz.production.kuanysh.sellings.StartActivity;

public class RegistrationPhoneActivity extends AppCompatActivity {

    private EditText phone;
    private Button get_confirmation_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_phone);

        phone=(EditText)findViewById(R.id.registration_write_phone_number);
        get_confirmation_code=(Button) findViewById(R.id.go_to_full_registration);

        get_confirmation_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirm_phone();
            }
        });
    }
    //go to registration
    public void confirm_phone(){
        Intent intent=new Intent(RegistrationPhoneActivity.this,RegistrationConfirmationActivity.class);
        intent.putExtra(StartActivity.TAG_ENTER_AS,getIntent().getStringExtra(StartActivity.TAG_ENTER_AS));
        startActivity(intent);
    }
}
