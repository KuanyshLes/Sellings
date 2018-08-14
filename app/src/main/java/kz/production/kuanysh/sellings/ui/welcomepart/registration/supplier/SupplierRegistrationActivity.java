package kz.production.kuanysh.sellings.ui.welcomepart.registration.supplier;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.model.Image;
import com.facebook.accountkit.Account;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccountKitCallback;
import com.facebook.accountkit.AccountKitError;
import com.facebook.accountkit.PhoneNumber;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kz.production.kuanysh.sellings.R;
import kz.production.kuanysh.sellings.data.network.model.supplier.registration.SupplierRegistration;
import kz.production.kuanysh.sellings.ui.base.BaseActivity;
import kz.production.kuanysh.sellings.ui.content_suppiler.activity.SupplierActivity;
import kz.production.kuanysh.sellings.ui.welcomepart.login.LoginActivity;
import kz.production.kuanysh.sellings.ui.welcomepart.registration.owner.OwnerRegistrationActivity;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class SupplierRegistrationActivity extends BaseActivity implements SupplierRegistrationMvpView {

    @Inject
    SupplierRegistrationPresenter<SupplierRegistrationMvpView> mPresenter;

    @BindView(R.id.supplier_registration_name)
    TextInputLayout name;

    @BindView(R.id.supplier_registration_organizer)
    TextInputLayout organizer;

    @BindView(R.id.supplier_registration_address)
    TextInputLayout address;

    @BindView(R.id.supplier_registration_password)
    TextInputLayout password;

    @BindView(R.id.supplier_registration_confirm_password)
    TextInputLayout confirm_password;

    @BindView(R.id.supplier_registration_time_from)
    Spinner timeFrom;

    @BindView(R.id.supplier_registration_time_to)
    Spinner timeTo;

    @BindView(R.id.supplier_registration_save)
    Button save_btn;

    @BindView(R.id.supplier_registration_image)
    ImageView image;


    private String timeFromText,timeToText;
    private Intent intent;
    private static Uri uriImage;
    private static String filePath;
    public static final String TAG_SUPPLIER_REGISTRATION="registration";
    public static final String TAG_SUPPLIER_CURRENT="logged in";
    public static Long PHONE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplier_registration);
        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(SupplierRegistrationActivity.this);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setUp();

    }


    @Override
    protected void setUp() {
        getAccount();
    }

    @OnClick(R.id.supplier_registration_save)
    public void regSupp(){
        if(filePath!=null && uriImage!=null){
            Log.i("PATH", uriImage.toString());

            File file = new File(filePath);

            RequestBody filePart = RequestBody.create(MediaType.parse("image/*"), file);

            MultipartBody.Part body =
                    MultipartBody.Part.createFormData("image", file.getName(), filePart);

            if(PHONE!=null){
                mPresenter.onSaveClick(name.getEditText().getText().toString(),
                        organizer.getEditText().getText().toString(),
                        address.getEditText().getText().toString(),
                        body,
                        String.valueOf(timeFrom.getSelectedItem()),
                        String.valueOf(timeTo.getSelectedItem()),
                        password.getEditText().getText().toString(),
                        confirm_password.getEditText().getText().toString(),PHONE);
            }
            else {
                mPresenter.getMvpView().onError("Something went wrong!");
            }



        }
        else{
            mPresenter.getMvpView().showMessage("Tap to image to select!");

        }
    }

    private void getAccount(){
        AccountKit.getCurrentAccount(new AccountKitCallback<Account>() {
            @Override
            public void onSuccess(final Account account) {
                // Get Account Kit ID
                String accountKitId = account.getId();

                // Get phone number
                PhoneNumber phoneNumber = account.getPhoneNumber();
                PHONE= Long.valueOf(phoneNumber.toString());

               // mPresenter.getMvpView().showMessage(PHONE+"");


            }

            @Override
            public void onError(final AccountKitError error) {
                Log.e("AccountKit",error.toString());
                // Handle Error
            }
        });

    }

    @OnClick(R.id.supplier_registration_image)
    public void openImageSupp(){
        mPresenter.onImageclick();
    }

    @Override
    public void openSupplierActivity() {
        intent=new Intent(SupplierRegistrationActivity.this, SupplierActivity.class);
        intent.putExtra(LoginActivity.TAG_SUPPLIER_KEY,TAG_SUPPLIER_REGISTRATION);
        startActivity(intent);
    }

    @Override
    public void openImagePicker() {
        ImagePicker.create(this)
                .folderMode(true) // folder mode (false by default)
                .toolbarFolderTitle("Folder") // folder selection title
                .toolbarImageTitle("Tap to select") // image selection title
                .toolbarArrowColor(Color.BLACK) // Toolbar 'up' arrow color
                .single() // single mode
                .limit(1) // max images can be selected (99 by default)
                .showCamera(true) // show camera or not (true by default)
                .imageDirectory("Camera") // directory name for captured image  ("Camera" folder by default)
                .enableLog(false) // disabling log
                .start(); // start image picker activity with request code
    }

    @Override
    protected void onActivityResult(int requestCode, final int resultCode, Intent data) {
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            com.esafirm.imagepicker.model.Image imageResult = ImagePicker.getFirstImageOrNull(data);
            filePath=imageResult.getPath();

            File f = new File(filePath);  //
            uriImage = Uri.fromFile(f);

            final InputStream inputStream;
            try {
                inputStream = getContentResolver().openInputStream(uriImage);
                Bitmap imageMap = BitmapFactory.decodeStream(inputStream);
                image.setImageBitmap(imageMap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }


            //uriImage=image.

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDetach();
        super.onDestroy();
    }
}
