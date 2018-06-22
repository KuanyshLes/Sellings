package kz.production.kuanysh.sellings.ui.content_owner;

import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import kz.production.kuanysh.sellings.R;
import kz.production.kuanysh.sellings.ui.content_owner.fragments.basket.BasketFragment;
import kz.production.kuanysh.sellings.ui.content_owner.fragments.main.MainFragment;
import kz.production.kuanysh.sellings.ui.content_owner.fragments.order.OrdersFragment;
import kz.production.kuanysh.sellings.ui.content_owner.fragments.profile.ProfileFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final String TAG="visible_fragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        //default fragment
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, new MainFragment(), TAG)
                .commit();
        //getSupportActionBar().setTitle("Главная");

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setHomeButtonEnabled(true);
        //set navigation drawer
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    //close navigation drawer
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    //navigation listener
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment=null;
        if (id == R.id.nav_main) {
            MainFragment mainFragment=new MainFragment();
            setFragment(mainFragment);
        } else if (id == R.id.nav_orders) {
            OrdersFragment ordersFragment=new OrdersFragment();
            setFragment(ordersFragment);

        } else if (id == R.id.nav_history_orders) {

        } else if (id == R.id.nav_basket) {
            BasketFragment basketFragment =new BasketFragment();
            setFragment(basketFragment);
        } else if (id == R.id.nav_consignation) {

        } else if (id == R.id.nav_profile) {
            ProfileFragment profileFragment=new ProfileFragment();
            setFragment(profileFragment);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //fragment transaction
    private void setFragment(Fragment fragment){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, fragment, TAG);
        ft.addToBackStack(null);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }
}
