package kz.production.kuanysh.sellings.ui.content_suppiler.fragments.profile;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.esafirm.imagepicker.features.ImagePicker;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kz.production.kuanysh.sellings.R;
import kz.production.kuanysh.sellings.di.component.ActivityComponent;
import kz.production.kuanysh.sellings.ui.base.BaseFragment;
import kz.production.kuanysh.sellings.ui.content_owner.fragments.changecredentials.ChangeInfoFragment;
import kz.production.kuanysh.sellings.ui.content_suppiler.fragments.statistics.StatisticsFragment;
import kz.production.kuanysh.sellings.ui.welcomepart.start.StartActivity;
import kz.production.kuanysh.sellings.utils.AppConstants;
import kz.production.kuanysh.sellings.utils.rx.AppMessages;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static kz.production.kuanysh.sellings.ui.content_owner.fragments.profile.ProfileFragment.PASS_PHONE_CHANGE;
import static kz.production.kuanysh.sellings.ui.content_owner.fragments.profile.ProfileFragment.STATUS_KEY;
import static kz.production.kuanysh.sellings.ui.content_owner.fragments.profile.ProfileFragment.STATUS_PASSWORD;
import static kz.production.kuanysh.sellings.ui.content_owner.fragments.profile.ProfileFragment.STATUS_PHONE;
import static kz.production.kuanysh.sellings.ui.content_owner.main.MainActivity.TAG;
import static kz.production.kuanysh.sellings.ui.content_owner.main.MainActivity.TAG_MAIN;

/**
 * A simple {@link Fragment} subclass.
 */
public class SupplierProfileFragment extends BaseFragment implements SupplierProfileMvpView{

    public final String TAG_FRAGMENT_STACK=getClass().getSimpleName();


    @Inject
    SupplierProfilePresenter<SupplierProfileMvpView> mPresenter;

    @BindView(R.id.supplier_profile_name)
    TextInputLayout name;

    @BindView(R.id.supplier_profile_organizer)
    TextInputLayout organizer;

    @BindView(R.id.supplier_profile_address)
    TextInputLayout address;

    @BindView(R.id.supplier_profile_time_from)
    Spinner timeFrom;

    @BindView(R.id.supplier_profile_time_to)
    Spinner timeTo;

    @BindView(R.id.supplier_profile_save)
    Button save_btn;

    @BindView(R.id.supplier_profile_toolbar_drawer)
    ImageView back;

    @BindView(R.id.supplier_profile_toolbar_edit)
    ImageView edit;

    @BindView(R.id.supplier_profile_picture)
    ImageView photo;

    @BindView(R.id.supplier_profile_phone_number)
    TextView phone;

    @BindView(R.id.supplier_profile_change_password)
    TextView password;

    @BindView(R.id.supplier_profile_card_password)
    CardView passwordCard;

    @BindView(R.id.supplier_profile_card_phone)
    CardView phoneCard;

    @BindView(R.id.supplier_card_logout)
    CardView logoutCard;

    private static Uri uriImage;
    private static String filePath;
    private String timeFromText,timeToText;
    private DrawerLayout drawerLayout;
    private static Bundle bundle;
    private static String IMAGE_LINK="";
    private static Dialog dialog;
    private static AlertDialog.Builder mBuilder;


    public SupplierProfileFragment() {
        // Required empty public constructor
    }
    public static SupplierProfileFragment newInstance() {
        Bundle args = new Bundle();
        SupplierProfileFragment fragment = new SupplierProfileFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_supplier_profile, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);


