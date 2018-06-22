package kz.production.kuanysh.sellings.ui.content_suppiler.fragments.profile;


import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import kz.production.kuanysh.sellings.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SupplierProfileFragment extends Fragment {
    private TextInputLayout name,organizer,address;
    private Spinner timeFrom,timeTo;
    private Button save_btn;
    private String timeFromText,timeToText;
    private ImageView back;
    private DrawerLayout drawerLayout;
    private TextView phone,password;
    private CardView phoneCard,phonePassword;


    public SupplierProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_supplier_profile, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        back=(ImageView)view.findViewById(R.id.supplier_profile_toolbar_drawer);
        drawerLayout=(DrawerLayout)getActivity().findViewById(R.id.supplier_drawer_layout);

        opendrawerlistener();

        name=(TextInputLayout)view.findViewById(R.id.supplier_profile_name);
        organizer=(TextInputLayout)view.findViewById(R.id.supplier_profile_organizer);
        address=(TextInputLayout)view.findViewById(R.id.supplier_profile_address);

        timeFrom=(Spinner)view.findViewById(R.id.supplier_profile_time_from);
        timeTo=(Spinner)view.findViewById(R.id.supplier_profile_time_to);

        phoneCard=(CardView)view.findViewById(R.id.supplier_profile_card_phone);
        phonePassword=(CardView)view.findViewById(R.id.supplier_profile_card_password);

        phone=(TextView)view.findViewById(R.id.supplier_profile_phone_number);
        phone=(TextView)view.findViewById(R.id.supplier_profile_change_password);

        save_btn=(Button)view.findViewById(R.id.supplier_profile_save);

        //selected times
        timeFromText=String.valueOf(timeFrom.getSelectedItem());
        timeToText=String.valueOf(timeTo.getSelectedItem());


        return view;
    }
    private void opendrawerlistener(){
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

    }

}
