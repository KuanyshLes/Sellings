package kz.production.kuanysh.sellings.ui.content_owner.fragments.profile;


import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import kz.production.kuanysh.sellings.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {
    private ImageView back,edit;
    private RadioGroup radioGroup;
    private TextInputLayout name,place,iin,address;
    private TextView phone_number,change_password;
    private Button save_btn;
    private static final String TAG_EDIT="edit";
    private static final String TAG_SAVE="save";
    private DrawerLayout drawer;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_profile, container, false);

        drawer = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        back=(ImageView)view.findViewById(R.id.profile_toolbar_back);
        edit=(ImageView)view.findViewById(R.id.profile_toolbar_edit);

        radioGroup=(RadioGroup)view.findViewById(R.id.profile_radiogroup);

        name=(TextInputLayout)view.findViewById(R.id.profile_name);
        iin=(TextInputLayout)view.findViewById(R.id.profile_iin);
        place=(TextInputLayout)view.findViewById(R.id.profile_place);
        address=(TextInputLayout)view.findViewById(R.id.profile_address);

        phone_number=(TextView)view.findViewById(R.id.profile_phone_number);
        change_password=(TextView)view.findViewById(R.id.profile_change_password);
        save_btn=(Button)view.findViewById(R.id.profile_save_btn);

        edit_credentials();
        changeable(TAG_SAVE);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.profile_phys){
                    //do something
                }if(checkedId==R.id.profile_lawyer){
                    //do something
                }

            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setvisibility(TAG_EDIT);
            }
        });
        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setvisibility(TAG_SAVE);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(GravityCompat.START);
            }
        });


        return view;
    }
    private void setvisibility(String status){
        if(status.equals(TAG_EDIT)){
            edit.setVisibility(View.GONE);
            save_btn.setVisibility(View.VISIBLE);
            changeable(TAG_EDIT);
        }else if(status.equals(TAG_SAVE)){
            save_btn.setVisibility(View.GONE);
            edit.setVisibility(View.VISIBLE);
            changeable(TAG_SAVE);
        }
    }
    private void edit_credentials(){
        name.getEditText().setText("Магазин Адия");
        iin.getEditText().setText("6546515151651");
        place.getEditText().setText("Алмалинский");
        address.getEditText().setText("Шевченко №12");


    }
    private void changeable(String status){
        List<TextInputLayout> textInputLayouts=new ArrayList<>();
        textInputLayouts.add(name);
        textInputLayouts.add(iin);
        textInputLayouts.add(place);
        textInputLayouts.add(address);
        Boolean changeable=null;
        if(status.equals(TAG_EDIT)){
            changeable=true;
        }if(status.equals(TAG_SAVE)){
            changeable=false;
        }
        for(int i=0;i<textInputLayouts.size();i++){
            textInputLayouts.get(i).setClickable(changeable);
            textInputLayouts.get(i).setFocusable(changeable);
            //Toast.makeText(getActivity(), textInputLayouts.get(i).getEditText().getText()+" ", Toast.LENGTH_SHORT).show();
            //Toast.makeText(getActivity(), "Is focusable "+textInputLayouts.get(i).isFocusable(), Toast.LENGTH_SHORT).show();
            //Toast.makeText(getActivity(), "Is clickable "+textInputLayouts.get(i).isClickable(), Toast.LENGTH_SHORT).show();

        }
    }


}
