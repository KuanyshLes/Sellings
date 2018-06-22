package kz.production.kuanysh.sellings.ui.content_owner.fragments.basket;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import kz.production.kuanysh.sellings.R;
import kz.production.kuanysh.sellings.model.Product;
import kz.production.kuanysh.sellings.ui.content_owner.utils.adapters.BasketItemsAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class BasketFragment extends Fragment {
    private ImageView back;
    private RecyclerView recyclerView;
    private CheckBox consignation;
    private RadioGroup payment_way;
    private TextView basket_price,send;
    private LinearLayoutManager linearLayoutManager;
    private BasketItemsAdapter basketItemsAdapter;
    private List<Product> order_detail_items;
    private DrawerLayout drawer;


    public BasketFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_basket, container, false);

        drawer = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);

        back=(ImageView)view.findViewById(R.id.basket_toolbar_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(GravityCompat.START);
            }
        });


        recyclerView=(RecyclerView)view.findViewById(R.id.basket_items);
        consignation=(CheckBox)view.findViewById(R.id.basket_consignation);
        payment_way=(RadioGroup)view.findViewById(R.id.basket_radiogroup);
        basket_price=(TextView)view.findViewById(R.id.basket_all_price);
        send=(TextView)view.findViewById(R.id.basket_send);


        order_detail_items=new ArrayList<>();
        order_detail_items.add(new Product("Молоко",1,1600)) ;
        order_detail_items.add(new Product("Кефир",1,1600)) ;
        order_detail_items.add(new Product("Шоколад",1,1600)) ;
        order_detail_items.add(new Product("Кока-Кола",1,1600)) ;
        order_detail_items.add(new Product("Фанта",1,1600)) ;
        order_detail_items.add(new Product("Сметана",1,1600)) ;
        order_detail_items.add(new Product("Сыр",1,1600)) ;

        linearLayoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        basketItemsAdapter=new BasketItemsAdapter(order_detail_items,getActivity());
        recyclerView.setAdapter(basketItemsAdapter);


        return view;
    }

}