        drawerLayout=(DrawerLayout)getActivity().findViewById(R.id.supplier_drawer_layout);


        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            setUnBinder(ButterKnife.bind(this, view));
            mPresenter.onAttach(this);
        }




        return view;
    }

    @OnClick(R.id.supplier_profile_toolbar_edit)
    public void editPro(){
        mPresenter.getMvpView().setVisibility(AppConstants.TAG_EDIT);
        mPresenter.getMvpView().setEnablity(true);
        mPresenter.getMvpView().showMessage(AppMessages.CHANGE_IMAGE);
    }


    @OnClick(R.id.supplier_profile_picture)
    public void openImagePicker(){
        mPresenter.getMvpView().openImagePick();

    }
    @OnClick(R.id.supplier_profile_card_phone)
    public void setPhone(){
        mPresenter.getMvpView().openChangesFragment(STATUS_PHONE);
    }

    @OnClick(R.id.supplier_profile_card_password)
    public void setPassword(){
        mPresenter.getMvpView().openChangesFragment(STATUS_PASSWORD);
    }

    @OnClick(R.id.supplier_profile_toolbar_drawer)
    public void openDrawerProfile(){
        mPresenter.onDrawerClick();
    }

    @OnClick(R.id.supplier_profile_save)
    public void onSaveProfile(){
        //selected times
        timeFromText=String.valueOf(timeFrom.getSelectedItem());
        timeToText=String.valueOf(timeTo.getSelectedItem());


        if(filePath!=null && uriImage!=null){
            Log.i("PATH", uriImage.toString());

            File file = new File(filePath);


            RequestBody filePart = RequestBody.create(MediaType.parse("image/*"), file);

            MultipartBody.Part body =
                    MultipartBody.Part.createFormData("image", file.getName(), filePart);


             mPresenter.onSaveClick(name.getEditText().getText().toString(),
                     organizer.getEditText().getText().toString(),
                     body,
                     address.getEditText().getText().toString(),timeFromText,timeToText);

        }
        else{
            mPresenter.onSaveClickWithoutImage(name.getEditText().getText().toString(),
                    organizer.getEditText().getText().toString(),
                    address.getEditText().getText().toString(),timeFromText,timeToText);

        }
    }

    @Override
    public void openDrawer() {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    @Override
    public void updateProfile() {
        if(mPresenter.getDataManager().getCurrentSupplierOrganizationName()!=null){
            name.getEditText().setText(mPresenter.getDataManager().getCurrentSupplierOrganizationName()
                    .replace("\"",""));
        }
        if(mPresenter.getDataManager().getCurrentSupplierAddress()!=null){
            address.getEditText().setText(mPresenter.getDataManager().getCurrentSupplierAddress().replace("\"",""));
        }
        if(mPresenter.getDataManager().getCurrentSupplierUsername()!=null){
            organizer.getEditText().setText(mPresenter.getDataManager().getCurrentSupplierUsername().replace("\"",""));
        }
        if(mPresenter.getDataManager().getCurrentSupplierTimeFrom()!=null){
            //timeFrom.setText(mPresenter.getDataManager().getCurrentSupplierOrganizationName());
        }
        if(mPresenter.getDataManager().getCurrentSupplierTimeTo()!=null){
            //timeTo.getEditText().setText(mPresenter.getDataManager().getCurrentSupplierTimeTo());
        }if(mPresenter.getDataManager().getCurrentSupplierPhone()!=null){
            phone.setText(mPresenter.getDataManager().getCurrentSupplierPhone());
        }
       /* if(mPresenter.getDataManager().getCurrentSupplierImage()!=null){
            Glide.with(this).load(AppConstants.BASE_URL_IMAGE+mPresenter.getDataManager().getCurrentSupplierImage()).into(photo);
        }*/
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
    public void openChangesFragment(int status) {
        ChangeInfoFragment changeInfoFragment=new ChangeInfoFragment();
        bundle=new Bundle();
        bundle.putInt(STATUS_KEY,status);
        bundle.putInt(PASS_PHONE_CHANGE,AppConstants.USER_TYPE_SUPPLIER);
        changeInfoFragment.setArguments(bundle);

        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.supplier_content_frame, changeInfoFragment, TAG_MAIN)
                .hide(this)
                .addToBackStack(TAG_FRAGMENT_STACK)
                .commit();
    }

    @OnClick(R.id.supplier_card_logout)
    public void setLogout(){
        mPresenter.getMvpView().openExitDialog();
    }

    @Override
    public void setEnablity(Boolean status) {
        photo.setClickable(status);

        timeFrom.setEnabled(status);
        timeTo.setEnabled(status);

        name.getEditText().setFocusableInTouchMode(status);
        name.getEditText().setFocusable(status);

        organizer.getEditText().setFocusableInTouchMode(status);
        organizer.getEditText().setFocusable(status);

        address.getEditText().setFocusableInTouchMode(status);
        address.getEditText().setFocusable(status);

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
    public void openImagePick() {
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
    public void logOut() {
        mPresenter.getDataManager().setSupplierAsLoggedOut();
        Intent intent=new Intent(getActivity(), StartActivity.class);
        startActivity(intent);
    }

    @Override
    public void onImageReceive(String imagelink,String from,String to) {
        Glide.with(this).load(AppConstants.BASE_URL_IMAGE+imagelink).into(photo);
        IMAGE_LINK=AppConstants.BASE_URL_IMAGE+imagelink;
        Log.d("request", "onImageReceive: from  "+mPresenter.getDataManager().getCurrentSupplierTimeFrom());
        Log.d("request", "onImageReceive: to  "+mPresenter.getDataManager().getCurrentSupplierTimeTo());
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
                mPresenter.getMvpView().logOut();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            com.esafirm.imagepicker.model.Image image = ImagePicker.getFirstImageOrNull(data);
            filePath=image.getPath();

            File f = new File(filePath);  //
            uriImage = Uri.fromFile(f);

            Glide.with(getActivity()).load(filePath).into(photo);


        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void setUp(View view) {
        mPresenter.onViewPrepared();
        mPresenter.getMvpView().setEnablity(false);
        mPresenter.getMvpView().setVisibility(AppConstants.TAG_SAVE);
        mPresenter.onImageRequest();
        for(int i=0;i<timeFrom.getCount();i++){
            if(timeFrom.getItemAtPosition(i).equals(mPresenter.getDataManager().
                    getCurrentSupplierTimeFrom().replace("\"",""))){
                timeFrom.setSelection(i);
            }
        }
        for(int i=0;i<timeTo.getCount();i++){
            if(timeTo.getItemAtPosition(i).equals(mPresenter.getDataManager().getCurrentSupplierTimeTo().replace("\"",""))){
                timeTo.setSelection(i);
            }
        }
        Log.d("myTag", "setUp:spinner "+String.valueOf(timeFrom.getItemAtPosition(5)));

    }

    @Override
    public void onDestroyView() {
        mPresenter.onDetach();
        super.onDestroyView();
    }
}
