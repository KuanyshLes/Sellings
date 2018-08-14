package kz.production.kuanysh.sellings.ui.content_owner.fragments.order.orderDetail;

import java.util.List;

import kz.production.kuanysh.sellings.data.network.model.owner.orderdetail.Result;
import kz.production.kuanysh.sellings.ui.base.MvpView;

/**
 * Created by User on 30.06.2018.
 */

public interface OrderDetailMvpView extends MvpView {

    void openOrdersFragment();

    void updateOrderDetailInfo(Result list);

}
