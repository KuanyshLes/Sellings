package kz.production.kuanysh.sellings.ui.content_owner.utils.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import kz.production.kuanysh.sellings.R;
import kz.production.kuanysh.sellings.model.OrderItem;
import kz.production.kuanysh.sellings.ui.content_owner.utils.Listener;

/**
 * Created by User on 10.06.2018.
 */
public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.ViewHolder> {
    private List<OrderItem> orders;
    private Context context;
    private Listener listener;



    public OrdersAdapter(List<OrderItem> orders, Context context) {
        this.orders= orders;
        this.context = context;
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


        number.setText("Заказ №"+orders.get(i).getNumber().toString());
        owner.setText(orders.get(i).getOwner());
        time.setText(orders.get(i).getTime());
        status.setText(orders.get(i).getStatus());


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


}
