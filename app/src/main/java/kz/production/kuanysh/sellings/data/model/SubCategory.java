package kz.production.kuanysh.sellings.data.model;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

import kz.production.kuanysh.sellings.data.network.model.owner.superproduct.*;
import kz.production.kuanysh.sellings.data.network.model.owner.superproduct.Product;

/**
 * Created by User on 14.06.2018.
 */

public class SubCategory extends ExpandableGroup<kz.production.kuanysh.sellings.data.network.model.owner.superproduct.Product> {

    private int iconResId;

    public SubCategory(String title, List<Product> items) {
        super(title, items);
    }

    public int getIconResId() {
        return iconResId;
    }



    @Override
    public int hashCode() {
        return getIconResId();
    }
}


