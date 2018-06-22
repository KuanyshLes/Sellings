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
import kz.production.kuanysh.sellings.model.ConsignmentItem;
import kz.production.kuanysh.sellings.model.OwnerItem;
import kz.production.kuanysh.sellings.ui.content_owner.utils.Colors;
import kz.production.kuanysh.sellings.ui.content_owner.utils.Listener;

/**
 * Created by User on 12.06.2018.
 */

public class SupplierConsignmentAdapter extends RecyclerView.Adapter<SupplierConsignmentAdapter.ViewHolder>{
    private List<ConsignmentItem> consignmentItemList;
    private Context context;
    private Listener listener;

    public SupplierConsignmentAdapter(List<ConsignmentItem> consignmentItemList, Context context) {
        this.consignmentItemList = consignmentItemList;
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
                .inflate(R.layout.consignment_row_item, parent, false);
        return new SupplierConsignmentAdapter.ViewHolder(cv);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        CardView cardView = holder.cardView;

        TextView name = (TextView) cardView.findViewById(R.id.supplier_consignment_item_name);
        TextView time= (TextView) cardView.findViewById(R.id.supplier_consignment_item_time);
        TextView price = (TextView) cardView.findViewById(R.id.supplier_consignment_item_price);

        name.setText("\""+consignmentItemList.get(position).getName()+"\"");
        time.setText(consignmentItemList.get(position).getTime());
        price.setText(consignmentItemList.get(position).getPrice()+" тг");


        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener!=null){
                    listener.onClick(position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return consignmentItemList.size();
    }


}
