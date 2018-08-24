package kz.production.kuanysh.sellings.ui.content_suppiler.utils.adapters;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import kz.production.kuanysh.sellings.R;
import kz.production.kuanysh.sellings.data.network.model.owner.category.Result;
import kz.production.kuanysh.sellings.ui.content_owner.utils.Listener;
import kz.production.kuanysh.sellings.ui.content_suppiler.fragments.goods.category.SupplierCategoryFragment;
import kz.production.kuanysh.sellings.ui.content_suppiler.fragments.goods.category.SupplierCategoryMvpView;
import kz.production.kuanysh.sellings.ui.content_suppiler.fragments.goods.category.SupplierCategoryPresenter;

/**
 * Created by User on 18.06.2018.
 */

public class SupplierCategoryAdapter extends RecyclerView.Adapter<SupplierCategoryAdapter.ViewHolder> {


    SupplierCategoryPresenter<SupplierCategoryMvpView> mPresenter;

    private List<Result> categoryItemList;
    private Listener listener;
    private Context context;




    public SupplierCategoryAdapter(List<Result> categoryItemList) {
        this.categoryItemList=categoryItemList;

    }

    class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;

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
                .inflate(R.layout.supplier_category_row_item, viewGroup, false);
        return new ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(SupplierCategoryAdapter.ViewHolder viewHolder, final int i) {

        CardView cardView = viewHolder.cardView;

        TextView name=(TextView)cardView.findViewById(R.id.supplier_category_name);
        ImageView edit=(ImageView)cardView.findViewById(R.id.supplier_category_edit);
        ImageView delete=(ImageView)cardView.findViewById(R.id.supplier_category_delete);

        name.setText(categoryItemList.get(i).getTitle().toString());
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.getMvpView().openDeleteDialog(i);
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               mPresenter.getMvpView().openDialog(i,2);
            }
        });


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
        return categoryItemList.size();
    }

    public void addItems(List<Result> items){
        categoryItemList.clear();
        categoryItemList.addAll(items);

        Log.d("myTag", "updateCategory: adapter size "+categoryItemList.size());
        notifyDataSetChanged();
    }
    public void addContext(Context mcontext){
        context=mcontext;
    }

    public void addPresenter(SupplierCategoryPresenter<SupplierCategoryMvpView> presenter){
        mPresenter=presenter;
    }

}
