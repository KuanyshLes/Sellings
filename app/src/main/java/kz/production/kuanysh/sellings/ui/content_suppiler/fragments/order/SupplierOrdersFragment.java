package kz.production.kuanysh.sellings.ui.content_suppiler.fragments.order;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import kz.production.kuanysh.sellings.R;
import kz.production.kuanysh.sellings.model.OrderItem;
import kz.production.kuanysh.sellings.model.OwnerItem;
import kz.production.kuanysh.sellings.ui.content_owner.utils.Listener;
import kz.production.kuanysh.sellings.ui.content_suppiler.utils.adapters.SupplierOrderAdapter;

import static kz.production.kuanysh.sellings.ui.content_suppiler.SupplierActivity.SUPPLIER_VISIBLE_FRAGMENT_TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class SupplierOrdersFragment extends Fragment {
    private ImageView back;
    private RecyclerView supplierOrdersList;
    private LinearLayoutManager linearLayoutManager;
    private SupplierOrderAdapter supplierOrderAdapter;
    private DrawerLayout drawerLayout;
    private List<OwnerItem> ownerItemList;
    private Bundle bundle;
    public static final String TAG_ORDER_DETAIL="key";

    public SupplierOrdersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_supplier_orders, container, false);


        drawerLayout=(DrawerLayout)getActivity().findViewById(R.id.supplier_drawer_layout);
        back=(ImageView)view.findViewById(R.id.supplier_orders_toolbar_drawer);
        opendrawerlistener();


        linearLayoutManager=new LinearLayoutManager(getActivity());
        supplierOrderAdapter=new SupplierOrderAdapter(setCustomInfo(),getActivity());

        supplierOrdersList=(RecyclerView)view.findViewById(R.id.supplier_orders);
        supplierOrdersList.setAdapter(supplierOrderAdapter);
        supplierOrdersList.setLayoutManager(linearLayoutManager);

        supplierOrderAdapter.setListener(new Listener() {
            @Override
            public void onClick(int position) {
                SupplierOrderDetailFragment supplierOrderDetailFragment=new SupplierOrderDetailFragment();
                bundle=new Bundle();
                bundle.putParcelable(TAG_ORDER_DETAIL,ownerItemList.get(position));
                supplierOrderDetailFragment.setArguments(bundle);
                setFragment(supplierOrderDetailFragment);
            }
        });
        return view;

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
    private void setFragment(Fragment fragment){
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.supplier_content_frame, fragment, SUPPLIER_VISIBLE_FRAGMENT_TAG)
                .addToBackStack(null)
                .commit();
    }

    //set custom info
    private List<OwnerItem> setCustomInfo(){
        ownerItemList=new ArrayList<>();
        ownerItemList.add(new OwnerItem("Арман Трендс конфет",
                "21.01.2018",
                "Ул.Байтурсынова 56а",
                OwnerItem.STATUS_WAITING,OwnerItem.CONSIGNMENT_YES,OwnerItem.CASH_YES));
        ownerItemList.add(new OwnerItem("Арман Трендс конфет",
                "21.01.2018",
                "Ул.Байтурсынова 56а",
                OwnerItem.STATUS_CANCELLED,OwnerItem.CONSIGNMENT_NO,OwnerItem.CASH_YES));
        ownerItemList.add(new OwnerItem("Арман Трендс конфет",
                "21.01.2018",
                "Ул.Байтурсынова 56а",
                OwnerItem.STATUS_CONFIRMED,OwnerItem.CONSIGNMENT_YES,OwnerItem.CASH_NO));
        ownerItemList.add(new OwnerItem("Арман Трендс конфет",
                "21.01.2018",
                "Ул.Байтурсынова 56а",
                OwnerItem.STATUS_WAITING,OwnerItem.CONSIGNMENT_NO,OwnerItem.CASH_YES));
        ownerItemList.add(new OwnerItem("Арман Трендс конфет",
                "21.01.2018",
                "Ул.Байтурсынова 56а",
                OwnerItem.STATUS_CANCELLED,OwnerItem.CONSIGNMENT_YES,OwnerItem.CASH_NO));
        ownerItemList.add(new OwnerItem("Арман Трендс конфет",
                "21.01.2018",
                "Ул.Байтурсынова 56а",
                OwnerItem.STATUS_CONFIRMED,OwnerItem.CONSIGNMENT_NO,OwnerItem.CASH_YES));
        ownerItemList.add(new OwnerItem("Арман Трендс конфет",
                "21.01.2018",
                "Ул.Байтурсынова 56а",
                OwnerItem.STATUS_WAITING,OwnerItem.CONSIGNMENT_NO,OwnerItem.CASH_NO));
        ownerItemList.add(new OwnerItem("Арман Трендс конфет",
                "21.01.2018",
                "Ул.Байтурсынова 56а",
                OwnerItem.STATUS_CONFIRMED,OwnerItem.CONSIGNMENT_YES,OwnerItem.CASH_YES));

        return  ownerItemList;
    }

}
