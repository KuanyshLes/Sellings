package kz.production.kuanysh.sellings.ui.content_suppiler.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.Window;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import kz.production.kuanysh.sellings.R;
import kz.production.kuanysh.sellings.ui.base.BaseActivity;
import kz.production.kuanysh.sellings.ui.content_suppiler.fragments.consignment.all.SupplierConsignmentFragment;
import kz.production.kuanysh.sellings.ui.content_suppiler.fragments.goods.category.SupplierCategoryFragment;
import kz.production.kuanysh.sellings.ui.content_suppiler.fragments.order.all.SupplierOrdersFragment;
import kz.production.kuanysh.sellings.ui.content_suppiler.fragments.statistics.StatisticsFragment;
import kz.production.kuanysh.sellings.ui.content_suppiler.fragments.profile.SupplierProfileFragment;
import kz.production.kuanysh.sellings.ui.welcomepart.login.LoginActivity;
import kz.production.kuanysh.sellings.ui.welcomepart.registration.supplier.SupplierRegistrationActivity;

public class SupplierActivity extends BaseActivity
        implements SupplierMvpView{

    @Inject
    SupplierPresenter<SupplierMvpView> mPresenter;

    @Nullable
    @BindView(R.id.supplier_main_toolbar)
    Toolbar toolbar;

    @BindView(R.id.supplier_nav_view)
    NavigationView navigationView;

    @BindView(R.id.supplier_drawer_layout)
    DrawerLayout drawer;

    public static final String SUPPLIER_VISIBLE_FRAGMENT_TAG="visible_fragment";

    public static final String SUPPLIER_TAG_MAIN="main";
    public static final String SUPPLIER_TAG_ORDERS="orders";
    public static final String SUPPLIER_TAG_CONSIGNMENT="consignment";
    public static final String SUPPLIER_TAG_PROFILE="profile";
    public static final String SUPPLIER_TAG_GOODS="goods";

    public static  String firstTimeORNot = null;
    private static Dialog dialog;
    private static AlertDialog.Builder mBuilder;


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplier);


        getActivityComponent().inject(this);
        setUnBinder(ButterKnife.bind(this));
        mPresenter.onAttach(this);

        setUp();
        mPresenter.onStatisticsClick();

        firstTimeORNot=getIntent().getStringExtra(LoginActivity.TAG_SUPPLIER_KEY);
        if(firstTimeORNot.equals(SupplierRegistrationActivity.TAG_SUPPLIER_REGISTRATION)){
            mPresenter.getMvpView().showDialogFirstTime();
        }else{
            mPresenter.getMvpView().showMessage("logged in");
        }

    }

    @Override
    protected void setUp() {
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        setUpNavMenu();
        mPresenter.onNavMenuCreated();
    }

    void setUpNavMenu(){
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        drawer.closeDrawer(GravityCompat.START);
                        switch (item.getItemId()) {
                            case R.id.supplier_nav_main:
                                mPresenter.onStatisticsClick();
                                return true;
                            case R.id.supplier_nav_orders:
                                mPresenter.onOrdersClick();
                                return true;
                            case R.id.supplier_nav_history_orders:
                                return true;
                            case R.id.supplier_nav_consignation:
                                mPresenter.onConsignmentClick();
                                return true;
                            case R.id.supplier_nav_profile:
                                mPresenter.onProfileClick();
                                return true;
                            case R.id.supplier_nav_goods:
                                mPresenter.onGoodsClick();
                                return true;
                            default:
                                return false;
                        }
                    }
                });
    }


    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);

            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
            startActivity(intent);
        } else {
            super.onBackPressed();

            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
            startActivity(intent);
        }
    }


    @Override
    public void openLoginActivity() {

    }

    @Override
    public void openStatisticsFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .disallowAddToBackStack()
                .replace(R.id.supplier_content_frame, StatisticsFragment.newInstance(), SUPPLIER_TAG_MAIN)
                .commit();
    }

    @Override
    public void openOrdersFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .disallowAddToBackStack()
                .replace(R.id.supplier_content_frame, SupplierOrdersFragment.newInstance(), SUPPLIER_TAG_MAIN)
                .commit();
    }

    @Override
    public void openConsignmentFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .disallowAddToBackStack()
                .replace(R.id.supplier_content_frame, SupplierConsignmentFragment.newInstance(), SUPPLIER_TAG_MAIN)
                .commit();
    }

    @Override
    public void openProfileFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .disallowAddToBackStack()
                .replace(R.id.supplier_content_frame, SupplierProfileFragment.newInstance(), SUPPLIER_TAG_MAIN)
                .commit();
    }

    @Override
    public void openGoodsFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .disallowAddToBackStack()
                .replace(R.id.supplier_content_frame, SupplierCategoryFragment.newInstance(), SUPPLIER_TAG_GOODS)
                .commit();
    }

    @Override
    public void closeNavigationDrawer() {

    }

    @Override
    public void showDialogFirstTime() {
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
    @Override
    protected void onDestroy() {
        mPresenter.onDetach();
        super.onDestroy();
    }
}
