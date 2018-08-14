package kz.production.kuanysh.sellings.ui.content_suppiler.fragments.goods.itemproduct;


import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kz.production.kuanysh.sellings.R;
import kz.production.kuanysh.sellings.data.network.model.supplier.subproducts.Result;
import kz.production.kuanysh.sellings.di.component.ActivityComponent;
import kz.production.kuanysh.sellings.ui.base.BaseFragment;
import kz.production.kuanysh.sellings.ui.content_suppiler.fragments.goods.add.SupplierAddGoodFragment;
import kz.production.kuanysh.sellings.ui.content_suppiler.fragments.goods.category.SupplierCategoryFragment;
import kz.production.kuanysh.sellings.ui.content_suppiler.fragments.goods.edit.SupplierEditGoodsFragment;
import kz.production.kuanysh.sellings.ui.content_suppiler.fragments.goods.subcategory.SupplierGoodsSubcategoryFragment;
import kz.production.kuanysh.sellings.ui.content_suppiler.utils.adapters.GoodsItemsAdapter;

import static kz.production.kuanysh.sellings.ui.content_suppiler.activity.SupplierActivity.SUPPLIER_TAG_GOODS;
import static kz.production.kuanysh.sellings.ui.content_suppiler.activity.SupplierActivity.SUPPLIER_VISIBLE_FRAGMENT_TAG;
import static kz.production.kuanysh.sellings.ui.content_suppiler.fragments.goods.subcategory.SupplierGoodsSubcategoryFragment.SUBCATEGORY_ID_KEY;

/**
 * A simple {@link Fragment} subclass.
 */
public class SupplierGoodsFragment extends BaseFragment implements SupplierGoodsMvpView{

    @Inject
    SupplierGoodsPresenter<SupplierGoodsMvpView> mPresenter;

    @BindView(R.id.supplier_goods_toolbar_drawer)
    ImageView back;

    @BindView(R.id.supplier_goods_toolbar_add_goods)
    ImageView addGoods;

    @BindView(R.id.supplier_goods_toolbar_title)
    TextView toolbar_title;

    @BindView(R.id.supplier_goods_recycler)
    RecyclerView goods;

    @BindView(R.id.id_nullable)
    View nullText;

    @Inject
    GoodsItemsAdapter goodsItemsAdapter;


    private Bundle bundle1;
    private ConstraintLayout document;
    private LinearLayoutManager linearLayoutManager;
    private List<Result> goods_list;

    private static Dialog dialog;
    private static AlertDialog.Builder mBuilder;
    private int SUBCATEGORY_ID;
    private String CATEGORY_NAME;
    private String SUBCATEGORY_NAME;
    private int CATEGORY_ID;
    private Bundle bundle;
    public static String PRODUCT_ITEM_KEY="kjefnek";


