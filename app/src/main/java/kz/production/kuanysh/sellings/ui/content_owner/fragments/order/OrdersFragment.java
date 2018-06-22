package kz.production.kuanysh.sellings.ui.content_owner.fragments.order;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import kz.production.kuanysh.sellings.R;
import kz.production.kuanysh.sellings.model.OrderItem;
import kz.production.kuanysh.sellings.ui.content_owner.utils.Listener;
import kz.production.kuanysh.sellings.ui.content_owner.utils.adapters.OrdersAdapter;

import static kz.production.kuanysh.sellings.ui.content_owner.MainActivity.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrdersFragment extends Fragment {
    private RecyclerView ordersRecycler;
    private OrdersAdapter ordersAdapter;
    private LinearLayoutManager linearLayoutManager;
    private List<OrderItem> order_list;
    private Bundle bundle;
    public static final String TAG_ORDER="order";
    private ImageView back;
    private DrawerLayout drawerLayout;


    public OrdersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_orders, container, false);

        back=(ImageView)view.findViewById(R.id.orders_toolbar_back);
        ordersRecycler=(RecyclerView)view.findViewById(R.id.supplier_list);
        drawerLayout=(DrawerLayout)getActivity().findViewById(R.id.drawer_layout);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        order_list=new ArrayList<>();
        order_list.add(new OrderItem("1523563", "Магазин Нур Астана","26.04.2018","Получен"));
        order_list.add(new OrderItem("1523563", "Магазин Нур Астана","26.04.2018","Получен"));
        order_list.add(new OrderItem("1523563", "Магазин Нур Астана","26.04.2018","Получен"));
        order_list.add(new OrderItem("1523563", "Магазин Нур Астана","26.04.2018","Получен"));
        order_list.add(new OrderItem("1523563", "Магазин Нур Астана","26.04.2018","Получен"));
        order_list.add(new OrderItem("1523563", "Магазин Нур Астана","26.04.2018","Получен"));
        order_list.add(new OrderItem("1523563", "Магазин Нур Астана","26.04.2018","Получен"));
        order_list.add(new OrderItem("1523563", "Магазин Нур Астана","26.04.2018","Получен"));
        order_list.add(new OrderItem("1523563", "Магазин Нур Астана","26.04.2018","Получен"));
        order_list.add(new OrderItem("1523563", "Магазин Нур Астана","26.04.2018","Получен"));

        linearLayoutManager=new LinearLayoutManager(getActivity());
        ordersAdapter=new OrdersAdapter(order_list,getActivity());

        ordersRecycler.setLayoutManager(linearLayoutManager);
        ordersRecycler.setAdapter(ordersAdapter);

        ordersAdapter.setListener(new Listener() {
            @Override
            public void onClick(int position) {
                OrderDetailFragment orderDetailFragment=new OrderDetailFragment();
                bundle = new Bundle();
                bundle.putParcelable(TAG_ORDER, order_list.get(position));
                orderDetailFragment.setArguments(bundle);
                FragmentTransaction transaction= ((AppCompatActivity)getActivity()).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.content_frame,orderDetailFragment, TAG).commit();
            }
        });




        return view;
    }

}
