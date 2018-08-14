package kz.production.kuanysh.sellings.ui.content_owner.utils.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import kz.production.kuanysh.sellings.R;
import kz.production.kuanysh.sellings.data.model.OrderItem;
import kz.production.kuanysh.sellings.data.network.model.owner.orderlist.Result;
import kz.production.kuanysh.sellings.ui.content_owner.utils.Colors;
import kz.production.kuanysh.sellings.ui.content_owner.utils.Listener;
import kz.production.kuanysh.sellings.utils.AppConstants;

/**
 * Created by User on 10.06.2018.
 */
public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.ViewHolder> {
    private List<Result> orders;
    private Listener listener;



    public OrdersAdapter(List<Result> orders) {
        this.orders= orders;
    }



    class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        public ViewHolder(CardView itemView) {
            super(itemView);
            cardView=itemView;

        }
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        CardView cv = (CardView) LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.orders_row_item, viewGroup, false);
        return new ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int i) {
        CardView cardView = viewHolder.cardView;

        TextView number = (TextView) cardView.findViewById(R.id.order_number);
        TextView owner = (TextView) cardView.findViewById(R.id.supplier_goods_item_name);
        TextView time= (TextView) cardView.findViewById(R.id.order_time);
        TextView status= (TextView) cardView.findViewById(R.id.order_status);


        number.setText("Заказ №"+orders.get(i).getId());
        owner.setText(orders.get(i).getProviderName().replace("\"",""));
        time.setText(orders.get(i).getUpdatedAt().replace("\"",""));
        if(Integer.parseInt(orders.get(i).getStatusId().toString())== AppConstants.SUPPLIER_ORDER_STATUS_CONFIRM){
            status.setText(AppConstants.OWNER_ORDER_STATUS_CONFIRM_TEXT);
            status.setTextColor(Color.parseColor(Colors.CONFIRMED_GREEN));
        }else if(Integer.parseInt(orders.get(i).getStatusId().toString())== AppConstants.SUPPLIER_ORDER_STATUS_CANCELL){
            status.setText(AppConstants.OWNER_ORDER_STATUS_CANCELL_TEXT);
            status.setTextColor(Color.parseColor(Colors.CANCELLED_RED));
        }else if(Integer.parseInt(orders.get(i).getStatusId().toString())== AppConstants.SUPPLIER_ORDER_STATUS_WAITING){
            status.setText(AppConstants.OWNER_ORDER_STATUS_WAITING_TEXT);
            status.setTextColor(Color.parseColor(Colors.WAITING));
        }



        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener!=null){
                   listener.onClick(i);
                }
            }
        });
    }
    @Override
    public int getItemCount() {
        return orders.size();
    }

    public void addItems(List<Result> list){
        orders.addAll(list);
        notifyDataSetChanged();

    }


}
