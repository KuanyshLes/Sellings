package kz.production.kuanysh.sellings.ui.content_suppiler.utils.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import kz.production.kuanysh.sellings.R;
import kz.production.kuanysh.sellings.data.model.Product;
import kz.production.kuanysh.sellings.ui.content_owner.utils.Listener;
import kz.production.kuanysh.sellings.utils.AppConstants;

/**
 * Created by User on 15.06.2018.
 */

public class SupplierOrderDetailAdapter extends RecyclerView.Adapter<SupplierOrderDetailAdapter.ViewHolder>{
    private List<kz.production.kuanysh.sellings.data.network.model.supplier.orderdetail.Product> productItemList;
    private Listener listener;

    public SupplierOrderDetailAdapter(List<kz.production.kuanysh.sellings.data.network.model.supplier.orderdetail.Product> productItemList) {
        this.productItemList = productItemList;
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
                .inflate(R.layout.supplier_order_detail_row_item, parent, false);
        return new SupplierOrderDetailAdapter.ViewHolder(cv);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        CardView cardView = holder.cardView;

        TextView name = (TextView) cardView.findViewById(R.id.supplier_order_detail_product_name);
        TextView amount = (TextView) cardView.findViewById(R.id.supplier_order_detail_product_amount);
        TextView price= (TextView) cardView.findViewById(R.id.supplier_order_detail_product_price);

        name.setText(productItemList.get(position).getTitle().toString());
        amount.setText(productItemList.get(position).getNumberOfStock()+"");
        price.setText(productItemList.get(position).getPrice()+ AppConstants.MONEY_TYPE);


    }

    @Override
    public int getItemCount() {
        return productItemList.size();
    }

    public void addItems(List<kz.production.kuanysh.sellings.data.network.model.supplier.orderdetail.Product> products){
        productItemList.addAll(products);
        notifyDataSetChanged();
    }


}
