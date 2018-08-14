package kz.production.kuanysh.sellings.ui.content_owner.utils.adapters;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

import kz.production.kuanysh.sellings.R;
import kz.production.kuanysh.sellings.ui.content_owner.fragments.main.subcategory.SupplierProductSubCategoryPresenter;
import kz.production.kuanysh.sellings.ui.content_owner.fragments.main.subcategory.SupplierProductSubcategoryMvpView;

/**
 * Created by User on 14.06.2018.
 */

public class SubCategoryItemViewHolder extends ChildViewHolder {

    SupplierProductSubCategoryPresenter<SupplierProductSubcategoryMvpView> mPresenter;
    private TextView name,price,amount,to_basket;
    private ImageView minus,plus;
    private int mId;
    private static int count=1;

    public SubCategoryItemViewHolder(View itemView,Context context) {
        super(itemView);
        name= (TextView) itemView.findViewById(R.id.supplier_product_subcategory_item_name);
        price= (TextView) itemView.findViewById(R.id.supplier_product_subcategory_item_price);
        amount= (TextView) itemView.findViewById(R.id.supplier_product_subcategory_item_amount);
        to_basket= (TextView) itemView.findViewById(R.id.supplier_product_subcategory_item_to_basket);

        minus=(ImageView)itemView.findViewById(R.id.supplier_product_subcategory_item_sign_minus);
        plus=(ImageView)itemView.findViewById(R.id.supplier_product_subcategory_item_sign_plus);

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count=Integer.parseInt(amount.getText().toString());
                count+=1;
                amount.setText(count+"");
            }
        });
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Integer.parseInt(amount.getText().toString())>1){
                    count=Integer.parseInt(amount.getText().toString());
                    count-=1;
                    amount.setText(count+"");
                }else if(Integer.parseInt(amount.getText().toString())==1){
                    Snackbar.make(v, "Осталось "+amount.getText().toString()+" штук", Snackbar.LENGTH_LONG).show();
                }


            }
        });

        to_basket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.onBasketAddClick(mId,count);
                //mPresenter.getMvpView().showMessage("count - "+count);

            }
        });

    }

    public void setArtistName(SupplierProductSubCategoryPresenter<SupplierProductSubcategoryMvpView> presenter,int id, String nametext, int pricetext, final Context context) {
        mId=id;
        mPresenter=presenter;
        name.setText(nametext);
        amount.setText("1");
        price.setText(pricetext+" тг");
    }
}

