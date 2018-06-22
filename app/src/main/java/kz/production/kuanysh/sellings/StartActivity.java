package kz.production.kuanysh.sellings;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import kz.production.kuanysh.sellings.ui.welcomepart.login.LoginActivity;

public class StartActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView owner,supplier;

    public static final String TAG_OWNER="owner";
    public static final String TAG_SUPPLIER="supplier";
    public static final String TAG_ENTER_AS="key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        owner=(TextView)findViewById(R.id.owner);
        supplier=(TextView)findViewById(R.id.supplier);

        owner.setOnClickListener(this);
        supplier.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.owner:
                fire(TAG_OWNER);
                break;
            case R.id.supplier:
                fire(TAG_SUPPLIER);
                break;
        }
    }
    private void fire(String enterAs){
        Intent intent =new Intent(StartActivity.this, LoginActivity.class);
        intent.putExtra(TAG_ENTER_AS,enterAs);
        startActivity(intent);
    }
}
