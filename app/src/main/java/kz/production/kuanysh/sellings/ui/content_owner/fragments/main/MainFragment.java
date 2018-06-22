package kz.production.kuanysh.sellings.ui.content_owner.fragments.main;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import kz.production.kuanysh.sellings.R;
import kz.production.kuanysh.sellings.model.SupplierItem;
import kz.production.kuanysh.sellings.ui.content_owner.fragments.basket.BasketFragment;
import kz.production.kuanysh.sellings.ui.content_owner.utils.Listener;
import kz.production.kuanysh.sellings.ui.content_owner.utils.adapters.SupplierAdapter;

import static kz.production.kuanysh.sellings.ui.content_owner.MainActivity.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    private RecyclerView supplier_list;
    private SupplierAdapter supplierAdapter;
    private LinearLayoutManager linearLayoutManager;
    private List<SupplierItem> list;
    private ImageView drawerHelper,basket;
    private DrawerLayout drawer;
    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_main, container, false);


        supplier_list=(RecyclerView)view.findViewById(R.id.supplier_list);
        drawerHelper=(ImageView)view.findViewById(R.id.main_toolbar_drawer);
        basket=(ImageView)view.findViewById(R.id.main_toolbar_shopping);



        drawerHelper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
                drawer.openDrawer(GravityCompat.START);
            }
        });
        //product.setOnClickListener((View.OnClickListener) getActivity());

        goToBasket();

        list=new ArrayList<>();
        list.add(new SupplierItem("Арман трендс конфет","конфеты,печении,сладости","09.00-24.00","Ул.Байтурсынова 56а"));
        list.add(new SupplierItem("Арман трендс конфет","конфеты,печении,сладости","09.00-24.00","Ул.Байтурсынова 56а"));
        list.add(new SupplierItem("Арман трендс конфет","конфеты,печении,сладости","09.00-24.00","Ул.Байтурсынова 56а"));
        list.add(new SupplierItem("Арман трендс конфет","конфеты,печении,сладости","09.00-24.00","Ул.Байтурсынова 56а"));
        list.add(new SupplierItem("Арман трендс конфет","конфеты,печении,сладости","09.00-24.00","Ул.Байтурсынова 56а"));
        list.add(new SupplierItem("Арман трендс конфет","конфеты,печении,сладости","09.00-24.00","Ул.Байтурсынова 56а"));
        list.add(new SupplierItem("Арман трендс конфет","конфеты,печении,сладости","09.00-24.00","Ул.Байтурсынова 56а"));
        list.add(new SupplierItem("Арман трендс конфет","конфеты,печении,сладости","09.00-24.00","Ул.Байтурсынова 56а"));
        list.add(new SupplierItem("Арман трендс конфет","конфеты,печении,сладости","09.00-24.00","Ул.Байтурсынова 56а"));

        linearLayoutManager=new LinearLayoutManager(getActivity());
        supplierAdapter=new SupplierAdapter(list,getActivity());

        supplier_list.setLayoutManager(linearLayoutManager);
        supplier_list.setAdapter(supplierAdapter);


        supplierAdapter.setListener(new Listener() {
            @Override
            public void onClick(int position) {
                SupplierProductFragment supplierProductFragment=new SupplierProductFragment();
                FragmentTransaction transaction= ((AppCompatActivity)getActivity()).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.content_frame,supplierProductFragment, TAG).commit();
            }
        });

        return view;
    }
    private void goToBasket(){
        basket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BasketFragment basketFragment=new BasketFragment();
                FragmentTransaction transaction= ((AppCompatActivity)getActivity()).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.content_frame,basketFragment, TAG).commit();
            }
        });
    }

//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.main_toolbar_drawer:
//                drawer = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
//                drawer.openDrawer(GravityCompat.START);
//            case R.id.main_toolbar_shopping:
//                Toast.makeText(getActivity(), "Clicked", Toast.LENGTH_SHORT).show();
//        }
//
//    }
}
