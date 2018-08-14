package kz.production.kuanysh.sellings.ui.content_owner.utils.adapters;


import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;


import kz.production.kuanysh.sellings.R;
import kz.production.kuanysh.sellings.data.network.model.owner.all_provider.Provider;
import kz.production.kuanysh.sellings.ui.content_owner.fragments.main.main.OwnerSupplierItemFragment;
import kz.production.kuanysh.sellings.ui.content_owner.utils.Listener;


public class SupplierAdapter extends RecyclerView.Adapter<SupplierAdapter.ViewHolder> {
    private List<Provider> supplierItemList;
    private OwnerSupplierItemFragment context;
    private Listener listener;


    public SupplierAdapter(ArrayList<Provider> providers, OwnerSupplierItemFragment context) {
        this.context = context;
        this.supplierItemList=providers;
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
        ImageView imageView=(ImageView)cardView.findViewById(R.id.supplier_image);

        String categoryString="";
        name.setText(supplierItemList.get(i).getTitle().replace("\"",""));
        for(int j=0;j<supplierItemList.get(i).getCategories().size();j++){
            categoryString+=supplierItemList.get(i).getCategories().get(j).getTitle()+",";
            category.setText(categoryString.toString());
        }
        workingTime.setText(supplierItemList.get(i).getFromHours().replace("\"","")+"-"+
                supplierItemList.get(i).getToHours().replace("\"",""));
        address.setText(supplierItemList.get(i).getAddress().replace("\"",""));
        if(categoryString.length()!=0){
            category.setText(categoryString.substring(0,categoryString.length()-1));
        }
        if(category.getText().length()>25){
            category.setText(categoryString.substring(0,25)+"...");
        }

        Glide.with(imageView.getContext())
                .load(supplierItemList.get(i).getImage())
                .into(imageView);

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

    public void addItems(List<Provider> providers) {
        //chat_item.clear();
        supplierItemList.addAll(providers);
        notifyDataSetChanged();
    }


}
