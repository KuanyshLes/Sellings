package kz.production.kuanysh.sellings.ui.content_suppiler.fragments.goods;


import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import kz.production.kuanysh.sellings.R;
import kz.production.kuanysh.sellings.model.Product;
import kz.production.kuanysh.sellings.ui.content_suppiler.utils.adapters.GoodsItemsAdapter;

import static kz.production.kuanysh.sellings.ui.content_suppiler.SupplierActivity.SUPPLIER_VISIBLE_FRAGMENT_TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class SupplierGoodsFragment extends Fragment {
    private ImageView back,addGoods;
    private TextView toolbar_title;
    private ConstraintLayout document;
    private RecyclerView goods;
    private LinearLayoutManager linearLayoutManager;
    private GoodsItemsAdapter goodsItemsAdapter;
    private DrawerLayout drawerLayout;
    private List<Product> goods_list;

    public SupplierGoodsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_supplier_goods, container, false);

        back=(ImageView)view.findViewById(R.id.supplier_goods_toolbar_drawer);
        addGoods=(ImageView)view.findViewById(R.id.supplier_goods_toolbar_add_goods);
        drawerLayout=(DrawerLayout)getActivity().findViewById(R.id.supplier_drawer_layout);
        toolbar_title=(TextView)view.findViewById(R.id.supplier_goods_toolbar_title);

        opendrawerlistener();
        addGoodsListener();

        addCustomInfo();

        goods=(RecyclerView)view.findViewById(R.id.supplier_goods_recycler);
        linearLayoutManager=new LinearLayoutManager(getActivity());
        goods.setLayoutManager(linearLayoutManager);

        goodsItemsAdapter=new GoodsItemsAdapter(goods_list,getActivity());
        goods.setAdapter(goodsItemsAdapter);



        return view;
    }
    //open drawer
    private void opendrawerlistener(){
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

    }
    private void addGoodsListener(){
        addGoods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.supplier_content_frame, new SupplierAddGoodFragment(), SUPPLIER_VISIBLE_FRAGMENT_TAG)
                        .addToBackStack(null)
                        .commit();
            }
        });

    }
    private void addCustomInfo(){
        goods_list=new ArrayList<>();
        goods_list.add(new Product("Молоко Моё",1,1600)) ;
        goods_list.add(new Product("Кефир",1,1600)) ;
        goods_list.add(new Product("Шоколад",1,1600)) ;
        goods_list.add(new Product("Кока-Кола",1,1600)) ;
        goods_list.add(new Product("Фанта",1,1600)) ;
        goods_list.add(new Product("Сметана",1,1600)) ;
        goods_list.add(new Product("Сыр",1,1600)) ;
    }

}
