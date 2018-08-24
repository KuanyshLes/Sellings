package kz.production.kuanysh.sellings.ui.content_owner.fragments.profile;


import android.animation.PropertyValuesHolder;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.CardView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.esafirm.imagepicker.features.ImagePicker;
import com.facebook.accountkit.ui.AccountKitActivity;
import com.facebook.accountkit.ui.AccountKitConfiguration;
import com.facebook.accountkit.ui.LoginType;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kz.production.kuanysh.sellings.R;
import kz.production.kuanysh.sellings.di.component.ActivityComponent;
import kz.production.kuanysh.sellings.ui.base.BaseFragment;
import kz.production.kuanysh.sellings.ui.content_owner.fragments.changecredentials.ChangeInfoFragment;
import kz.production.kuanysh.sellings.ui.content_owner.fragments.main.main.OwnerSupplierItemFragment;
import kz.production.kuanysh.sellings.ui.welcomepart.start.StartActivity;
import kz.production.kuanysh.sellings.utils.AppConstants;
import kz.production.kuanysh.sellings.utils.rx.AppMessages;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static android.app.Activity.RESULT_OK;
import static kz.production.kuanysh.sellings.ui.content_owner.main.MainActivity.TAG_MAIN;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends BaseFragment implements ProfileFragmentMvpView{

    public final String TAG_FRAGMENT_STACK=getClass().getSimpleName();


    @Inject
    ProfileFragmentPresenter<ProfileFragmentMvpView> mPresenter;

    @BindView(R.id.profile_toolbar_back)
    ImageView back;

    @BindView(R.id.profile_toolbar_edit)
    ImageView edit;

    @BindView(R.id.owner_profile_photoimage)
    ImageView profilePhoto;

    @BindView(R.id.profile_radiogroup)
    RadioGroup radioGroup;

    @BindView(R.id.owner_profile_phys)
    RadioButton phys;

    @BindView(R.id.owner_profile_lawyer)
    RadioButton lawyer;

    @BindView(R.id.profile_name)
    TextInputLayout name;

    @BindView(R.id.profile_iin)
    TextInputLayout iin;

    @BindView(R.id.profile_place)
    TextInputLayout place;

    @BindView(R.id.profile_address)
    TextInputLayout address;

    @BindView(R.id.profile_phone_number)
    TextView phone_number;

    @BindView(R.id.profile_change_password)
    TextView change_password;

    @BindView(R.id.profile_save_btn)
    Button save_btn;

    @BindView(R.id.owner_profile_logout)
    TextView logout;

    @BindView(R.id.owner_card_logout)
    CardView logoutCard;

    @BindView(R.id.supplier_profile_card_phone)
    CardView changePhoneCard;

    @BindView(R.id.supplier_profile_card_password)
    CardView changePasswordCard;

    public static final int STATUS_PHONE=1;
    public static final int STATUS_PASSWORD=2;
    public static final String STATUS_KEY="ege";
    private static Uri uriImage;
    private static String filePath;
    private static String imageString;
    private static  Bitmap imageMap;
    private static Bundle bundle;
    public static String PASS_PHONE_CHANGE="changes";
    private static Dialog dialog;
    private static AlertDialog.Builder mBuilder;

    public static String OWNER_STATUS;

    private DrawerLayout drawer;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance() {
        Bundle args = new Bundle();
        ProfileFragment fragment = new ProfileFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_profile, container, false);

        drawer = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        ActivityComponent component = getActivityComponent();

        if (component != null) {
            component.inject(this);
            setUnBinder(ButterKnife.bind(this, view));
            mPresenter.onAttach(this);
        }

        return view;
    }

    @OnClick(R.id.owner_card_logout)
    public void logout(){
        mPresenter.getMvpView().openExitDialog();
    }

    @OnClick(R.id.profile_toolbar_edit)
    public void editPro(){
        mPresenter.getMvpView().setVisibility(AppConstants.TAG_EDIT);
        mPresenter.getMvpView().setEnablity(true);
        mPresenter.getMvpView().showMessage(AppMessages.CHANGE_IMAGE);
    }

    @OnClick(R.id.supplier_profile_card_password)
    public void changePass(){
        mPresenter.onChangePasswordClick(STATUS_PASSWORD);
    }

    @OnClick(R.id.supplier_profile_card_phone)
    public void changePhone(){
        mPresenter.onChangePhoneClick(STATUS_PHONE);
    }

    @OnClick(R.id.owner_profile_photoimage)
    public void openImageEdit(){
        mPresenter.onImageClick();
    }

    @OnClick(R.id.profile_save_btn)
    public void savePro(){
        switch (radioGroup.getCheckedRadioButtonId()) {
            case R.id.owner_profile_phys:
                OWNER_STATUS=AppConstants.USER_STATUS_PHYS;
                break;
            case R.id.owner_profile_lawyer:
                OWNER_STATUS=AppConstants.USER_STATUS_LAWYER;
                break;
        }


        if(filePath!=null && uriImage!=null){

            Log.i("PATH", uriImage.toString());

            File file = new File(filePath);
            RequestBody filePart = RequestBody.create(MediaType.parse("image/*"), file);
            MultipartBody.Part body =
                    MultipartBody.Part.createFormData("image", file.getName(), filePart);

            mPresenter.onSaveClick(name.getEditText().getText().toString(),
                   iin.getEditText().getText().toString(),body,
                    place.getEditText().getText().toString(),
                    address.getEditText().getText().toString(),OWNER_STATUS
            );
        }
        else{
            mPresenter.onSaveClickWithoutImage(name.getEditText().getText().toString(),
                    iin.getEditText().getText().toString(),
                    place.getEditText().getText().toString(),
                    address.getEditText().getText().toString(),OWNER_STATUS);
            /*mPresenter.onSaveClick(name.getEditText().getText().toString(),
                    Integer.valueOf(iin.getEditText().toString()),
                    ,
                    place.getEditText().getText().toString(),
                    address.getEditText().getText().toString(),OWNER_STATUS
            );*/

        }


    }

    @OnClick(R.id.profile_toolbar_back)
    public void openDrawerPro(){
        drawer.openDrawer(GravityCompat.START);
    }



    @Override
    public void setCredentials(List<String> profile) {
        name.getEditText().setText(profile.get(0).toString().replace("\"",""));
        iin.getEditText().setText(profile.get(1).toString().replace("\"",""));
        place.getEditText().setText(profile.get(2).toString().replace("\"",""));
        address.getEditText().setText(profile.get(3).toString().replace("\"",""));
        phone_number.setText(profile.get(4).toString());

        if(profile.get(5).substring(0,2).toLowerCase().equals(AppConstants.USER_STATUS_PHYS)){
            phys.setChecked(true);
        }else{
            lawyer.setChecked(true);
        }


    }


    @Override
    public void setAnimation(View view,String status) {
        if(status.equals(AppConstants.ANIMATION_STATUS_SHOW)){
            AlphaAnimation animation1 = new AlphaAnimation(0.0f, 1.0f);
            animation1.setDuration(200);
            animation1.setFillAfter(true);
            view.startAnimation(animation1);
            view.setVisibility(View.VISIBLE);
            view.setEnabled(true);
            view.setClickable(true);
        }else{
            AlphaAnimation animation1 = new AlphaAnimation(1.0f, 0.0f);
            animation1.setDuration(250);
            animation1.setFillAfter(true);
            view.startAnimation(animation1);
            view.setVisibility(View.GONE);
            view.setEnabled(false);
            view.setClickable(false);
        }
    }

    @Override
    public void setEnablity(Boolean status) {
        phys.setClickable(status);
        lawyer.setClickable(status);
        profilePhoto.setClickable(status);

        name.getEditText().setFocusableInTouchMode(status);
        name.getEditText().setFocusable(status);

        iin.getEditText().setFocusableInTouchMode(status);
        iin.getEditText().setFocusable(status);

        place.getEditText().setFocusableInTouchMode(status);
        place.getEditText().setFocusable(status);

        address.getEditText().setFocusableInTouchMode(status);
        address.getEditText().setFocusable(status);


    }

    @Override
    public void openImagePicker() {
        ImagePicker.create(this)
                .folderMode(true) // folder mode (false by default)
                .toolbarFolderTitle("Sellings") // folder selection title
                .toolbarImageTitle("Нажмите чтобы выбрать") // image selection title
                .toolbarArrowColor(Color.BLACK) // Toolbar 'up' arrow color
                .single() // single mode
                .limit(1) // max images can be selected (99 by default)
                .showCamera(true) // show camera or not (true by default)
                .imageDirectory("Sellings") // directory name for captured image  ("Camera" folder by default)
                .enableLog(false) // disabling log
                .start(); // start image picker activity with request code
    }

    @Override
    public void setVisibility(String status) {
        if(status.equals(AppConstants.TAG_EDIT)){
            mPresenter.getMvpView().setAnimation(save_btn, AppConstants.ANIMATION_STATUS_SHOW);
            mPresenter.getMvpView().setAnimation(edit, AppConstants.ANIMATION_STATUS_HIDE);
            mPresenter.getMvpView().setAnimation(logoutCard, AppConstants.ANIMATION_STATUS_HIDE);
        }else if(status.equals(AppConstants.TAG_SAVE)){
            mPresenter.getMvpView().setAnimation(save_btn, AppConstants.ANIMATION_STATUS_HIDE);
            mPresenter.getMvpView().setAnimation(edit, AppConstants.ANIMATION_STATUS_SHOW);
            mPresenter.getMvpView().setAnimation(logoutCard, AppConstants.ANIMATION_STATUS_SHOW);
        }
    }

    @Override
    public void openStartActivity() {
        Intent intent=new Intent(getActivity(), StartActivity.class);
        getActivity().startActivity(intent);

    }

    @Override
    public void openSmsFlow() {

    }

    @Override
    public void onImageReceive(String imageLink) {
        Glide.with(this).load(AppConstants.BASE_URL_IMAGE+imageLink).into(profilePhoto);
    }

    @Override
    public void openChangeInfoFragment(int status) {
        ChangeInfoFragment changeInfoFragment=new ChangeInfoFragment();
        bundle=new Bundle();
        bundle.putInt(STATUS_KEY,status);
        bundle.putInt(PASS_PHONE_CHANGE,AppConstants.USER_TYPE_OWNER);
        changeInfoFragment.setArguments(bundle);

        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(TAG_FRAGMENT_STACK)
                .hide(this)
                .add(R.id.content_frame, changeInfoFragment, TAG_MAIN).commit();
    }

    @Override
    public void openExitDialog() {
        mBuilder= new AlertDialog.Builder(getActivity());
        View mView =getLayoutInflater().inflate(R.layout.dialog_exit,null);
        mBuilder.setView(mView);

        dialog=mBuilder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();

        //size
        Rect displayRectangle = new Rect();
        Window window = getActivity().getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
        //set size
        dialog.getWindow().setLayout((int)(displayRectangle.width() *
                0.84f), (int)(displayRectangle.height() * 0.20f));


        TextView ok=(TextView)mView.findViewById(R.id.dialog_exit_ok);
        TextView cancell=(TextView)mView.findViewById(R.id.dialog_exit_cancell);
        cancell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                mPresenter.setUserAsLoggedOut();
                mPresenter.getMvpView().openStartActivity();
            }
        });

    }

    @Override
    public  void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            com.esafirm.imagepicker.model.Image image = ImagePicker.getFirstImageOrNull(data);
            filePath=image.getPath();

            File f = new File(filePath);  //
            uriImage = Uri.fromFile(f);

            Glide.with(getActivity()).load(filePath).into(profilePhoto);

        }
    }


    @Override
    protected void setUp(View view) {
        mPresenter.onViewPrepared();
        mPresenter.getMvpView().setEnablity(false);
        mPresenter.getMvpView().setVisibility(AppConstants.TAG_SAVE);
        mPresenter.onImageRequest();
    }

    @Override
    public void onDestroyView() {
        mPresenter.onDetach();
        super.onDestroyView();
    }
}
