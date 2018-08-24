package kz.production.kuanysh.sellings.ui.content_suppiler.fragments.goods.edit;


import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import java.nio.BufferUnderflowException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kz.production.kuanysh.sellings.R;
import kz.production.kuanysh.sellings.data.network.model.supplier.subproducts.Result;
import kz.production.kuanysh.sellings.di.component.ActivityComponent;
import kz.production.kuanysh.sellings.ui.base.BaseFragment;
import kz.production.kuanysh.sellings.ui.content_suppiler.fragments.goods.add.SupplierAddGoodFragment;
import kz.production.kuanysh.sellings.ui.content_suppiler.fragments.goods.add.SupplierAddGoodMvpView;
import kz.production.kuanysh.sellings.ui.content_suppiler.fragments.goods.add.SupplierAddGoodPresenter;
import kz.production.kuanysh.sellings.ui.content_suppiler.fragments.goods.category.SupplierCategoryFragment;
import kz.production.kuanysh.sellings.ui.content_suppiler.fragments.goods.itemproduct.SupplierGoodsFragment;
import kz.production.kuanysh.sellings.ui.content_suppiler.fragments.goods.subcategory.SupplierGoodsSubcategoryFragment;

import static kz.production.kuanysh.sellings.ui.content_suppiler.activity.SupplierActivity.SUPPLIER_TAG_GOODS;
import static kz.production.kuanysh.sellings.ui.content_suppiler.activity.SupplierActivity.SUPPLIER_VISIBLE_FRAGMENT_TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class SupplierEditGoodsFragment extends BaseFragment implements SupplierEditGoodsMvpView{

    public final String TAG_FRAGMENT_STACK=getClass().getSimpleName();

    @Inject
    SupplierEditGoodsPresenter<SupplierEditGoodsMvpView> mPresenter;

    @BindView(R.id.supplier_edit_goods_toolbar_drawer)
    ImageView back;

    @BindView(R.id.supplier_edit_goods_edit_category)
    Spinner category;

    @BindView(R.id.supplier_edit_goods_edit_subcategory)
    Spinner subcategory;

    @BindView(R.id.supplier_edit_goods_edit_name)
    TextInputLayout name;

    @BindView(R.id.supplier_edit_goods_edit_amount)
    TextInputLayout amount;

    @BindView(R.id.supplier_edit_goods_edit_price)
    TextInputLayout price;

    @BindView(R.id.supplier_edit_goods_edit_save_btn)
    Button save_btn;


    private static String categoryText=null;
    private static String subCategoryText=null;
    private int SUBCATEGORY_ID;
    private String SUBCATEGORY_NAME;
    private int CATEGORY_ID;
    private String CATEGORY_NAME;
    private Bundle bundle;
    private Result GOOD_ITEM;

    private static List<String> categoryTitle;
    private static List<kz.production.kuanysh.sellings.data.network.model.owner.category.Result> categoryList;
    private static List<String> subcategoryTitle;
    private static List<kz.production.kuanysh.sellings.data.network.model.supplier.subcategory.get.Result> subcategoryList;
    private static ArrayAdapter<String> categoryAdapter;
    private static ArrayAdapter<String> subcategoryAdapter;


    public SupplierEditGoodsFragment() {
        // Required empty public constructor
    }
    public static SupplierEditGoodsFragment newInstance() {
        Bundle args = new Bundle();
        SupplierEditGoodsFragment fragment = new SupplierEditGoodsFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_supplier_edit_goods, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            setUnBinder(ButterKnife.bind(this, view));
            mPresenter.onAttach(this);
        }

        final Bundle bundle=getArguments();
        if(bundle!=null){
            SUBCATEGORY_ID =bundle.getInt(SupplierGoodsSubcategoryFragment.SUBCATEGORY_ID_KEY);
            SUBCATEGORY_NAME =bundle.getString(SupplierGoodsSubcategoryFragment.SUBCATEGORY_NAME_KEY);
            CATEGORY_NAME=bundle.getString(SupplierCategoryFragment.CATEGORY_NAME_KEY);
            CATEGORY_ID=bundle.getInt(SupplierCategoryFragment.CATEGORY_ID_KEY);
            GOOD_ITEM=bundle.getParcelable(SupplierGoodsFragment.PRODUCT_ITEM_KEY);

            Log.d("myTag", "onCreateView: EditProduct receives: ");
            Log.d("myTag", "onCreateView: CATEGORY_ID "+CATEGORY_ID);
            Log.d("myTag", "onCreateView: SUBCATEGORY_ID "+SUBCATEGORY_ID);
            Log.d("myTag", "onCreateView: CATEGORY_NAME "+CATEGORY_NAME);
            Log.d("myTag", "onCreateView: SUBCATEGORY_NAME "+SUBCATEGORY_NAME);

        }


        return view;
    }

    @OnClick(R.id.supplier_edit_goods_toolbar_drawer)
    public void goBack(){
        mPresenter.onBackClick();
    }

    @OnClick(R.id.supplier_edit_goods_edit_save_btn)
    public void goSave(){
        if(categoryAdapter.getCount()!=0){
            if(subcategoryAdapter.getCount()!=0){
                mPresenter.onAddClick(String.valueOf(subcategoryList.get(subcategory.getSelectedItemPosition()).getId()),
                        name.getEditText().getText().toString(),
                        price.getEditText().getText().toString(),
                        amount.getEditText().getText().toString(),GOOD_ITEM.getId());
            }else{
                mPresenter.getMvpView().showMessage("Subcategory does not exist!");
            }
        }else{
            mPresenter.getMvpView().showMessage("Category does not exist!");
        }
    }


    @Override
    public void onDestroyView() {
        mPresenter.onDetach();
        super.onDestroyView();
    }


    @Override
    public void updateCategory(List<kz.production.kuanysh.sellings.data.network.model.owner.category.Result> result) {
        categoryList.clear();
        categoryList.addAll(result);
        categoryTitle.clear();

        for (int i=0;i<result.size();i++){
            categoryTitle.add(result.get(i).getTitle());
        }
        categoryAdapter.notifyDataSetChanged();

        for(int i=0;i<categoryList.size();i++){
            if(categoryList.get(i).getId()==CATEGORY_ID){
                category.setSelection(i);
            }
        }
    }

    @Override
    public void updateSubCategory(List<kz.production.kuanysh.sellings.data.network.model.supplier.subcategory.get.Result> subList) {
        subcategoryList.clear();
        subcategoryList.addAll(subList);
        subcategoryTitle.clear();
        subcategoryAdapter.notifyDataSetChanged();

        for (int i=0;i<subcategoryList.size();i++){
            subcategoryTitle.add(subcategoryList.get(i).getTitle());
        }
        subcategoryAdapter.notifyDataSetChanged();

        if(mPresenter.getDataManager().getCategorySetted()==null){
            for(int i=0;i<subcategoryList.size();i++){
                if(subcategoryList.get(i).getId()==SUBCATEGORY_ID){
                    subcategory.setSelection(i);
                    mPresenter.getDataManager().setCategorySetted("setted cat");
                }
            }
        }

    }

    @Override
    public void openSubcategoryFragments() {
        getActivity().getSupportFragmentManager().popBackStack();

        SupplierGoodsFragment supplierGoodsFragment=new SupplierGoodsFragment();
        bundle=new Bundle();
        bundle.putInt(SupplierGoodsSubcategoryFragment.SUBCATEGORY_ID_KEY,SUBCATEGORY_ID);
        bundle.putString(SupplierGoodsSubcategoryFragment.SUBCATEGORY_NAME_KEY,SUBCATEGORY_NAME);
        bundle.putInt(SupplierCategoryFragment.CATEGORY_ID_KEY,CATEGORY_ID);
        bundle.putString(SupplierCategoryFragment.CATEGORY_NAME_KEY,CATEGORY_NAME);
        mPresenter.getDataManager().setCategorySetted(null);
        supplierGoodsFragment.setArguments(bundle);
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .disallowAddToBackStack()
                .replace(R.id.supplier_content_frame, supplierGoodsFragment, SUPPLIER_TAG_GOODS)
                .commit();


    }

    @Override
    protected void setUp(View view) {
        categoryList=new ArrayList<>();
        subcategoryList=new ArrayList<>();
        categoryTitle=new ArrayList<>();
        subcategoryTitle=new ArrayList<>();

        categoryAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, categoryTitle);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category.setAdapter(categoryAdapter);

        subcategoryAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, subcategoryTitle);
        subcategoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        subcategory.setAdapter(subcategoryAdapter);


        mPresenter.onViewPrepared();
        name.getEditText().setText(GOOD_ITEM.getTitle().toString());
        amount.getEditText().setText(GOOD_ITEM.getNumberOfStock());
        price.getEditText().setText(GOOD_ITEM.getPrice());


        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                subcategoryTitle.clear();
                subcategoryAdapter.notifyDataSetChanged();
                mPresenter.onCategoryClick(categoryList.get(position).getId());
            }
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });

    }
}
