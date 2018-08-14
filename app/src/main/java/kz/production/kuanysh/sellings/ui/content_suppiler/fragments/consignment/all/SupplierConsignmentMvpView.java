package kz.production.kuanysh.sellings.ui.content_suppiler.fragments.consignment.all;

import java.util.List;

import kz.production.kuanysh.sellings.data.network.model.supplier.consignment.Result;
import kz.production.kuanysh.sellings.ui.base.MvpView;

/**
 * Created by User on 02.07.2018.
 */

public interface SupplierConsignmentMvpView extends MvpView {

    void openDrawer();

    void openItem(int position);

    void updateConsignmentList(List<Result> resultList);
}
