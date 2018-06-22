package kz.production.kuanysh.sellings.ui.content_owner.fragments.main;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import kz.production.kuanysh.sellings.R;
import kz.production.kuanysh.sellings.ui.content_owner.fragments.main.SupplierProductFragment;
import kz.production.kuanysh.sellings.ui.content_owner.utils.adapters.MultiTypeCategoryAdapter;

import static kz.production.kuanysh.sellings.ui.content_owner.utils.SubcategoryDataFactory.makeGenres;
import static kz.production.kuanysh.sellings.ui.content_owner.MainActivity.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class SupplierProductSubcategoryFragment extends Fragment {
    private RecyclerView recyclerSubCategory;
    private ImageView back;
    private LinearLayoutManager linearLayoutManager;
    private MultiTypeCategoryAdapter multiTypeCategoryAdapter;

    public SupplierProductSubcategoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_supplier_product_subcategory, container, false);
        back=(ImageView)view.findViewById(R.id.supplier_product_subcategory_toolbar_back);
        gobacklistener();

        linearLayoutManager=new LinearLayoutManager(getActivity());
        recyclerSubCategory=(RecyclerView)view.findViewById(R.id.supplier_products_subcategories);
        multiTypeCategoryAdapter=new MultiTypeCategoryAdapter(makeGenres(),getActivity());

        recyclerSubCategory.setLayoutManager(linearLayoutManager);
        recyclerSubCategory.setAdapter(multiTypeCategoryAdapter);


        return view;
    }


    private void gobacklistener(){
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SupplierProductFragment supplierProductFragment=new SupplierProductFragment();
                setFragment(supplierProductFragment);
            }
        });
    }


    private void setFragment(Fragment fragment){
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, fragment, TAG);
        ft.addToBackStack(null);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }

}
