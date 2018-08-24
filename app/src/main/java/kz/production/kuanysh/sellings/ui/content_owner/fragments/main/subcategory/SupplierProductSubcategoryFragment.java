package kz.production.kuanysh.sellings.ui.content_owner.fragments.main.subcategory;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kz.production.kuanysh.sellings.R;
import kz.production.kuanysh.sellings.data.model.SubCategory;
import kz.production.kuanysh.sellings.data.network.model.owner.subcategory.Result;
import kz.production.kuanysh.sellings.di.component.ActivityComponent;
import kz.production.kuanysh.sellings.ui.base.BaseFragment;
import kz.production.kuanysh.sellings.ui.content_owner.fragments.main.category.SupplierProductFragment;
import kz.production.kuanysh.sellings.ui.content_owner.fragments.main.main.OwnerSupplierItemFragment;
import kz.production.kuanysh.sellings.ui.content_owner.main.MainActivity;
import kz.production.kuanysh.sellings.ui.content_owner.utils.adapters.MultiTypeCategoryAdapter;

import static kz.production.kuanysh.sellings.ui.content_owner.main.MainActivity.TAG_MAIN;
import static kz.production.kuanysh.sellings.ui.content_owner.main.MainActivity.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class SupplierProductSubcategoryFragment extends BaseFragment implements SupplierProductSubcategoryMvpView{

    public final String TAG_FRAGMENT_STACK=getClass().getSimpleName();


    @Inject
    SupplierProductSubCategoryPresenter<SupplierProductSubcategoryMvpView> mPresenter;

    @BindView(R.id.supplier_products_subcategories)
    RecyclerView recyclerSubCategory;

    @BindView(R.id.supplier_product_subcategory_toolbar_back)
    ImageView back;

    @BindView(R.id.supplier_product_subcategory_toolbar_title)
    TextView toolbar_title;

    private static List<kz.production.kuanysh.sellings.data.network.model.owner.superproduct.Result> subProducts;
    private static List<SubCategory> goFetchItems;

    private static int CATEGORY_ID;
    private static int USER_ID;
    private static String CATEGORY_NAME;
    private static String USER_NAME;
    private static MultiTypeCategoryAdapter multiTypeCategoryAdapter;
    private static Bundle bundle;

    @BindView(R.id.id_nullable)
    View nullText;


    private LinearLayoutManager linearLayoutManager;

    public SupplierProductSubcategoryFragment() {
        // Required empty public constructor
    }
    public static SupplierProductSubcategoryFragment newInstance() {
        SupplierProductSubcategoryFragment fragment = new SupplierProductSubcategoryFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_supplier_product_subcategory, container, false);

        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            setUnBinder(ButterKnife.bind(this, view));
            mPresenter.onAttach(this);
        }
        final Bundle bundle = getArguments();
        if(bundle!=null){
            CATEGORY_ID=bundle.getInt(SupplierProductFragment.CATEGORY_ID_KEY);
            USER_ID=bundle.getInt(OwnerSupplierItemFragment.USER_ID_KEY);
            CATEGORY_NAME=bundle.getString(SupplierProductFragment.CATEGORY_NAME_KEY);
            USER_NAME=bundle.getString(OwnerSupplierItemFragment.USER_NAME_KEY);
        }
        subProducts=new ArrayList<>();
        goFetchItems=new ArrayList<>();



        return view;
    }



    @OnClick(R.id.supplier_product_subcategory_toolbar_back)
    public void goCategory(){
        mPresenter.getMvpView().openCategoryFragment();
    }

    @Override
    public void updateSubcategory(List<kz.production.kuanysh.sellings.data.network.model.owner.superproduct.Result> list) {
        recyclerSubCategory.setVisibility(View.VISIBLE);
        nullText.setVisibility(View.GONE);
        subProducts.clear();
        subProducts.addAll(list);


        for(int i=0;i<subProducts.size();i++){
            goFetchItems.add(new SubCategory(subProducts.get(i).getTitle(),subProducts.get(i).getProducts()));
        }

        linearLayoutManager=new LinearLayoutManager(getActivity());
        multiTypeCategoryAdapter=new MultiTypeCategoryAdapter(goFetchItems,getActivity());
        recyclerSubCategory.setLayoutManager(linearLayoutManager);
        recyclerSubCategory.setAdapter(multiTypeCategoryAdapter);
        multiTypeCategoryAdapter.addPresenter(mPresenter);

    }

    @Override
    public void openCategoryFragment() {
        getActivity().getSupportFragmentManager().popBackStack();
//        SupplierProductFragment supplierProductFragment=new SupplierProductFragment();
//        bundle=new Bundle();
//        bundle.putString(OwnerSupplierItemFragment.USER_NAME_KEY,USER_NAME);
//        bundle.putInt(OwnerSupplierItemFragment.USER_ID_KEY,USER_ID);
//        supplierProductFragment.setArguments(bundle);
//
//
//        getActivity().getSupportFragmentManager()
//                .beginTransaction()
//                .disallowAddToBackStack()
//                .replace(R.id.content_frame, supplierProductFragment, TAG_MAIN)
//                .commit();
    }

    @Override
    protected void setUp(View view) {
        toolbar_title.setText(CATEGORY_NAME.replace("\"",""));
        mPresenter.onViewPrepared(CATEGORY_ID);




    }
}
