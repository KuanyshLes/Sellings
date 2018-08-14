package kz.production.kuanysh.sellings;

import android.app.Application;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.interceptors.HttpLoggingInterceptor;
import com.facebook.accountkit.AccountKit;

import javax.inject.Inject;

import kz.production.kuanysh.sellings.data.DataManager;
import kz.production.kuanysh.sellings.di.component.*;
import kz.production.kuanysh.sellings.di.module.ApplicationModule;

/**
 * Created by User on 24.06.2018.
 */

public class Sellings extends Application {

    @Inject
    DataManager mDataManager;


    private ApplicationComponent mApplicationComponent;



    @Override
    public void onCreate() {
        super.onCreate();

        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this)).build();

        mApplicationComponent.inject(this);

        AccountKit.initialize(getApplicationContext());

        //AppLogger.init();

        AndroidNetworking.initialize(getApplicationContext());
        if (BuildConfig.DEBUG) {
            AndroidNetworking.enableLogging(HttpLoggingInterceptor.Level.BODY);
        }

    }

    public ApplicationComponent getComponent() {
        return mApplicationComponent;
    }


    // Needed to replace the component with a test specific one
    public void setComponent(ApplicationComponent applicationComponent) {
        mApplicationComponent = applicationComponent;
    }
}
