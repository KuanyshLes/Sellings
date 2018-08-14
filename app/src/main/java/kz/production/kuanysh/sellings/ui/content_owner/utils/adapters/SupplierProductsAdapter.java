package kz.production.kuanysh.sellings.ui.content_owner.utils.adapters;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import kz.production.kuanysh.sellings.R;
import kz.production.kuanysh.sellings.data.network.model.owner.category.Result;
import kz.production.kuanysh.sellings.ui.content_owner.fragments.main.category.SupplierProductFragment;
import kz.production.kuanysh.sellings.ui.content_owner.utils.Listener;

/**
 * Created by User on 10.06.2018.
 */

public class SupplierProductsAdapter extends RecyclerView.Adapter<SupplierProductsAdapter.ViewHolder> {
    private List<Result> supplierItemList;
    private Context context;
    private Listener listener;
    private SupplierProductFragment supplierProductFragment;



    public SupplierProductsAdapter(List<Result> supplierItemList, SupplierProductFragment supplierProductFragment) {
        this.supplierItemList=supplierItemList;
        this.supplierProductFragment = supplierProductFragment;
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
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, final int i) {
        CardView cv = (CardView) LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.suppliers_products_row_item, viewGroup, false);
        return new ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int i) {

        CardView cardView = viewHolder.cardView;

        TextView name_product=(TextView)cardView.findViewById(R.id.supplier_product_name);
        name_product.setText(supplierItemList.get(i).getTitle());

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
        return supplierItemList.size();
    }

    public void addItems(List<Result> list){
        supplierItemList.clear();
        supplierItemList.addAll(list);
        notifyDataSetChanged();
    }


}
