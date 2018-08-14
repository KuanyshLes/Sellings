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

import com.bumptech.glide.Glide;

import java.util.List;

import kz.production.kuanysh.sellings.R;
import kz.production.kuanysh.sellings.data.model.OwnerItem;
import kz.production.kuanysh.sellings.data.network.model.supplier.orders.Order;
import kz.production.kuanysh.sellings.ui.content_owner.utils.Colors;
import kz.production.kuanysh.sellings.ui.content_owner.utils.Listener;
import kz.production.kuanysh.sellings.utils.AppConstants;

/**
 * Created by User on 12.06.2018.
 */

public class SupplierOrderAdapter extends RecyclerView.Adapter<SupplierOrderAdapter.ViewHolder>{
    private List<Order> ownerItemList;
    private Listener listener;

    public SupplierOrderAdapter(List<Order> ownerItemList) {

        this.ownerItemList = ownerItemList;
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
        Integer statusText=ownerItemList.get(position).getStatus();

        TextView name = (TextView) cardView.findViewById(R.id.supplier_order_item_name);
        TextView time= (TextView) cardView.findViewById(R.id.supplier_order_item_time);
        TextView address = (TextView) cardView.findViewById(R.id.supplier_order_item_address);
        TextView status= (TextView) cardView.findViewById(R.id.supplier_order_item_status);
        ImageView image = (ImageView) cardView.findViewById(R.id.supplier_order_item_image);

        if(ownerItemList.get(position).getTitle()!=null){
            name.setText(ownerItemList.get(position).getTitle().replace("\"",""));
        }
        if(ownerItemList.get(position).getCreatedAt()!=null){
            time.setText(ownerItemList.get(position).getCreatedAt());
        }
        if(ownerItemList.get(position).getAddress()!=null){
            address.setText(ownerItemList.get(position).getAddress().replace("\"",""));
        }

        if(ownerItemList.get(position).getImage()!=null){
            Glide.with(image.getContext()).load(ownerItemList.get(position).getImage())
                    .into(image);
        }

        if(statusText.equals(AppConstants.SUPPLIER_ORDER_STATUS_CONFIRM)){
            status.setTextColor(Color.parseColor(Colors.CONFIRMED_GREEN));
            status.setText(AppConstants.SUPPLIER_ORDER_STATUS_CONFIRM_TEXT);
        }else if (statusText.equals(AppConstants.SUPPLIER_ORDER_STATUS_CANCELL)){
            status.setText(AppConstants.SUPPLIER_ORDER_STATUS_CANCELL_TEXT);
            status.setTextColor(Color.parseColor(Colors.CANCELLED_RED));
        }else{
            status.setText(AppConstants.SUPPLIER_ORDER_STATUS_WAITING_TEXT);
            status.setTextColor(Color.parseColor(Colors.WAITING));
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

    public void addItems(List<Order> orders){
        ownerItemList=orders;
        notifyDataSetChanged();
    }


}
