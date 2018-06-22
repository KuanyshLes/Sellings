package kz.production.kuanysh.sellings.ui.content_suppiler.fragments.goods;


import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import kz.production.kuanysh.sellings.R;

import static kz.production.kuanysh.sellings.ui.content_suppiler.SupplierActivity.SUPPLIER_VISIBLE_FRAGMENT_TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class SupplierAddGoodFragment extends Fragment {
    private ImageView back;
    private static Spinner category,subcategory;
    private TextInputLayout name,amount,price;
    private Button save_btn;
    private static String categoryText=null;
    private static String subCategoryText=null;


    public SupplierAddGoodFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_supplier_add_good, container, false);

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        back=(ImageView)view.findViewById(R.id.supplier_add_goods_toolbar_drawer);
        gobacklistener();

        category=(Spinner)view.findViewById(R.id.supplier_add_goods_edit_category);
        subcategory=(Spinner)view.findViewById(R.id.supplier_edit_goods_add_subcategory);

        name=(TextInputLayout)view.findViewById(R.id.supplier_goods_add_name);
        amount=(TextInputLayout)view.findViewById(R.id.supplier_goods_add_amount);
        price=(TextInputLayout)view.findViewById(R.id.supplier_goods_add_price);

        save_btn=(Button) view.findViewById(R.id.supplier_goods_add_save_btn);



        final List<String> categoryList = new ArrayList<String>();
        categoryList.add("Молоко");
        categoryList.add("Мясо");
        categoryList.add("Конфет");
        categoryList.add("Молоко");
        categoryList.add("Мясо");
        categoryList.add("Конфет");

        final List<String> subCategoryList= new ArrayList<>();
        subCategoryList.add("Молоко");
        subCategoryList.add("Мясо");
        subCategoryList.add("Конфет");

        subCategoryList.add("Молоко");
        subCategoryList.add("Мясо");
        subCategoryList.add("Конфет");

        //get categories

        categoryText=getCategories(categoryList,subCategoryList).get(0);
        subCategoryText=getCategories(categoryList,subCategoryList).get(1);


        return view;
    }

    //open drawer
    private void gobacklistener(){
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.supplier_content_frame, new SupplierGoodsFragment(), SUPPLIER_VISIBLE_FRAGMENT_TAG)
                        .addToBackStack(null)
                        .commit();
            }
        });

    }
    private List<String> getCategories(final List<String> categoryList, List<String> subcategeryList){
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, categoryList);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category.setAdapter(categoryAdapter);

        ArrayAdapter<String> subCategoryAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, subcategeryList);
        subCategoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        subcategory.setAdapter(subCategoryAdapter);

        List<String> categories=new ArrayList<>();
        categories.add(String.valueOf(category.getSelectedItem()));
        categories.add(String.valueOf(subcategory.getSelectedItem()));

        return categories;
    }

}
