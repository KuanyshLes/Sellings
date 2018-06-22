package kz.production.kuanysh.sellings.ui.content_owner.utils.adapters;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.concurrent.TimeUnit;

import kz.production.kuanysh.sellings.R;
import kz.production.kuanysh.sellings.model.Product;
import kz.production.kuanysh.sellings.ui.content_owner.utils.Listener;

/**
 * Created by User on 11.06.2018.
 */

public class BasketItemsAdapter extends RecyclerView.Adapter<BasketItemsAdapter.ViewHolder> {
    private List<Product> products;
    private Context context;
    private Listener listener;


    public BasketItemsAdapter(List<Product> products, Context context) {
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
                .inflate(R.layout.basket_items_row_item, viewGroup, false);
        return new ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int i) {
        CardView cardView = viewHolder.cardView;

        TextView name = (TextView) cardView.findViewById(R.id.supplier_goods_item_name);
        final TextView amount = (TextView) cardView.findViewById(R.id.basket_item_amount);
        TextView price= (TextView) cardView.findViewById(R.id.basket_item_price);

        ImageView minus=(ImageView)cardView.findViewById(R.id.basket_item_minus_sign);
        ImageView plus=(ImageView)cardView.findViewById(R.id.supplier_good_item_edit);
        ImageView trash=(ImageView)cardView.findViewById(R.id.supplier_good_item_delete);

        final String amountCount="50";
        name.setText(products.get(i).getName().toString());
        price.setText(products.get(i).getPrice()+"тг");

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count=Integer.parseInt(amount.getText().toString());
                count+=1;
                amount.setText(count+"");
            }
        });
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Integer.parseInt(amount.getText().toString())>=1){
                    int count=Integer.parseInt(amount.getText().toString());
                    count-=1;
                    amount.setText(count+"");
                }else{
                    Toast.makeText(context, "Осталось "+amount.getText().toString()+" штук", Toast.LENGTH_SHORT).show();
                }


            }
        });
        trash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                products.remove(i);
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Snackbar.make(v, "Успешно удалено!", Snackbar.LENGTH_LONG).show();
                notifyDataSetChanged();
            }
        });


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
