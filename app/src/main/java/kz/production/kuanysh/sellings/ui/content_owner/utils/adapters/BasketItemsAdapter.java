package kz.production.kuanysh.sellings.ui.content_owner.utils.adapters;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import kz.production.kuanysh.sellings.R;
import kz.production.kuanysh.sellings.data.network.model.owner.basket.Result;
import kz.production.kuanysh.sellings.ui.content_owner.fragments.basket.BasketFragment;
import kz.production.kuanysh.sellings.ui.content_owner.fragments.basket.BasketFragmentMvpView;
import kz.production.kuanysh.sellings.ui.content_owner.fragments.basket.BasketFragmentPresenter;
import kz.production.kuanysh.sellings.ui.content_owner.main.MainActivity;
import kz.production.kuanysh.sellings.ui.content_owner.utils.Listener;
import kz.production.kuanysh.sellings.utils.AppConstants;

/**
 * Created by User on 11.06.2018.
 */

public class BasketItemsAdapter extends RecyclerView.Adapter<BasketItemsAdapter.ViewHolder> {


    BasketFragmentPresenter<BasketFragmentMvpView> mPresenter;

    private List<Result> products;
    private Listener listener;
    Context mContext;
    private int mId;


    public BasketItemsAdapter(ArrayList<Result> products) {
        this.products = products;
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
                .inflate(R.layout.basket_items_row_item, viewGroup, false);
        return new ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int i) {
        CardView cardView = viewHolder.cardView;

        TextView name = (TextView) cardView.findViewById(R.id.supplier_goods_item_name);
        final TextView amount = (TextView) cardView.findViewById(R.id.basket_item_amount);
        TextView price= (TextView) cardView.findViewById(R.id.basket_item_price);

        ImageView minus=(ImageView)cardView.findViewById(R.id.basket_item_minus_sign);
        ImageView plus=(ImageView)cardView.findViewById(R.id.supplier_good_item_edit);
        ImageView trash=(ImageView)cardView.findViewById(R.id.supplier_good_item_delete);

        name.setText(products.get(i).getTitle().toString());
        price.setText(products.get(i).getPrice()+ AppConstants.MONEY_TYPE);
        amount.setText(products.get(i).getNumberOfStock().toString());

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count=Integer.parseInt(amount.getText().toString());
                count+=1;
                products.get(i).setNumberOfStock(count);
                amount.setText(count+"");
                mPresenter.getMvpView().onAmountChangedClick(count,i);
                //mPresenter.getMvpView().onAmountChangedClick(count,i);

            }
        });
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Integer.parseInt(amount.getText().toString())>1){
                    int count=Integer.parseInt(amount.getText().toString());
                    count-=1;
                    products.get(i).setNumberOfStock(count);
                    amount.setText(count+"");

                    mPresenter.getMvpView().onAmountChangedClick(count,i);
                    //mPresenter.getMvpView().onAmountChangedClick(count,i);
                }else if(Integer.parseInt(amount.getText().toString())==1){
                    Toast.makeText(mContext, "Осталось "+amount.getText().toString()+" штук", Toast.LENGTH_SHORT).show();
                }


            }
        });
        trash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mPresenter.onDeleteClick(String.valueOf(products.get(i).getProductId()),mId);

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
        return products.size();
    }

    public void addContext(Context context){
        mContext=context;
    }
    public void addItems(List<Result> list){
        products.clear();
        products.addAll(list);
        notifyDataSetChanged();
    }

    public void addPresenter(BasketFragmentPresenter<BasketFragmentMvpView> presenter,int PROVIDER_ID){
        mPresenter=presenter;
        mId=PROVIDER_ID;
    }


}
