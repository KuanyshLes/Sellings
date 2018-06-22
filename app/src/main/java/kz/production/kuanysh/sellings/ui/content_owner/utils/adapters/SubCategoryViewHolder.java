package kz.production.kuanysh.sellings.ui.content_owner.utils.adapters;

import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

import kz.production.kuanysh.sellings.R;
import kz.production.kuanysh.sellings.model.SubCategory;

import static android.view.animation.Animation.RELATIVE_TO_SELF;

/**
 * Created by User on 14.06.2018.
 */

public class SubCategoryViewHolder extends GroupViewHolder {

    private TextView SubCategoryName;
    private ImageView arrow;

    public SubCategoryViewHolder(View itemView) {
        super(itemView);
        SubCategoryName = (TextView) itemView.findViewById(R.id.supplier_product_subcategory_name);
        arrow = (ImageView) itemView.findViewById(R.id.supplier_product_subcategory_next);
        }

    public void setGenreTitle(ExpandableGroup subcategory) {
        if (subcategory instanceof SubCategory) {
            SubCategoryName.setText(subcategory.getTitle());
            }

    }

    @Override
    public void expand() {
        animateExpand();
    }

    @Override
    public void collapse() {
        animateCollapse();
    }

    private void animateExpand() {
        RotateAnimation rotate =
                new RotateAnimation(360, 90, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(300);
        rotate.setFillAfter(true);
        arrow.setAnimation(rotate);
    }

    private void animateCollapse() {
        RotateAnimation rotate =
                new RotateAnimation(90, 360, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(300);
        rotate.setFillAfter(true);
        arrow.setAnimation(rotate);
    }
}


