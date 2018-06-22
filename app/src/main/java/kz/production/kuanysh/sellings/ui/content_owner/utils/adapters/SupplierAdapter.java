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
import kz.production.kuanysh.sellings.model.SupplierItem;
import kz.production.kuanysh.sellings.ui.content_owner.utils.Listener;


public class SupplierAdapter extends RecyclerView.Adapter<SupplierAdapter.ViewHolder> {
    private List<SupplierItem> supplierItemList;
    private Context context;
    private Listener listener;



    public SupplierAdapter(List<SupplierItem> supplierItemList, Context context) {
        this.supplierItemList = supplierItemList;
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
                .inflate(R.layout.suppliers_row_item, viewGroup, false);
        return new ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int i) {
        CardView cardView = viewHolder.cardView;

        TextView name = (TextView) cardView.findViewById(R.id.supplier_name);
        TextView category = (TextView) cardView.findViewById(R.id.supplier_category);
        TextView workingTime = (TextView) cardView.findViewById(R.id.supplier_time);
        TextView address = (TextView) cardView.findViewById(R.id.supplier_address);

        name.setText("\""+supplierItemList.get(i).getName()+"\"");
        category.setText(supplierItemList.get(i).getCategory());
        workingTime.setText(supplierItemList.get(i).getWorkingTime());
        address.setText(supplierItemList.get(i).getAddress());

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


}
