package kz.production.kuanysh.sellings.ui.content_owner.fragments.order;


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

import java.util.ArrayList;
import java.util.List;

import kz.production.kuanysh.sellings.R;
import kz.production.kuanysh.sellings.model.OrderItem;
import kz.production.kuanysh.sellings.model.Product;
import kz.production.kuanysh.sellings.ui.content_owner.utils.adapters.OrderItemsAdapter;

import static kz.production.kuanysh.sellings.ui.content_owner.MainActivity.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderDetailFragment extends Fragment {
    private TextView name,status,priceAll,toolbar_title;
    private RecyclerView recyclerView;
    private TextView repeatOrderBtn;
    private ImageView backImage;
    private OrderItem orderItem;
    private LinearLayoutManager linearLayoutManager;
    private List<Product> order_detail_items;
    private OrderItemsAdapter orderItemsAdapter;
    private ImageView back;
    private OrdersFragment ordersFragment;
    private FragmentTransaction fragmentTransaction;

    public OrderDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_order_detail, container, false);
        final Bundle bundle = getArguments();
        if (bundle != null){
            orderItem = (OrderItem) bundle.get(OrdersFragment.TAG_ORDER);
        }

        back=(ImageView)view.findViewById(R.id.orders_detail_toolbar_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ordersFragment=new OrdersFragment();
                fragmentTransaction= ((AppCompatActivity)getActivity()).getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.content_frame,ordersFragment, TAG).commit();
            }
        });
        order_detail_items=new ArrayList<>();
        order_detail_items.add(new Product("Молоко Моё,1.6",1,1600)) ;
        order_detail_items.add(new Product("Молоко Моё,3.2",1,1600)) ;
        order_detail_items.add(new Product("Молоко Моё,1.6",1,1600)) ;
        order_detail_items.add(new Product("Молоко Моё,3.2",1,1600)) ;

        name=(TextView)view.findViewById(R.id.order_detail_name);
        toolbar_title=(TextView)view.findViewById(R.id.orders_detail_toolbar_title);
        status=(TextView)view.findViewById(R.id.order_detail_status);
        priceAll=(TextView)view.findViewById(R.id.order_detail_price_all);

        recyclerView=(RecyclerView) view.findViewById(R.id.order_detail_products_items);
        repeatOrderBtn=(TextView) view.findViewById(R.id.order_detail_repeat_order_btn);
        backImage=(ImageView)view.findViewById(R.id.orders_detail_toolbar_back);


        toolbar_title.setText("Заказ №"+orderItem.getNumber().toString());
        name.setText(orderItem.getOwner().toString());
        status.setText(orderItem.getStatus().toString());
        priceAll.setText("3200тг");

        orderItemsAdapter=new OrderItemsAdapter(order_detail_items,getActivity());
        linearLayoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setAdapter(orderItemsAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);


        return view;
    }

}
