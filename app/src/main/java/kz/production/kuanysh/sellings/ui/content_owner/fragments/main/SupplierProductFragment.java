package kz.production.kuanysh.sellings.ui.content_owner.fragments.main;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
import kz.production.kuanysh.sellings.ui.content_owner.utils.Listener;
import kz.production.kuanysh.sellings.ui.content_owner.utils.adapters.SupplierProductsAdapter;

import static kz.production.kuanysh.sellings.ui.content_owner.MainActivity.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class SupplierProductFragment extends Fragment {
    private RecyclerView supplier_products;
    private SupplierProductsAdapter supplierProductsAdapter;
    private LinearLayoutManager linearLayoutManager;
    private List<String> supplierItemList;
    private ImageView back;
    private MainFragment mainFragment;
    private FragmentTransaction fragmentTransaction;

    public SupplierProductFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_supplier_product, container, false);

        back=(ImageView)view.findViewById(R.id.supplier_product_toolbar_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainFragment=new MainFragment();
                fragmentTransaction= ((AppCompatActivity)getActivity()).getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.content_frame,mainFragment, TAG).commit();
            }
        });
        supplierItemList=new ArrayList<>();
        supplierItemList.add("Молоко");
        supplierItemList.add("Мясо");
        supplierItemList.add("Сыр");
        supplierItemList.add("Молоко");
        supplierItemList.add("Мясо");
        supplierItemList.add("Сыр");
        supplierItemList.add("Молоко");
        supplierItemList.add("Мясо");
        supplierItemList.add("Сыр");


        supplier_products=(RecyclerView)view.findViewById(R.id.supplier_products);
        linearLayoutManager=new LinearLayoutManager(getActivity());
        supplierProductsAdapter=new SupplierProductsAdapter(supplierItemList,getActivity());

        supplier_products.setLayoutManager(linearLayoutManager);
        supplier_products.setAdapter(supplierProductsAdapter);

        supplierProductsAdapter.setListener(new Listener() {
            @Override
            public void onClick(int position) {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, new SupplierProductSubcategoryFragment(), TAG);
                ft.addToBackStack(null);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();
            }
        });



        return view;
    }




}
