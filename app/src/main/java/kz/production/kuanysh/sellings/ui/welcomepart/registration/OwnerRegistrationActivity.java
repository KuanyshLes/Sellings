package kz.production.kuanysh.sellings.ui.welcomepart.registration;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioGroup;

import kz.production.kuanysh.sellings.R;
import kz.production.kuanysh.sellings.ui.content_owner.MainActivity;

public class OwnerRegistrationActivity extends AppCompatActivity {
    private RadioGroup radioGroup;
    private TextInputLayout name,iin,place,address,password,confirm_password;
    private Button save_btn;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_owner);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        radioGroup=(RadioGroup)findViewById(R.id.radiogroup);

        name=(TextInputLayout)findViewById(R.id.registration_name);
        iin=(TextInputLayout)findViewById(R.id.registration_IIN);
        place=(TextInputLayout)findViewById(R.id.registration_place);
        address=(TextInputLayout)findViewById(R.id.registration_address);
        password=(TextInputLayout)findViewById(R.id.registration_password);
        confirm_password=(TextInputLayout)findViewById(R.id.registration_confirm_password);
        save_btn=(Button)findViewById(R.id.owner_registration_save);
        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent=new Intent(OwnerRegistrationActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