    public SupplierGoodsFragment() {
        // Required empty public constructor
    }
    public static SupplierGoodsFragment newInstance() {
        Bundle args = new Bundle();
        SupplierGoodsFragment fragment = new SupplierGoodsFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_supplier_goods, container, false);

        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            setUnBinder(ButterKnife.bind(this, view));
            mPresenter.onAttach(this);
        }

        final Bundle bundle=getArguments();
        if(bundle!=null){
            SUBCATEGORY_NAME =bundle.getString(SupplierGoodsSubcategoryFragment.SUBCATEGORY_NAME_KEY);
            SUBCATEGORY_ID =bundle.getInt(SupplierGoodsSubcategoryFragment.SUBCATEGORY_ID_KEY);
            CATEGORY_NAME=bundle.getString(SupplierCategoryFragment.CATEGORY_NAME_KEY);
            CATEGORY_ID=bundle.getInt(SupplierCategoryFragment.CATEGORY_ID_KEY);

            Log.d("myTag", "onCreateView: ItemProduct receives: ");
            Log.d("myTag", "onCreateView: CATEGORY_ID "+CATEGORY_ID);
            Log.d("myTag", "onCreateView: SUBCATEGORY_ID "+SUBCATEGORY_ID);
            Log.d("myTag", "onCreateView: CATEGORY_NAME "+CATEGORY_NAME);
        }

        return view;
    }

    @Override
    protected void setUp(View view) {
        toolbar_title.setText(SUBCATEGORY_NAME);
        goods_list=new ArrayList<>();
        linearLayoutManager=new LinearLayoutManager(getActivity());
        goods.setLayoutManager(linearLayoutManager);

        goods.setAdapter(goodsItemsAdapter);
        mPresenter.onViewPrepared(String.valueOf(SUBCATEGORY_ID));
        goodsItemsAdapter.addPresenter(mPresenter);

    }

    @OnClick(R.id.supplier_goods_toolbar_drawer)
    public void goSubcategory(){
        mPresenter.onBackClick();
    }

    @OnClick(R.id.supplier_goods_toolbar_add_goods)
    public void setAddGoods(){
        mPresenter.onAddClick();
    }


    @Override
    public void updateProducts(List<Result> resultList) {
        goods.setVisibility(View.VISIBLE);
        nullText.setVisibility(View.GONE);
        goods_list.clear();
        goods_list.addAll(resultList);
        goodsItemsAdapter.addItems(goods_list);
    }


    @Override
    public void openSubcategoryFragment() {
        SupplierGoodsSubcategoryFragment supplierGoodsSubcategoryFragment=new SupplierGoodsSubcategoryFragment();
        bundle=new Bundle();
        bundle.putInt(SupplierCategoryFragment.CATEGORY_ID_KEY,CATEGORY_ID);
        bundle.putString(SupplierCategoryFragment.CATEGORY_NAME_KEY,CATEGORY_NAME);
        supplierGoodsSubcategoryFragment.setArguments(bundle);

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.supplier_content_frame, supplierGoodsSubcategoryFragment, SUPPLIER_VISIBLE_FRAGMENT_TAG)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void openAddProductFragment() {
        SupplierAddGoodFragment supplierAddGoodFragment=new SupplierAddGoodFragment();
        bundle=new Bundle();
        bundle.putInt(SupplierCategoryFragment.CATEGORY_ID_KEY,CATEGORY_ID);
        bundle.putInt(SupplierGoodsSubcategoryFragment.SUBCATEGORY_ID_KEY,SUBCATEGORY_ID);
        bundle.putString(SupplierGoodsSubcategoryFragment.SUBCATEGORY_NAME_KEY,SUBCATEGORY_NAME);
        bundle.putString(SupplierCategoryFragment.CATEGORY_NAME_KEY,CATEGORY_NAME);
        supplierAddGoodFragment.setArguments(bundle);

        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .disallowAddToBackStack()
                .replace(R.id.supplier_content_frame, supplierAddGoodFragment, SUPPLIER_TAG_GOODS)
                .commit();
    }

    @Override
    public void openDeleteDialog(int position) {
        mBuilder= new AlertDialog.Builder(getActivity());
        View mView =getLayoutInflater().inflate(R.layout.dialog_item_delete,null);
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


        TextView title=(TextView) mView.findViewById(R.id.dialog_category_title);

        TextView ok=(TextView) mView.findViewById(R.id.dialog_item_delete_ok);
        TextView cancell=(TextView)mView.findViewById(R.id.dialog_item_delete_cancell);



        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Log.d("myTag", "intented delete product id: "+position);
                mPresenter.onDeleteClick(SUBCATEGORY_ID,position);
            }
        });
        cancell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    @Override
    public void openEditcategoryFragment(int position) {
        SupplierEditGoodsFragment supplierEditGoodsFragment=new SupplierEditGoodsFragment();
        bundle=new Bundle();
        bundle.putInt(SupplierCategoryFragment.CATEGORY_ID_KEY,CATEGORY_ID);
        bundle.putInt(SupplierGoodsSubcategoryFragment.SUBCATEGORY_ID_KEY,SUBCATEGORY_ID);
        bundle.putString(SupplierGoodsSubcategoryFragment.SUBCATEGORY_NAME_KEY,SUBCATEGORY_NAME);
        bundle.putString(SupplierCategoryFragment.CATEGORY_NAME_KEY,CATEGORY_NAME);
        bundle.putParcelable(PRODUCT_ITEM_KEY,goods_list.get(position));
        supplierEditGoodsFragment.setArguments(bundle);

        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .disallowAddToBackStack()
                .replace(R.id.supplier_content_frame, supplierEditGoodsFragment, SUPPLIER_TAG_GOODS)
                .commit();

    }




    @Override
    public void onDestroyView() {
        mPresenter.onDetach();
        super.onDestroyView();
    }
}
