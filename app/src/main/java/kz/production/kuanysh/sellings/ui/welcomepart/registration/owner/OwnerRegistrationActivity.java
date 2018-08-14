package kz.production.kuanysh.sellings.ui.welcomepart.registration.owner;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.esafirm.imagepicker.features.ImagePicker;
import com.facebook.accountkit.Account;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccountKitCallback;
import com.facebook.accountkit.AccountKitError;
import com.facebook.accountkit.PhoneNumber;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kz.production.kuanysh.sellings.R;
import kz.production.kuanysh.sellings.ui.base.BaseActivity;
import kz.production.kuanysh.sellings.ui.content_owner.main.MainActivity;
import kz.production.kuanysh.sellings.ui.welcomepart.login.LoginActivity;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class OwnerRegistrationActivity extends BaseActivity implements OwnerRegistrationMvpView {

    @Inject
    OwnerRegistrationPresenter<OwnerRegistrationMvpView> mPresenter;

    @BindView(R.id.owner_registration_radiogroup)
    RadioGroup radioGroup;

    @BindView(R.id.registration_as_lawyer)
    RadioButton lawyer;

    @BindView(R.id.registration_as_phys)
    RadioButton phys;


    @BindView(R.id.owner_registration_name)
    TextInputLayout name;

    @BindView(R.id.owner_registration_IIN)
    TextInputLayout iin;

    @BindView(R.id.owner_registration_place)
    TextInputLayout place;

    @BindView(R.id.owner_registration_address)
    TextInputLayout address;

    @BindView(R.id.owner_registration_password)
    TextInputLayout password;

    @BindView(R.id.owner_registration_confirm_password)
    TextInputLayout confirm_password;

    @BindView(R.id.owner_registration_save)
    Button save_btn;

    @BindView(R.id.owner_registration_image)
    ImageView profilePhoto;


    public static Long PHONE;

    private static Uri uriImage;
    private static String filePath;
    private static String imageString;
    private static  Bitmap imageMap;
    private static String SUBTYPE;
    public static final String LAWYER="Юридическое лицо";
    public static final String PHYS="Физическое лицо";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_owner);
        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(OwnerRegistrationActivity.this);
        setUp();

    }
    @Override
    protected void setUp() {
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getAccount();

    }

    @OnClick(R.id.owner_registration_image)
    public void openImagePickerC(){
        mPresenter.onImageClick();
    }


    @OnClick(R.id.owner_registration_save)
    public void ownerConfirmRegistration(){
        if(filePath!=null && uriImage!=null){
            Log.i("PATH", uriImage.toString());
            //FileUtils.getFile(uriImage);
            File file = new File(filePath);

            RequestBody filePart = RequestBody.create(MediaType.parse("image/*"), file);

            MultipartBody.Part body =
                    MultipartBody.Part.createFormData("image", file.getName(), filePart);

            if(lawyer.isChecked() || phys.isChecked()){
                switch (radioGroup.getCheckedRadioButtonId()) {
                    case R.id.registration_as_lawyer:
                        SUBTYPE = LAWYER;
                        break;
                    case R.id.registration_as_phys:
                        SUBTYPE = PHYS;
                        break;
                    default:
                        SUBTYPE = PHYS;
                        break;
                }
                        mPresenter.onRegister(String.valueOf(PHONE),
                                name.getEditText().getText().toString(),
                                iin.getEditText().getText().toString(),body
                                ,place.getEditText().getText().toString(),
                                password.getEditText().getText().toString(),
                                confirm_password.getEditText().getText().toString(),
                                address.getEditText().getText().toString(),
                                SUBTYPE);
            }else{
                mPresenter.getMvpView().onError("Select one of registration type");
            }


        }
        else{
            mPresenter.getMvpView().onError("Please tap to default icon to select profile photo");
        }

        //mPresenter.onRegistrationClick();
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


    @Override
    public void openLoginActivity() {
        Intent intent=new Intent(OwnerRegistrationActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void openMainActivity() {
        Intent intent=new Intent(OwnerRegistrationActivity.this, MainActivity.class);
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
    public  void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            com.esafirm.imagepicker.model.Image imageResult = ImagePicker.getFirstImageOrNull(data);
            filePath=imageResult.getPath();

            File f = new File(filePath);  //
            uriImage = Uri.fromFile(f);

            final InputStream inputStream;
            try {
                inputStream = getContentResolver().openInputStream(uriImage);
                Bitmap imageMap = BitmapFactory.decodeStream(inputStream);
                profilePhoto.setImageBitmap(imageMap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }


            //uriImage=image.

        }

    }
    public static String getPath(Context context, Uri uri ) {
        String result = null;
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = context.getContentResolver( ).query( uri, proj, null, null, null );
        if(cursor != null){
            if ( cursor.moveToFirst( ) ) {
                int column_index = cursor.getColumnIndexOrThrow( proj[0] );
                result = cursor.getString( column_index );
            }
            cursor.close( );
        }
        if(result == null) {
            result = "Not found";
        }
        return result;
    }


    @Override
    protected void onDestroy() {
        mPresenter.onDetach();
        super.onDestroy();
    }
}
