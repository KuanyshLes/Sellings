package kz.production.kuanysh.sellings.ui.content_suppiler.fragments.order.all;

import java.util.List;

import kz.production.kuanysh.sellings.data.network.model.supplier.orders.Order;
import kz.production.kuanysh.sellings.ui.base.MvpView;

/**
 * Created by User on 02.07.2018.
 */

public interface SupplierOrdersMvpView extends MvpView {

    void openDrawer();

    void openItem(int position);

    void updateOrders(List<Order> orders,int count_page);
}
