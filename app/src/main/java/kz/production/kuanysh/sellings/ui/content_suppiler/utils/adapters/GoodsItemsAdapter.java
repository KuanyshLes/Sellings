package kz.production.kuanysh.sellings.ui.content_suppiler.utils.adapters;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
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
import kz.production.kuanysh.sellings.data.model.Product;
import kz.production.kuanysh.sellings.data.network.model.supplier.subproducts.Result;
import kz.production.kuanysh.sellings.ui.content_owner.utils.Listener;
import kz.production.kuanysh.sellings.ui.content_suppiler.activity.SupplierActivity;
import kz.production.kuanysh.sellings.ui.content_suppiler.fragments.goods.edit.SupplierEditGoodsFragment;
import kz.production.kuanysh.sellings.ui.content_suppiler.fragments.goods.itemproduct.SupplierGoodsMvpView;
import kz.production.kuanysh.sellings.ui.content_suppiler.fragments.goods.itemproduct.SupplierGoodsPresenter;
import kz.production.kuanysh.sellings.utils.AppConstants;

import static kz.production.kuanysh.sellings.ui.content_suppiler.activity.SupplierActivity.SUPPLIER_VISIBLE_FRAGMENT_TAG;

/**
 * Created by User on 13.06.2018.
 */

public class GoodsItemsAdapter extends RecyclerView.Adapter<GoodsItemsAdapter.ViewHolder> {
    private List<Result> productList;
    private Context context;
    private Listener listener;
    SupplierGoodsPresenter<SupplierGoodsMvpView> mPresenter;


    public GoodsItemsAdapter(List<Result> productList) {
        this.productList = productList;
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
                .inflate(R.layout.supplier_goods_row_items, viewGroup, false);
        return new ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int i) {
        CardView cardView = viewHolder.cardView;

        TextView name = (TextView) cardView.findViewById(R.id.supplier_goods_item_name);
        final TextView amount = (TextView) cardView.findViewById(R.id.supplier_goods_item_amount);
        TextView price= (TextView) cardView.findViewById(R.id.supplier_goods_item_price);

        ImageView edit=(ImageView)cardView.findViewById(R.id.supplier_good_item_edit);
        ImageView trash=(ImageView)cardView.findViewById(R.id.supplier_good_item_delete);

        name.setText(productList.get(i).getTitle().toString());
        price.setText(productList.get(i).getPrice()+ AppConstants.MONEY_TYPE);
        amount.setText(productList.get(i).getNumberOfStock());


        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.getMvpView().openEditcategoryFragment(i);
            }
        });

        trash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.getMvpView().openDeleteDialog(productList.get(i).getId());
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
        return productList.size();
    }

    public void addItems(List<Result> results){
        productList=results;
        notifyDataSetChanged();
    }
    //fragment changer
    private void setFragment(Fragment fragment){
        ((SupplierActivity)context).getSupportFragmentManager().beginTransaction()
                .replace(R.id.supplier_content_frame, fragment, SUPPLIER_VISIBLE_FRAGMENT_TAG)
                .addToBackStack(null)
                .commit();
    }
    public void addPresenter(SupplierGoodsPresenter<SupplierGoodsMvpView> presenter){
        mPresenter=presenter;
    }


}
