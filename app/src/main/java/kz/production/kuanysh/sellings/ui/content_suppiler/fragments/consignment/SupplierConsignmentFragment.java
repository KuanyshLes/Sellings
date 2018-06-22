package kz.production.kuanysh.sellings.ui.content_suppiler.fragments.consignment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import kz.production.kuanysh.sellings.R;
import kz.production.kuanysh.sellings.model.ConsignmentItem;
import kz.production.kuanysh.sellings.ui.content_owner.utils.Listener;
import kz.production.kuanysh.sellings.ui.content_suppiler.SupplierActivity;
import kz.production.kuanysh.sellings.ui.content_suppiler.fragments.consignment.ConsignmentItemControlFragment;
import kz.production.kuanysh.sellings.ui.content_suppiler.utils.adapters.SupplierConsignmentAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class SupplierConsignmentFragment extends Fragment {
    private ImageView back;
    private RecyclerView consignment_recycler;
    private SupplierConsignmentAdapter supplierConsignmentAdapter;
    private LinearLayoutManager linearLayoutManager;
    private List<ConsignmentItem> consignmentCustomList;
    private DrawerLayout drawerLayout;
    public static final String KEY_CONSIGNMENT="just key";
    private Bundle bundle;


    public SupplierConsignmentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_supplier_consignment, container, false);

        drawerLayout=(DrawerLayout)getActivity().findViewById(R.id.supplier_drawer_layout);
        back=(ImageView)view.findViewById(R.id.supplier_consignment_toolbar_drawer);
        opendrawerlistener();

        consignmentCustomList=new ArrayList<>();
        setCustomInfo();


        linearLayoutManager=new LinearLayoutManager(getActivity());
        supplierConsignmentAdapter=new SupplierConsignmentAdapter(consignmentCustomList,getActivity());

        consignment_recycler=(RecyclerView)view.findViewById(R.id.supplier_сonsignment_recycler);
        consignment_recycler.setLayoutManager(linearLayoutManager);
        consignment_recycler.setItemAnimator(new DefaultItemAnimator());
        consignment_recycler.setAdapter(supplierConsignmentAdapter);


        supplierConsignmentAdapter.setListener(new Listener() {
            @Override
            public void onClick(int position) {
                ConsignmentItemControlFragment consignmentItemControlFragment=new ConsignmentItemControlFragment();
                bundle = new Bundle();
                bundle.putParcelable(KEY_CONSIGNMENT, consignmentCustomList.get(position));
                consignmentItemControlFragment.setArguments(bundle);
                FragmentTransaction transaction= ((AppCompatActivity)getActivity()).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.supplier_content_frame,consignmentItemControlFragment, SupplierActivity.SUPPLIER_VISIBLE_FRAGMENT_TAG).commit();
            }
        });

        return view;
    }

    private void setCustomInfo() {
        consignmentCustomList.add(new ConsignmentItem("ИП Трендс Конфет", "31.05.2018", "1680"));
        consignmentCustomList.add(new ConsignmentItem("ИП Арман Конфет", "17.12.2014", "9990"));
        consignmentCustomList.add(new ConsignmentItem("ИП Арман Трендс", "18.08.2015", "2150"));

        consignmentCustomList.add(new ConsignmentItem("ИП Трендс Конфет", "31.05.2018", "1680"));
        consignmentCustomList.add(new ConsignmentItem("ИП Арман Конфет", "17.12.2014", "9990"));
        consignmentCustomList.add(new ConsignmentItem("ИП Арман Трендс", "18.08.2015", "2150"));

        consignmentCustomList.add(new ConsignmentItem("ИП Трендс Конфет", "31.05.2018", "1680"));
        consignmentCustomList.add(new ConsignmentItem("ИП Арман Конфет", "17.12.2014", "9990"));
        consignmentCustomList.add(new ConsignmentItem("ИП Арман Трендс", "18.08.2015", "2150"));

    }
    //open drawer
    private void opendrawerlistener(){
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

    }

}
