package kz.production.kuanysh.sellings.ui.content_suppiler.fragments.order;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.security.acl.Owner;
import java.util.ArrayList;
import java.util.List;

import kz.production.kuanysh.sellings.R;
import kz.production.kuanysh.sellings.model.OrderItem;
import kz.production.kuanysh.sellings.model.OwnerItem;
import kz.production.kuanysh.sellings.model.Product;
import kz.production.kuanysh.sellings.ui.content_owner.fragments.order.OrdersFragment;
import kz.production.kuanysh.sellings.ui.content_owner.utils.Colors;
import kz.production.kuanysh.sellings.ui.content_suppiler.utils.adapters.SupplierOrderAdapter;
import kz.production.kuanysh.sellings.ui.content_suppiler.utils.adapters.SupplierOrderDetailAdapter;

import static kz.production.kuanysh.sellings.ui.content_suppiler.SupplierActivity.SUPPLIER_VISIBLE_FRAGMENT_TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class SupplierOrderDetailFragment extends Fragment {
    private static TextView name,status,cash,consignment;
    private Button cancell,approve;
    private RecyclerView orders;
    private LinearLayoutManager linearLayoutManager;
    private SupplierOrderDetailAdapter supplierOrderDetailAdapter;
    private ImageView back;
    private OwnerItem ownerItem;
    private static List<Product> productList;

    public SupplierOrderDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_supplier_order_detail, container, false);
        final Bundle bundle = getArguments();
        if (bundle != null){
            ownerItem = (OwnerItem) bundle.get(SupplierOrdersFragment.TAG_ORDER_DETAIL);
        }


        productList=new ArrayList<>();
        back=(ImageView) view.findViewById(R.id.supplier_detail_orders_toolbar_drawer);
        gobacklistener();

        name=(TextView)view.findViewById(R.id.supplier_order_detail_name);
        status=(TextView)view.findViewById(R.id.supplier_order_detail_status);
        cash=(TextView)view.findViewById(R.id.supplier_order_detail_cash);
        consignment=(TextView)view.findViewById(R.id.supplier_order_detail_consignment);
        setInfo(ownerItem);

        cancell=(Button)view.findViewById(R.id.supplier_order_detail_cancell_btn);
        approve=(Button) view.findViewById(R.id.supplier_order_detail_approve_btn);

        setCustomInfoToList();
        orders=(RecyclerView)view.findViewById(R.id.supplier_order_detail_list);

        linearLayoutManager=new LinearLayoutManager(getActivity());
        supplierOrderDetailAdapter=new SupplierOrderDetailAdapter(productList,getActivity());
        orders.setLayoutManager(linearLayoutManager);
        orders.setAdapter(supplierOrderDetailAdapter);


        return view;

    }

    private void gobacklistener(){
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.supplier_content_frame, new SupplierOrdersFragment(), SUPPLIER_VISIBLE_FRAGMENT_TAG)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    private void setInfo(OwnerItem ownerItem){
        name.setText(ownerItem.getName());
        status.setText(ownerItem.getStatus());
        cash.setText(ownerItem.getCash());


        //check consignment
        if(ownerItem.getConsignment().equals(OwnerItem.CONSIGNMENT_NO)){
            consignment.setTextColor(Color.parseColor(Colors.CANCELLED_RED));
        }else{
            consignment.setText("");
        }

        //check status
        if(ownerItem.getStatus().equals(OwnerItem.STATUS_CONFIRMED)){
            status.setTextColor(Color.parseColor(Colors.CONFIRMED_GREEN));
        }else if (ownerItem.getStatus().equals(OwnerItem.STATUS_CANCELLED)){
            status.setTextColor(Color.parseColor(Colors.CANCELLED_RED));
        }
    }
    private void setCustomInfoToList(){
        productList.add(new Product("Молоко Моё,1.6",1,1600)) ;
        productList.add(new Product("Молоко Моё,3.2",1,1600)) ;
        productList.add(new Product("Молоко Моё,1.6",1,1600)) ;
        productList.add(new Product("Молоко Моё,3.2",1,1600)) ;
    }

}
