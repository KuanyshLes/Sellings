package kz.production.kuanysh.sellings.ui.content_suppiler;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.Window;
import android.widget.TextView;

import kz.production.kuanysh.sellings.R;
import kz.production.kuanysh.sellings.ui.content_suppiler.fragments.goods.SupplierCategoryFragment;
import kz.production.kuanysh.sellings.ui.content_suppiler.fragments.statistics.StatisticsFragment;
import kz.production.kuanysh.sellings.ui.content_suppiler.fragments.consignment.SupplierConsignmentFragment;
import kz.production.kuanysh.sellings.ui.content_suppiler.fragments.goods.SupplierGoodsFragment;
import kz.production.kuanysh.sellings.ui.content_suppiler.fragments.order.SupplierOrdersFragment;
import kz.production.kuanysh.sellings.ui.content_suppiler.fragments.profile.SupplierProfileFragment;
import kz.production.kuanysh.sellings.ui.welcomepart.login.LoginActivity;
import kz.production.kuanysh.sellings.ui.welcomepart.registration.SupplierRegistrationActivity;

public class SupplierActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public static final String SUPPLIER_VISIBLE_FRAGMENT_TAG="visible_fragment";
    public static  String firstTimeORNot = null;
    private static Dialog dialog;
    private static AlertDialog.Builder mBuilder;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplier);
        Toolbar toolbar = (Toolbar) findViewById(R.id.supplier_main_toolbar);
        setSupportActionBar(toolbar);

        //show dialog if user enters for the first time
        firstTimeORNot=getIntent().getStringExtra(LoginActivity.TAG_SUPPLIER_KEY);
        if(firstTimeORNot.equals(SupplierRegistrationActivity.TAG_SUPPLIER_REGISTRATION)){
            showDialog();
        }
        //default fragment
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.supplier_content_frame, new StatisticsFragment(), SUPPLIER_VISIBLE_FRAGMENT_TAG)
                .commit();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.supplier_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.supplier_nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.supplier_drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }




    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.supplier_nav_main) {
            StatisticsFragment statisticsFragment=new StatisticsFragment();
            setFragment(statisticsFragment);
        } else if (id == R.id.supplier_nav_orders) {
            SupplierOrdersFragment supplierOrdersFragment=new SupplierOrdersFragment();
            setFragment(supplierOrdersFragment);
        } else if (id == R.id.supplier_nav_history_orders) {
            SupplierCategoryFragment supplierCategoryFragment=new SupplierCategoryFragment();
            setFragment(supplierCategoryFragment);
        } else if (id == R.id.supplier_nav_consignation) {
            SupplierConsignmentFragment consignmentFragment=new SupplierConsignmentFragment();
            setFragment(consignmentFragment);
        } else if (id == R.id.supplier_nav_profile) {
            SupplierProfileFragment supplierProfileFragment=new SupplierProfileFragment();
            setFragment(supplierProfileFragment);

        } else if (id == R.id.supplier_nav_goods) {
            SupplierGoodsFragment supplierGoodsFragment=new SupplierGoodsFragment();
            setFragment(supplierGoodsFragment);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.supplier_drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //fragment changer
    private void setFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.supplier_content_frame, fragment, SUPPLIER_VISIBLE_FRAGMENT_TAG)
                .addToBackStack(null)
                .commit();
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void showDialog(){

        mBuilder= new AlertDialog.Builder(this);
        View mView =getLayoutInflater().inflate(R.layout.dialog_supplier_registration,null);
        mBuilder.setView(mView);

        dialog=mBuilder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();

        //size
        Rect displayRectangle = new Rect();
        Window window = this.getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
        //set size
        dialog.getWindow().setLayout((int)(displayRectangle.width() *
                0.84f), (int)(displayRectangle.height() * 0.28f));


        TextView dismiss=(TextView)mView.findViewById(R.id.dialog_dismiss_button);
        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }
}
