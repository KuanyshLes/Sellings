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

import java.util.ArrayList;
import java.util.List;

import kz.production.kuanysh.sellings.R;
import kz.production.kuanysh.sellings.model.OwnerItem;
import kz.production.kuanysh.sellings.ui.content_owner.utils.Listener;
import kz.production.kuanysh.sellings.ui.content_suppiler.fragments.order.SupplierOrdersFragment;
import kz.production.kuanysh.sellings.ui.content_suppiler.utils.adapters.SupplierCategoryAdapter;

import static kz.production.kuanysh.sellings.ui.content_suppiler.SupplierActivity.SUPPLIER_VISIBLE_FRAGMENT_TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class SupplierSubcategoryFragment extends Fragment {
    private ImageView back,addCategory;
    private RecyclerView category;
    private LinearLayoutManager linearLayoutManager;
    private static SupplierCategoryAdapter supplierCategoryAdapter;
    private static List<String> subcategoryItemList;
    private static Dialog dialog;
    private static AlertDialog.Builder mBuilder;
    private EditText dialog_intended_name;
    private TextView title,dialog_intended_add;
    private String PARENT_CATEGORY;

    public SupplierSubcategoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_supplier_subcategory, container, false);

        final Bundle bundle = getArguments();
        if (bundle != null){
            PARENT_CATEGORY = (String) bundle.get(SupplierCategoryFragment.SUBCATEGORY_KEY);
        }


        subcategoryItemList=new ArrayList<>();
        setCustomInfo();

        title=(TextView)view.findViewById(R.id.supplier_subcategory_toolbar_title);
        title.setText(PARENT_CATEGORY.toString());

        back=(ImageView)view.findViewById(R.id.supplier_subcategory_toolbar_drawer);
        gobackListener();

        addCategory=(ImageView)view.findViewById(R.id.supplier_subcategory_toolbar_add_category);
        addCategoryListener();

        category=(RecyclerView)view.findViewById(R.id.supplier_subcategory_list);

        linearLayoutManager=new LinearLayoutManager(getActivity());
        category.setLayoutManager(linearLayoutManager);

        supplierCategoryAdapter=new SupplierCategoryAdapter(subcategoryItemList,getActivity());
        category.setAdapter(supplierCategoryAdapter);



        return view;
    }

    private void setCustomInfo(){
        subcategoryItemList.add("Моё");
        subcategoryItemList.add("Домик в деревне");
        subcategoryItemList.add("Нутрилион");

        subcategoryItemList.add("Моё");
        subcategoryItemList.add("Домик в деревне");
        subcategoryItemList.add("Нутрилион");

        subcategoryItemList.add("Моё");
        subcategoryItemList.add("Домик в деревне");
        subcategoryItemList.add("Нутрилион");

        subcategoryItemList.add("Моё");
        subcategoryItemList.add("Домик в деревне");
        subcategoryItemList.add("Нутрилион");
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
        subcategoryItemList.add(name);
        supplierCategoryAdapter=new SupplierCategoryAdapter(subcategoryItemList,getActivity());
        category.setAdapter(supplierCategoryAdapter);
    }
    private void gobackListener(){
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SupplierCategoryFragment supplierCategoryFragment=new SupplierCategoryFragment();
                setFragment(supplierCategoryFragment);
            }
        });
    }
    private void setFragment(Fragment fragment){
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.supplier_content_frame, fragment, SUPPLIER_VISIBLE_FRAGMENT_TAG)
                .addToBackStack(null)
                .commit();
    }

}
