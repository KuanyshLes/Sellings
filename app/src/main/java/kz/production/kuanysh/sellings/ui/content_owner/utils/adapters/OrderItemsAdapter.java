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
import kz.production.kuanysh.sellings.model.Product;
import kz.production.kuanysh.sellings.ui.content_owner.utils.Listener;

/**
 * Created by User on 10.06.2018.
 */

public class OrderItemsAdapter extends RecyclerView.Adapter<OrderItemsAdapter.ViewHolder> {
    private List<Product> products;
    private Context context;
    private Listener listener;



    public OrderItemsAdapter(List<Product> products, Context context) {
        this.products = products;
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
                .inflate(R.layout.order_product_row_item, viewGroup, false);
        return new ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int i) {
        CardView cardView = viewHolder.cardView;

        TextView name = (TextView) cardView.findViewById(R.id.detail_product_name);
        TextView amount = (TextView) cardView.findViewById(R.id.detail_product_amount);
        TextView price= (TextView) cardView.findViewById(R.id.detail_product_price);

        name.setText(products.get(i).getName().toString());
        amount.setText(products.get(i).getCount()+"");
        price.setText(products.get(i).getPrice()+"тг");

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
        return products.size();
    }


}
