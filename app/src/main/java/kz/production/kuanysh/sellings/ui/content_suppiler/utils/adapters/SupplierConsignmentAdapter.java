package kz.production.kuanysh.sellings.ui.content_suppiler.utils.adapters;

import android.content.Context;
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
import kz.production.kuanysh.sellings.data.model.ConsignmentItem;
import kz.production.kuanysh.sellings.data.network.model.supplier.consignment.Result;
import kz.production.kuanysh.sellings.ui.content_owner.utils.Listener;
import kz.production.kuanysh.sellings.utils.AppConstants;

/**
 * Created by User on 12.06.2018.
 */

public class SupplierConsignmentAdapter extends RecyclerView.Adapter<SupplierConsignmentAdapter.ViewHolder>{
    private List<Result> consignmentItemList;
    private Listener listener;
    private Context mContext;

    public SupplierConsignmentAdapter(List<Result> consignmentItemList) {
        this.consignmentItemList = consignmentItemList;
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
        ImageView image= (ImageView) cardView.findViewById(R.id.supplier_consignment_item_image);

        name.setText(consignmentItemList.get(position).getShopName().replace("\"",""));
        time.setText(consignmentItemList.get(position).getTillDate());
        price.setText(consignmentItemList.get(position).getCredit()+ AppConstants.MONEY_TYPE);
        if(consignmentItemList.get(position).getProviderImage()!=null){
            Glide.with(mContext).load(consignmentItemList.get(position).getProviderImage()).into(image);
        }


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

    public void addItems(List<Result> list){
        consignmentItemList.addAll(list);
        notifyDataSetChanged();
    }
    public void addContext(Context context){
        mContext=context;
    }


}
