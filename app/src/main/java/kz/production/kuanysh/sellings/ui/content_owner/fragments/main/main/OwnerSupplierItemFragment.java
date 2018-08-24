package kz.production.kuanysh.sellings.ui.content_owner.fragments.main.main;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kz.production.kuanysh.sellings.R;
import kz.production.kuanysh.sellings.data.model.SupplierItem;
import kz.production.kuanysh.sellings.data.network.model.owner.all_provider.Provider;
import kz.production.kuanysh.sellings.data.network.model.owner.basketproviders.BasketProviders;
import kz.production.kuanysh.sellings.di.component.ActivityComponent;
import kz.production.kuanysh.sellings.ui.base.BaseFragment;
import kz.production.kuanysh.sellings.ui.content_owner.fragments.basket.BasketFragment;
import kz.production.kuanysh.sellings.ui.content_owner.fragments.basketproviders.BasketProvidersFragment;
import kz.production.kuanysh.sellings.ui.content_owner.fragments.main.category.SupplierProductFragment;
import kz.production.kuanysh.sellings.ui.content_owner.utils.Listener;
import kz.production.kuanysh.sellings.ui.content_owner.utils.adapters.SupplierAdapter;

import static kz.production.kuanysh.sellings.ui.content_owner.main.MainActivity.TAG;
import static kz.production.kuanysh.sellings.ui.content_owner.main.MainActivity.TAG_MAIN;

/**
 * A simple {@link Fragment} subclass.
 */
public class OwnerSupplierItemFragment extends BaseFragment implements OwnerSupplierItemMvpView{


    public final String TAG_FRAGMENT_STACK=getClass().getSimpleName();


    @Nullable
    @BindView(R.id.main_toolbar)
    Toolbar toolbar;

    @Inject
    OwnerSupplierItemPresenter<OwnerSupplierItemMvpView> mPresenter;


    @BindView(R.id.supplier_list)
    RecyclerView supplier_list;

    @BindView(R.id.swipyrefreshlayout)
    SwipyRefreshLayout swipyRefreshLayout;

    @BindView(R.id.main_toolbar_drawer)
    ImageView drawerHelper;


    @BindView(R.id.main_toolbar_shopping)
    ImageView basket;

    @Inject
    SupplierAdapter supplierAdapter;

    @BindView(R.id.id_nullable)
    View nullText;

    private static DrawerLayout drawer;

    public static final String USER_ID_KEY="keyIdUser";
    public static final String USER_NAME_KEY="keyNameUser";
    public static String SUPPLIER_USER_NAME="";

    private LinearLayoutManager linearLayoutManager;
    public static int page=0;
    public static int ALL_PAGE=0;

    private List<Provider> list;
    private Bundle bundle;


    public OwnerSupplierItemFragment() {
        // Required empty public constructor
    }

    public static OwnerSupplierItemFragment newInstance() {
        Bundle args = new Bundle();
        OwnerSupplierItemFragment fragment = new OwnerSupplierItemFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_main, container, false);

        drawer=(DrawerLayout)getActivity().findViewById(R.id.drawer_layout);

        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            setUnBinder(ButterKnife.bind(this, view));
            mPresenter.onAttach(this);
        }


        return view;
    }


    @OnClick(R.id.main_toolbar_drawer)
    public void openDrawer(){
        drawer.openDrawer(GravityCompat.START);
    }

    @OnClick(R.id.main_toolbar_shopping)
    public void openBasket(){
        mPresenter.onBasketClick();
    }




    @Override
    public void openBasketFragment() {
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame, new BasketProvidersFragment(), TAG_MAIN)
                .commit();
    }

    @Override
    public void openSupplierProductFragment(int position) {
        SupplierProductFragment supplierProductFragment=new SupplierProductFragment();
        bundle = new Bundle();
        bundle.putInt(USER_ID_KEY, list.get(position).getUserId());
        bundle.putString(USER_NAME_KEY, list.get(position).getTitle());
        supplierProductFragment.setArguments(bundle);

        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(TAG_FRAGMENT_STACK)
                .hide(this)
                .add(R.id.content_frame, supplierProductFragment, TAG_MAIN)
                .commit();
    }

    @Override
    public void refreshSupplierList(List<Provider> supplierItems,int page_count) {
        ALL_PAGE=page_count+1;
        swipyRefreshLayout.setRefreshing(false);
        supplier_list.setVisibility(View.VISIBLE);
        nullText.setVisibility(View.GONE);
        list.addAll(supplierItems);
        supplierAdapter.addItems(list);
        linearLayoutManager.scrollToPosition(list.size()-19);

    }


    @Override
    protected void setUp(View view) {
        linearLayoutManager=new LinearLayoutManager(getActivity());
        supplier_list.setLayoutManager(linearLayoutManager);
        supplier_list.setAdapter(supplierAdapter);
        list=new ArrayList<>();
        supplierAdapter.setListener(new Listener() {
            @Override
            public void onClick(int position) {
                mPresenter.onSupplierItemClick(position);
            }
        });
        mPresenter.onViewPrepared(String.valueOf(page));

        swipyRefreshLayout.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(SwipyRefreshLayoutDirection direction) {
                if(direction==SwipyRefreshLayoutDirection.TOP){
                    list.clear();
                    supplierAdapter.notifyDataSetChanged();
                    page=0;
                    mPresenter.onViewPrepared(String.valueOf(page));
                }else{
                    if(page!=ALL_PAGE-1){
                        page++;
                        mPresenter.onViewPrepared(String.valueOf(page));
                    }else{
                        swipyRefreshLayout.setRefreshing(false);

                    }
                }
            }
        });
    }


    @Override
    public void onDestroyView() {
        mPresenter.onDetach();
        super.onDestroyView();
    }

}
