package kz.production.kuanysh.sellings.ui.content_suppiler.fragments.goods.edit;

import kz.production.kuanysh.sellings.di.PerActivity;
import kz.production.kuanysh.sellings.ui.base.MvpPresenter;
import kz.production.kuanysh.sellings.ui.content_suppiler.fragments.statistics.StatisticsMvpView;

/**
 * Created by User on 02.07.2018.
 */
@PerActivity
public interface SupplierEditGoodsMvpPresenter <V extends SupplierEditGoodsMvpView> extends MvpPresenter<V> {

    void onViewPrepared();

    void onBackClick();

    void onAddClick(String subcategory_id,String title,String price,String count,int product_id);

    void onCategoryClick(int category_id);
}
