package kz.production.kuanysh.sellings.ui.content_suppiler.fragments.goods.edit;

import java.util.List;

import kz.production.kuanysh.sellings.data.network.model.owner.category.Result;
import kz.production.kuanysh.sellings.ui.base.MvpView;

/**
 * Created by User on 02.07.2018.
 */

public interface SupplierEditGoodsMvpView extends MvpView {

    void updateCategory(List<Result> result);


    void updateSubCategory(List<kz.production.kuanysh.sellings.data.network.model.supplier.subcategory.get.Result> subList);


    void openSubcategoryFragments();
}
