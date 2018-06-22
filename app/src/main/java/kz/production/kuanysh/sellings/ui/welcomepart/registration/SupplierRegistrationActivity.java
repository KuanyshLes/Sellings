package kz.production.kuanysh.sellings.ui.welcomepart.registration;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Spinner;

import kz.production.kuanysh.sellings.R;
import kz.production.kuanysh.sellings.ui.content_suppiler.SupplierActivity;
import kz.production.kuanysh.sellings.ui.welcomepart.login.LoginActivity;

public class SupplierRegistrationActivity extends AppCompatActivity implements View.OnClickListener {
    private TextInputLayout name,organizer,address,password,confirm_password;
    private Spinner timeFrom,timeTo;
    private Button save_btn;
    private String timeFromText,timeToText;
    private Intent intent;
    public static final String TAG_SUPPLIER_REGISTRATION="registration";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplier_registration);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        //initialize
        name=(TextInputLayout)findViewById(R.id.supplier_registration_name);
        organizer=(TextInputLayout)findViewById(R.id.supplier_registration_organizer);
        address=(TextInputLayout)findViewById(R.id.supplier_registration_address);
        password=(TextInputLayout)findViewById(R.id.supplier_registration_password);
        confirm_password=(TextInputLayout)findViewById(R.id.supplier_registration_confirm_password);

        timeFrom=(Spinner)findViewById(R.id.supplier_registration_time_from);
        timeTo=(Spinner)findViewById(R.id.supplier_registration_time_to);

        save_btn=(Button)findViewById(R.id.supplier_registration_save);

        //selected times
        timeFromText=String.valueOf(timeFrom.getSelectedItem());
        timeToText=String.valueOf(timeTo.getSelectedItem());

        save_btn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.supplier_registration_save:
                intent=new Intent(SupplierRegistrationActivity.this, SupplierActivity.class);
                intent.putExtra(LoginActivity.TAG_SUPPLIER_KEY,TAG_SUPPLIER_REGISTRATION);
                startActivity(intent);

        }
    }
}
