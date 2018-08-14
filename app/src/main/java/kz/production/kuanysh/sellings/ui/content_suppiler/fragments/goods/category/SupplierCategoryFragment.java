package kz.production.kuanysh.sellings.ui.content_suppiler.fragments.goods.category;


import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
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
import kz.production.kuanysh.sellings.data.network.model.owner.category.Result;
import kz.production.kuanysh.sellings.di.component.ActivityComponent;
import kz.production.kuanysh.sellings.ui.base.BaseFragment;
import kz.production.kuanysh.sellings.ui.content_owner.utils.Listener;
import kz.production.kuanysh.sellings.ui.content_suppiler.fragments.goods.subcategory.SupplierGoodsSubcategoryFragment;
import kz.production.kuanysh.sellings.ui.content_suppiler.utils.adapters.SupplierCategoryAdapter;

import static kz.production.kuanysh.sellings.ui.content_suppiler.activity.SupplierActivity.SUPPLIER_TAG_GOODS;

/**
 * A simple {@link Fragment} subclass.
 */
public class SupplierCategoryFragment extends BaseFragment implements SupplierCategoryMvpView {

    @Inject
    SupplierCategoryPresenter<SupplierCategoryMvpView> mPresenter;

    @BindView(R.id.supplier_category_toolbar_drawer)
    ImageView back;

    @BindView(R.id.supplier_category_toolbar_add_category)
    ImageView addCategory;

    @BindView(R.id.supplier_category_list)
    RecyclerView category;

    @Inject
    SupplierCategoryAdapter supplierCategoryAdapter;

    @BindView(R.id.id_nullable)
    View nullText;




    private LinearLayoutManager linearLayoutManager;
    private static List<String> categoryItemList;
    private static List<Result> itemList;
    private static Dialog dialog;
    private static AlertDialog.Builder mBuilder;
    private EditText dialog_intended_name;
    private TextView dialog_intended_add;
    private Bundle bundle;
    private DrawerLayout drawerLayout;
    public static final String CATEGORY_ID_KEY ="key";
    public static final String CATEGORY_NAME_KEY ="keyName";

    public SupplierCategoryFragment() {
        // Required empty public constructor
    }
    public static SupplierCategoryFragment newInstance() {
        Bundle args = new Bundle();
        SupplierCategoryFragment fragment = new SupplierCategoryFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_suppiler_category, container, false);
        drawerLayout=(DrawerLayout)getActivity().findViewById(R.id.supplier_drawer_layout);

        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            setUnBinder(ButterKnife.bind(this, view));
            mPresenter.onAttach(this);
        }

        return view;


    }


    @OnClick(R.id.supplier_category_toolbar_drawer)
    public void goOpenDrawer(){
        mPresenter.onDrawerClick();
    }

    @OnClick(R.id.supplier_category_toolbar_add_category)
    public void goAddCategory(){
        mPresenter.onAddCategoryClick();
    }


    @Override
    public void openDrawer() {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    @Override
    public void updateCategory(List<Result> categoryA) {
        category.setVisibility(View.VISIBLE);
        nullText.setVisibility(View.GONE);
        itemList.clear();
        itemList.addAll(categoryA);
        supplierCategoryAdapter.addItems(itemList);
        Log.d("myTag", "updateCategory: size "+itemList.size());
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

        dialog_intended_name=(EditText) mView.findViewById(R.id.dialog_add_category_name);
        dialog_intended_add=(TextView)mView.findViewById(R.id.dialog_add_category_add);

        if(status==2){
            title.setText("Изменения категории");
            dialog_intended_add.setText("Изменить");
        }

        dialog_intended_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(status==2){
                    mPresenter.onEditClick(id,dialog_intended_name.getText().toString());
                    dialog.dismiss();
                }else{
                    mPresenter.addCategoryClick(dialog_intended_name.getText().toString());
                    dialog.dismiss();

                }
            }
        });
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
                mPresenter.onDeleteClick(itemList.get(position).getId());
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
    public void openSubcategory(int position) {
        SupplierGoodsSubcategoryFragment supplierSubcategoryFragment=new SupplierGoodsSubcategoryFragment();
        bundle=new Bundle();
        bundle.putInt(CATEGORY_ID_KEY,itemList.get(position).getId());
        bundle.putString(CATEGORY_NAME_KEY,itemList.get(position).getTitle());
        Log.d("myTag", "category id: " + position);
        supplierSubcategoryFragment.setArguments(bundle);
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .disallowAddToBackStack()
                .replace(R.id.supplier_content_frame, supplierSubcategoryFragment, SUPPLIER_TAG_GOODS)
                .commit();

    }

    @Override
    public void openEditDialog(String name) {

    }

    @Override
    protected void setUp(View view) {
        categoryItemList=new ArrayList<>();
        itemList=new ArrayList<>();


        linearLayoutManager=new LinearLayoutManager(getActivity());
        category.setLayoutManager(linearLayoutManager);
        category.setAdapter(supplierCategoryAdapter);
        supplierCategoryAdapter.addPresenter(mPresenter);

        supplierCategoryAdapter.setListener(new Listener() {
            @Override
            public void onClick(int position) {
                mPresenter.onItemClick(position);
            }
        });
        mPresenter.onViewPrepared();
        supplierCategoryAdapter.addContext(getActivity());
    }

    @Override
    public void onDestroyView() {
        mPresenter.onDetach();
        super.onDestroyView();
    }
}
