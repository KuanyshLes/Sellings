package kz.production.kuanysh.sellings.ui.content_owner.fragments.main.category;


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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kz.production.kuanysh.sellings.R;
import kz.production.kuanysh.sellings.data.network.model.owner.category.Result;
import kz.production.kuanysh.sellings.di.component.ActivityComponent;
import kz.production.kuanysh.sellings.ui.base.BaseFragment;
import kz.production.kuanysh.sellings.ui.content_owner.fragments.main.subcategory.SupplierProductSubcategoryFragment;
import kz.production.kuanysh.sellings.ui.content_owner.fragments.main.main.OwnerSupplierItemFragment;
import kz.production.kuanysh.sellings.ui.content_owner.fragments.order.orders.OwnerOrdersFragment;
import kz.production.kuanysh.sellings.ui.content_owner.main.MainActivity;
import kz.production.kuanysh.sellings.ui.content_owner.utils.Listener;
import kz.production.kuanysh.sellings.ui.content_owner.utils.adapters.SupplierProductsAdapter;

import static kz.production.kuanysh.sellings.ui.content_owner.main.MainActivity.TAG;
import static kz.production.kuanysh.sellings.ui.content_owner.main.MainActivity.TAG_MAIN;

/**
 * A simple {@link Fragment} subclass.
 */
public class SupplierProductFragment extends BaseFragment implements SupplierProductMvpView{

    public final String TAG_FRAGMENT_STACK=getClass().getSimpleName();


    @Inject
    SupplierProductPresenter<SupplierProductMvpView> mPresenter;

    @BindView(R.id.supplier_products)
    RecyclerView supplier_products;

    @Inject
    SupplierProductsAdapter supplierProductsAdapter;

    @BindView(R.id.supplier_product_toolbar_back)
    ImageView back;

    @BindView(R.id.supplier_product_toolbar_title)
    TextView toolbar_title;

    @BindView(R.id.id_nullable)
    View nullText;


    private LinearLayoutManager linearLayoutManager;
    private OwnerSupplierItemFragment mainFragment;
    private FragmentTransaction fragmentTransaction;
    private static int USER_ID;
    private static String USER_NAME;
    private static List<Result> categoryList=new ArrayList<>();
    private static Bundle bundle;
    public static final String CATEGORY_ID_KEY="CATEGORY_ID_KEY";
    public static final String CATEGORY_NAME_KEY="CATEGORY_NAME_KEY";

    public SupplierProductFragment() {
        // Required empty public constructor
    }
    public static SupplierProductFragment newInstance() {
        SupplierProductFragment fragment = new SupplierProductFragment();
        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_supplier_product, container, false);

        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            setUnBinder(ButterKnife.bind(this, view));
            mPresenter.onAttach(this);
        }
        final Bundle bundle = getArguments();
        if(bundle!=null){
            USER_ID=bundle.getInt(OwnerSupplierItemFragment.USER_ID_KEY);
            USER_NAME=bundle.getString(OwnerSupplierItemFragment.USER_NAME_KEY);
        }

        return view;
    }

    @OnClick(R.id.supplier_product_toolbar_back)
    public void goProviders() {
        getActivity().getSupportFragmentManager().popBackStack();
    }


    @Override
    public void updateSuppliersCategories(List<Result> list) {
        supplier_products.setVisibility(View.VISIBLE);
        nullText.setVisibility(View.GONE);
        categoryList.addAll(list);
        supplierProductsAdapter.addItems(list);
    }

    @Override
    public void openSuppliers() {

    }

    @Override
    public void openSupplierSubcategory(int id) {
        SupplierProductSubcategoryFragment supplierProductSubcategoryFragment=new SupplierProductSubcategoryFragment();
        bundle = new Bundle();
        bundle.putInt(CATEGORY_ID_KEY, categoryList.get(id).getId());
        bundle.putInt(OwnerSupplierItemFragment.USER_ID_KEY, USER_ID);
        bundle.putString(CATEGORY_NAME_KEY, categoryList.get(id).getTitle());
        bundle.putString(OwnerSupplierItemFragment.USER_NAME_KEY, USER_NAME);
        supplierProductSubcategoryFragment.setArguments(bundle);
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.add(R.id.content_frame, supplierProductSubcategoryFragment, TAG);
        ft.addToBackStack(TAG_FRAGMENT_STACK);
        ft.hide(this);
        ft.commit();
    }

    @Override
    protected void setUp(View view) {
        toolbar_title.setText(USER_NAME.replace("\"",""));
        linearLayoutManager=new LinearLayoutManager(getActivity());
        supplier_products.setLayoutManager(linearLayoutManager);
        supplier_products.setAdapter(supplierProductsAdapter);

        supplierProductsAdapter.setListener(new Listener() {
            @Override
            public void onClick(int position) {
                mPresenter.onCategoryClick(position);
            }
        });

        mPresenter.onViewPrepared(USER_ID);
    }
}
