package kz.production.kuanysh.sellings.ui.content_suppiler.fragments.statistics;


import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import kz.production.kuanysh.sellings.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class StatisticsFragment extends Fragment {
    private TextView orderAmount,incomeAmount,debtorsAmount;
    private ImageView back;
    private DrawerLayout drawerLayout;

    public StatisticsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_statistics, container, false);

        //initialize elements
        drawerLayout=(DrawerLayout)getActivity().findViewById(R.id.supplier_drawer_layout);
        orderAmount=(TextView)view.findViewById(R.id.supplier_statistics_order_amount);
        orderAmount=(TextView)view.findViewById(R.id.supplier_statistics_income_amount);
        orderAmount=(TextView)view.findViewById(R.id.supplier_statistics_debtors_amount);
        back=(ImageView)view.findViewById(R.id.supplier_statistics_toolbar_drawer);

        //open drawer
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });


        return view;
    }

}
