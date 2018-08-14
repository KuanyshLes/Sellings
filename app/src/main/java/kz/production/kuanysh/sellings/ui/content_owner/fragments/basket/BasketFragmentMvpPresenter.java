package kz.production.kuanysh.sellings.ui.content_owner.fragments.basket;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import kz.production.kuanysh.sellings.data.network.model.owner.basket.Result;
import kz.production.kuanysh.sellings.di.PerActivity;
import kz.production.kuanysh.sellings.ui.base.MvpPresenter;
import kz.production.kuanysh.sellings.ui.content_owner.fragments.main.main.OwnerSupplierItemMvpView;

/**
 * Created by User on 25.06.2018.
 */


@PerActivity
public interface BasketFragmentMvpPresenter<V extends BasketFragmentMvpView> extends MvpPresenter<V> {

    void onSendClick(int provider_id,int consignment,int payment_type_id,int amount,
                     List<Result> list);

    void onSendClickWithDate(int provider_id,int consignment,int payment_type_id,int amount,List<Result> list,String dateA);

    void onViewPrepared(int id);

    void onDeleteClick(String product_id,int id);

}
