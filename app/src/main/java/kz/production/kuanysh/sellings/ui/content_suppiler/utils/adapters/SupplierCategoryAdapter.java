package kz.production.kuanysh.sellings.ui.content_suppiler.utils.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.concurrent.TimeUnit;

import kz.production.kuanysh.sellings.R;
import kz.production.kuanysh.sellings.model.OwnerItem;
import kz.production.kuanysh.sellings.ui.content_owner.utils.Colors;
import kz.production.kuanysh.sellings.ui.content_owner.utils.Listener;

/**
 * Created by User on 18.06.2018.
 */

public class SupplierCategoryAdapter extends RecyclerView.Adapter<SupplierCategoryAdapter.ViewHolder> {
    private List<String> categoryItemList;
    private Context context;
    private Listener listener;



    public SupplierCategoryAdapter(List<String> categoryItemList,Context context) {
        this.categoryItemList=categoryItemList;
        this.context = context;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private ImageView edit,delete;
        public ViewHolder(View itemView) {
            super(itemView);

            name=(TextView)itemView.findViewById(R.id.supplier_category_name);
            edit=(ImageView)itemView.findViewById(R.id.supplier_category_edit);
            delete=(ImageView)itemView.findViewById(R.id.supplier_category_delete);

        }
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, final int i) {
        ConstraintLayout cv = (ConstraintLayout) LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.supplier_category_row_item, viewGroup, false);
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
    public void onBindViewHolder(SupplierCategoryAdapter.ViewHolder viewHolder, final int i) {

        viewHolder.name.setText(categoryItemList.get(i).toString());
        viewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryItemList.remove(i);
                try {
                    TimeUnit.MILLISECONDS.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Snackbar.make(v, "Успешно удалено!", Snackbar.LENGTH_LONG).show();
                notifyDataSetChanged();
            }
        });

        ConstraintLayout cv = (ConstraintLayout) LayoutInflater.from(context)
                .inflate(R.layout.supplier_category_row_item, null, false);
        cv.setOnClickListener(new View.OnClickListener() {
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
        return categoryItemList.size();
    }


}
