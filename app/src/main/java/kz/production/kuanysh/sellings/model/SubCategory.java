package kz.production.kuanysh.sellings.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

/**
 * Created by User on 14.06.2018.
 */

public class SubCategory extends ExpandableGroup<Product> {

    private int iconResId;

    public SubCategory(String title, List<Product> items) {
        super(title, items);
    }

    public int getIconResId() {
        return iconResId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SubCategory)) return false;

        SubCategory genre = (SubCategory) o;

        return getIconResId() == genre.getIconResId();

    }

    @Override
    public int hashCode() {
        return getIconResId();
    }
}


