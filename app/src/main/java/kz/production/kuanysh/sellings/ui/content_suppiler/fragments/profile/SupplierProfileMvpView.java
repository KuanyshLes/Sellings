package kz.production.kuanysh.sellings.ui.content_suppiler.fragments.profile;

import android.text.BoringLayout;
import android.view.View;

import kz.production.kuanysh.sellings.ui.base.MvpView;

/**
 * Created by User on 02.07.2018.
 */

public interface SupplierProfileMvpView extends MvpView {

    void openDrawer();

    void updateProfile();

    void setAnimation(View view, String status);

    void openChangesFragment(int type);

    void setEnablity(Boolean status);

    void setVisibility(String status);

    void openImagePick();

    void logOut();

    void onImageReceive(String imagelink);

    void openExitDialog();

}
