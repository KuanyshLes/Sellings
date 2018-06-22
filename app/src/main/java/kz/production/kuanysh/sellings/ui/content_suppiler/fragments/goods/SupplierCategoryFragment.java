package kz.production.kuanysh.sellings.ui.content_suppiler.fragments.goods;


import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import kz.production.kuanysh.sellings.R;
import kz.production.kuanysh.sellings.ui.content_owner.utils.Listener;
import kz.production.kuanysh.sellings.ui.content_suppiler.utils.adapters.SupplierCategoryAdapter;

import static kz.production.kuanysh.sellings.ui.content_suppiler.SupplierActivity.SUPPLIER_VISIBLE_FRAGMENT_TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class SupplierCategoryFragment extends Fragment {
    private ImageView back,addCategory;
    private RecyclerView category;
    private LinearLayoutManager linearLayoutManager;
    private static SupplierCategoryAdapter supplierCategoryAdapter;
    private static List<String> categoryItemList;
    private static Dialog dialog;
    private static AlertDialog.Builder mBuilder;
    private EditText dialog_intended_name;
    private TextView dialog_intended_add;
    private Bundle bundle;
    public static final String SUBCATEGORY_KEY="key";

    public SupplierCategoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_suppiler_category, container, false);

        categoryItemList=new ArrayList<>();
        setCustomInfo();
        
        back=(ImageView)view.findViewById(R.id.supplier_category_toolbar_drawer);
        addCategory=(ImageView)view.findViewById(R.id.supplier_category_toolbar_add_category);
        addCategoryListener();

        category=(RecyclerView)view.findViewById(R.id.supplier_category_list);

        linearLayoutManager=new LinearLayoutManager(getActivity());
        category.setLayoutManager(linearLayoutManager);

        supplierCategoryAdapter=new SupplierCategoryAdapter(categoryItemList,getActivity());
        category.setAdapter(supplierCategoryAdapter);

        supplierCategoryAdapter.setListener(new Listener() {
            @Override
            public void onClick(int position) {
                SupplierSubcategoryFragment supplierSubcategoryFragment=new SupplierSubcategoryFragment();
                bundle=new Bundle();
                bundle.putString(SUBCATEGORY_KEY,categoryItemList.get(position));
                supplierSubcategoryFragment.setArguments(bundle);
                setFragment(supplierSubcategoryFragment);
                //Toast.makeText(getActivity(), position+" position", Toast.LENGTH_SHORT).show();
            }
        });

        return view;


    }
    private void setCustomInfo(){
        categoryItemList.add("Молоко");
        categoryItemList.add("Мясо");
        categoryItemList.add("Сыр");
        categoryItemList.add("Молоко");
        categoryItemList.add("Мясо");
        categoryItemList.add("Сыр");
        categoryItemList.add("Молоко");
        categoryItemList.add("Мясо");
        categoryItemList.add("Сыр");
    }
    private void setFragment(Fragment fragment){
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.supplier_content_frame, fragment, SUPPLIER_VISIBLE_FRAGMENT_TAG)
                .addToBackStack(null)
                .commit();
    }
    private void addCategoryListener(){
        addCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
    }
    private void showDialog(){

        mBuilder= new AlertDialog.Builder(getActivity());
        View mView =getLayoutInflater().inflate(R.layout.dialog_add_category,null);
        mBuilder.setView(mView);

        dialog=mBuilder.create();
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();

        //size
        Rect displayRectangle = new Rect();
        Window window = getActivity().getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
        //set size
        dialog.getWindow().setLayout((int)(displayRectangle.width() *
                0.84f), (int)(displayRectangle.height() * 0.28f));


        dialog_intended_name=(EditText) mView.findViewById(R.id.dialog_add_category_name);
        dialog_intended_add=(TextView)mView.findViewById(R.id.dialog_add_category_add);
        dialog_intended_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                addCategory(dialog_intended_name.getText().toString());
            }
        });

    }
    private void addCategory(String name){
        categoryItemList.add(name);
        supplierCategoryAdapter=new SupplierCategoryAdapter(categoryItemList,getActivity());
        category.setAdapter(supplierCategoryAdapter);
    }

}
