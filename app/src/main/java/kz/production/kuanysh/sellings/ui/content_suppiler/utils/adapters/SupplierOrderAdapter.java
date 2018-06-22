package kz.production.kuanysh.sellings.ui.content_suppiler.utils.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import kz.production.kuanysh.sellings.R;
import kz.production.kuanysh.sellings.model.OwnerItem;
import kz.production.kuanysh.sellings.ui.content_owner.utils.Colors;
import kz.production.kuanysh.sellings.ui.content_owner.utils.Listener;

/**
 * Created by User on 12.06.2018.
 */

public class SupplierOrderAdapter extends RecyclerView.Adapter<SupplierOrderAdapter.ViewHolder>{
    private List<OwnerItem> ownerItemList;
    private Context context;
    private Listener listener;

    public SupplierOrderAdapter(List<OwnerItem> ownerItemList, Context context) {

        this.ownerItemList = ownerItemList;
        this.context = context;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
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
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView cv = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.supplier_orders_row_item, parent, false);
        return new SupplierOrderAdapter.ViewHolder(cv);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        CardView cardView = holder.cardView;
        String statusText=ownerItemList.get(position).getStatus();

        TextView name = (TextView) cardView.findViewById(R.id.supplier_order_item_name);
        TextView time= (TextView) cardView.findViewById(R.id.supplier_order_item_time);
        TextView address = (TextView) cardView.findViewById(R.id.supplier_order_item_address);
        TextView status= (TextView) cardView.findViewById(R.id.supplier_order_item_status);
        ImageView image = (ImageView) cardView.findViewById(R.id.supplier_order_item_image);

        name.setText("\""+ownerItemList.get(position).getName()+"\"");
        time.setText(ownerItemList.get(position).getTime());
        address.setText(ownerItemList.get(position).getAddress());
        status.setText(ownerItemList.get(position).getStatus());

        if(statusText.equals(OwnerItem.STATUS_CONFIRMED)){
            status.setTextColor(Color.parseColor(Colors.CONFIRMED_GREEN));
        }else if (statusText.equals(OwnerItem.STATUS_CANCELLED)){
            status.setTextColor(Color.parseColor(Colors.CANCELLED_RED));
        }

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(position);
            }
        });
        //image.setImageResource();
    }

    @Override
    public int getItemCount() {
        return ownerItemList.size();
    }


}
