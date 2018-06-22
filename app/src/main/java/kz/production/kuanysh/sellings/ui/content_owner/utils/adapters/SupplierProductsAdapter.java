package kz.production.kuanysh.sellings.ui.content_owner.utils.adapters;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import kz.production.kuanysh.sellings.R;
import kz.production.kuanysh.sellings.ui.content_owner.utils.Listener;

/**
 * Created by User on 10.06.2018.
 */

public class SupplierProductsAdapter extends RecyclerView.Adapter<SupplierProductsAdapter.ViewHolder> {
    private List<String> supplierItemList;
    private Context context;
    private Listener listener;



    public SupplierProductsAdapter(List<String> supplierItemList,Context context) {
        this.supplierItemList=supplierItemList;
        this.context = context;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name_product;
        private ImageView next;
        public ViewHolder(View itemView) {
            super(itemView);

            name_product=(TextView)itemView.findViewById(R.id.supplier_product_name);
            next=(ImageView)itemView.findViewById(R.id.supplier_product_next);

        }
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, final int i) {
        ConstraintLayout cv = (ConstraintLayout) LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.suppliers_products_row_item, viewGroup, false);
        cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener!=null){
                    listener.onClick(i);
                }
            }
        });
        return new ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int i) {

        viewHolder.name_product.setText(supplierItemList.get(i).toString());

    }
    @Override
    public int getItemCount() {
        return supplierItemList.size();
    }


}
