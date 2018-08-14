package kz.production.kuanysh.sellings.ui.content_owner.main;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import kz.production.kuanysh.sellings.R;
import kz.production.kuanysh.sellings.data.model.SupplierItem;
import kz.production.kuanysh.sellings.data.network.model.owner.basketproviders.BasketProviders;
import kz.production.kuanysh.sellings.ui.base.BaseActivity;
import kz.production.kuanysh.sellings.ui.content_owner.fragments.basket.BasketFragment;
import kz.production.kuanysh.sellings.ui.content_owner.fragments.basketproviders.BasketProvidersFragment;
import kz.production.kuanysh.sellings.ui.content_owner.fragments.consignment.OwnerConsignmentFragment;
import kz.production.kuanysh.sellings.ui.content_owner.fragments.main.main.OwnerSupplierItemFragment;
import kz.production.kuanysh.sellings.ui.content_owner.fragments.order.orders.OwnerOrdersFragment;
import kz.production.kuanysh.sellings.ui.content_owner.fragments.profile.ProfileFragment;

public class MainActivity extends BaseActivity implements MainMvpView {

    @Inject
    MainMvpPresenter<MainMvpView> mPresenter;


    @Nullable
    @BindView(R.id.main_toolbar)
    Toolbar toolbar;

    @BindView(R.id.nav_view)
    NavigationView navigationView;


    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    public static final String TAG="visible_fragment";

    public static final String TAG_MAIN="main";
    public static final String TAG_PROFILE="profile";
    public static final String TAG_BASKET="basket";
    public static final String TAG_ORDERS="orders";
    public static final String TAG_CONSIGNMENT="consignment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        getActivityComponent().inject(this);
        setUnBinder(ButterKnife.bind(this));
        mPresenter.onAttach(this);

        setUp();
        mPresenter.onDrawerMainClick();



    }

    @Override
    protected void setUp() {
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        setupNavMenu();
        mPresenter.onNavMenuCreated();


    }

    void setupNavMenu() {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        drawer.closeDrawer(GravityCompat.START);
                        switch (item.getItemId()) {
                            case R.id.nav_main:
                                mPresenter.onDrawerMainClick();
                                return true;
                            case R.id.nav_orders:
                                mPresenter.onDrawerOrdersClick();
                                return true;
                            case R.id.nav_basket:
                                mPresenter.onDrawerBasketClick();
                                return true;
                            case R.id.nav_profile:
                                mPresenter.onDrawerProfileClick();
                                return true;
                            case R.id.nav_consignation:
                                mPresenter.onConsignmentClick();
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
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
            startActivity(intent);
            super.onBackPressed();
        }
    }

    //fragment transaction
    private void setFragment(Fragment fragment){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, fragment, TAG);
        ft.addToBackStack(null);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }

    @Override
    public void openLoginActivity() {

    }

    @Override
    public void openMainFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame, OwnerSupplierItemFragment.newInstance(), TAG_MAIN)
                .commit();
    }

    @Override
    public void openOrdersFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame, OwnerOrdersFragment.newInstance(), TAG_ORDERS)
                .commit();
    }

    @Override
    public void openBasketFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame, BasketProvidersFragment.newInstance(), TAG_BASKET)
                .commit();
    }

    @Override
    public void openProfileFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame, ProfileFragment.newInstance(), TAG_PROFILE)
                .commit();
    }

    @Override
    public void openConsignmentFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame, OwnerConsignmentFragment.newInstance(), TAG_CONSIGNMENT)
                .commit();
    }

    @Override
    public void updateSupplierList(List<SupplierItem> supplierList) {

    }


    @Override
    public void closeNavigationDrawer() {
        if (drawer != null) {
            drawer.closeDrawer(Gravity.START);
        }
    }

    @Override
    public void onFragmentAttached() {

    }

    @Override
    public void onFragmentDetached(String tag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(tag);
        if (fragment != null) {
            fragmentManager
                    .beginTransaction()
                    .remove(fragment)
                    .commitNow();
        }
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDetach();
        super.onDestroy();
    }
}
