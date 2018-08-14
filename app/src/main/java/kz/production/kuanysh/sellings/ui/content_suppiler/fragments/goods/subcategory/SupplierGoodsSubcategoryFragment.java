package kz.production.kuanysh.sellings.ui.content_suppiler.fragments.goods.subcategory;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kz.production.kuanysh.sellings.R;
import kz.production.kuanysh.sellings.data.network.model.supplier.subcategory.get.Result;
import kz.production.kuanysh.sellings.di.component.ActivityComponent;
import kz.production.kuanysh.sellings.ui.base.BaseFragment;
import kz.production.kuanysh.sellings.ui.content_owner.utils.Listener;
import kz.production.kuanysh.sellings.ui.content_suppiler.fragments.goods.category.SupplierCategoryFragment;
import kz.production.kuanysh.sellings.ui.content_suppiler.fragments.goods.itemproduct.SupplierGoodsFragment;
import kz.production.kuanysh.sellings.ui.content_suppiler.utils.adapters.SupplierSubcategoryAdapter;

import static kz.production.kuanysh.sellings.ui.content_suppiler.activity.SupplierActivity.SUPPLIER_TAG_GOODS;

/**
 * Created by User on 02.07.2018.
 */

public class SupplierGoodsSubcategoryFragment extends BaseFragment implements SupplierGoodsSubcategoryMvpView {

    @Inject
    SupplierGoodsSubcategoryPresenter<SupplierGoodsSubcategoryMvpView> mPresenter;

    @BindView(R.id.supplier_subcategory_toolbar_drawer)
    ImageView back;

    @BindView(R.id.supplier_subcategory_toolbar_add_category)
    ImageView addCategory;

    @BindView(R.id.supplier_subcategory_list)
    RecyclerView category;

    @BindView(R.id.supplier_subcategory_toolbar_title)
    TextView titleCategory;

    @BindView(R.id.id_nullable)
    View nullText;

    @Inject
    SupplierSubcategoryAdapter supplierCategoryAdapter;

    private static LinearLayoutManager linearLayoutManager;

    private static List<Result> itemList;
    private static Dialog dialog;
    private static AlertDialog.Builder mBuilder;
    private EditText dialog_intended_name;
    private TextView title,dialog_intended_add;
    private int CATEGORY_ID;
    public static final String SUBCATEGORY_ID_KEY="keySubbb";
    public static final String SUBCATEGORY_NAME_KEY="keySuwdfesdtfybbb";
    private static int SUBCATEGORY_ID;
    private String CATEGORY_NAME;
    private Bundle bundle;

    public SupplierGoodsSubcategoryFragment() {
        // Required empty public constructor
    }
    public static SupplierGoodsSubcategoryFragment newInstance() {
        Bundle args = new Bundle();
        SupplierGoodsSubcategoryFragment fragment = new SupplierGoodsSubcategoryFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_supplier_subcategory, container, false);
        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            setUnBinder(ButterKnife.bind(this, view));
            mPresenter.onAttach(this);
        }


        final Bundle bundle = getArguments();
        if (bundle != null){
            CATEGORY_ID = bundle.getInt(SupplierCategoryFragment.CATEGORY_ID_KEY);
            CATEGORY_NAME =bundle.getString(SupplierCategoryFragment.CATEGORY_NAME_KEY);
            Log.d("myTag", "onCreateView: Subcategory receives: ");
            Log.d("myTag", "onCreateView: CATEGORY_ID "+CATEGORY_ID);
            Log.d("myTag", "onCreateView: CATEGORY_NAME "+CATEGORY_NAME);
        }


        return view;
    }

    @OnClick(R.id.supplier_subcategory_toolbar_drawer)
    public void goBack(){
        mPresenter.onBackClick();
    }

    @OnClick(R.id.supplier_subcategory_toolbar_add_category)
    public void goOpenDialog(){
        mPresenter.onAddSubCategoryClick();
    }






    @Override
    public void openCategory() {
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .disallowAddToBackStack()
                .replace(R.id.supplier_content_frame, SupplierCategoryFragment.newInstance(), SUPPLIER_TAG_GOODS)
                .commit();
    }

    @Override
    public void updateSubCategory(List<Result> resultList) {
        category.setVisibility(View.VISIBLE);
        nullText.setVisibility(View.GONE);
        itemList.clear();
        itemList.addAll(resultList);
        supplierCategoryAdapter.addItems(itemList);
    }



    @Override
    public void openDialog(int id,int status) {
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

        TextView title=(TextView) mView.findViewById(R.id.dialog_category_title);
        title.setText("Новая сабкатегория");
        dialog_intended_name=(EditText) mView.findViewById(R.id.dialog_add_category_name);
        dialog_intended_add=(TextView)mView.findViewById(R.id.dialog_add_category_add);

        if(status==2){
            title.setText("Изменения сабкатегории");
            dialog_intended_add.setText("Изменить");
        }

        dialog_intended_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(status==2){
                    dialog.dismiss();
                    mPresenter.onEditClick(id,dialog_intended_name.getText().toString(),String.valueOf(CATEGORY_ID));
                }else {
                    dialog.dismiss();
                    mPresenter.onSendAddSubClick(String.valueOf(CATEGORY_ID),dialog_intended_name.getText().toString());
                }
            }
        });
    }

    @Override
    public void openProduct(int position) {
        SupplierGoodsFragment supplierGoodsFragment=new SupplierGoodsFragment();
        bundle=new Bundle();
        bundle.putInt(SUBCATEGORY_ID_KEY,itemList.get(position).getId());
        bundle.putString(SUBCATEGORY_NAME_KEY,itemList.get(position).getTitle());
        Log.d("myTag", "openProduct: id"+itemList.get(position).getId());
        bundle.putInt(SupplierCategoryFragment.CATEGORY_ID_KEY, CATEGORY_ID);
        bundle.putString(SupplierCategoryFragment.CATEGORY_NAME_KEY, CATEGORY_NAME);
        supplierGoodsFragment.setArguments(bundle);

        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .disallowAddToBackStack()
                .replace(R.id.supplier_content_frame, supplierGoodsFragment, SUPPLIER_TAG_GOODS)
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
                mPresenter.onDeleteClick(itemList.get(position).getId(),String.valueOf(CATEGORY_ID));
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
    public void openEditDialog(String name) {

    }

    @Override
    protected void setUp(View view) {
        itemList=new ArrayList<>();
        titleCategory.setText(CATEGORY_NAME);
        linearLayoutManager=new LinearLayoutManager(getActivity());
        category.setLayoutManager(linearLayoutManager);
        supplierCategoryAdapter.addPresenter(mPresenter);

        category.setAdapter(supplierCategoryAdapter);
        mPresenter.onViewPrepared(String.valueOf(CATEGORY_ID));

        supplierCategoryAdapter.setListener(new Listener() {
            @Override
            public void onClick(int position) {
                mPresenter.onSubItemClick(position);
            }
        });
    }

    @Override
    public void onDestroyView() {
        mPresenter.onDetach();
        super.onDestroyView();
    }
}
