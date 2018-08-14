package kz.production.kuanysh.sellings.ui.content_owner.utils.adapters;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.thoughtbot.expandablerecyclerview.MultiTypeExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.models.ExpandableListPosition;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

import java.util.List;

import kz.production.kuanysh.sellings.R;
import kz.production.kuanysh.sellings.data.model.Product;
import kz.production.kuanysh.sellings.data.model.SubCategory;
import kz.production.kuanysh.sellings.ui.content_owner.fragments.main.subcategory.SupplierProductSubCategoryPresenter;
import kz.production.kuanysh.sellings.ui.content_owner.fragments.main.subcategory.SupplierProductSubcategoryMvpView;

import static android.view.LayoutInflater.from;

/**
 * Created by User on 14.06.2018.
 */
public class MultiTypeCategoryAdapter
        extends MultiTypeExpandableRecyclerViewAdapter<SubCategoryViewHolder, ChildViewHolder> {

    public static final int SUBCATEGORY_VIEW_TYPE = 4;
    private Context mContext;
    private List<SubCategory> mList;
    SupplierProductSubCategoryPresenter<SupplierProductSubcategoryMvpView> mPresenter;

    public MultiTypeCategoryAdapter(List<SubCategory> groups,Context context) {
        super(groups);
        mContext=context;
    }

    @Override
    public SubCategoryViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        View view = from(parent.getContext())
                .inflate(R.layout.supplier_product_subcategery_row_item, parent, false);
        return new SubCategoryViewHolder(view);
    }

    @Override
    public ChildViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case SUBCATEGORY_VIEW_TYPE :
                View artist = from(parent.getContext())
                        .inflate(R.layout.supplier_product_subcategory_items_row_item, parent, false);
                return new SubCategoryItemViewHolder(artist,mContext);
            default:
                throw new IllegalArgumentException("Invalid viewType");
        }
    }

    @Override
    public void onBindChildViewHolder(ChildViewHolder holder, int flatPosition, ExpandableGroup group,
                                      int childIndex) {
        int viewType = getItemViewType(flatPosition);
        kz.production.kuanysh.sellings.data.network.model.owner.superproduct.Product artist =
                ((SubCategory) group).getItems().get(childIndex);
        switch (viewType) {
            case SUBCATEGORY_VIEW_TYPE :
                ((SubCategoryItemViewHolder) holder).
                        setArtistName(mPresenter,
                                artist.getProductId(),
                                artist.getTitle(),
                                Integer.valueOf(artist.getPrice()),
                                mContext);
                break;
        }
    }

    @Override
    public void onBindGroupViewHolder(SubCategoryViewHolder holder, int flatPosition,
                                      ExpandableGroup group) {
        holder.setGenreTitle(group);
    }

    @Override
    public int getChildViewType(int position, ExpandableGroup group, int childIndex) {
        return SUBCATEGORY_VIEW_TYPE ;
    }

    @Override
    public boolean isGroup(int viewType) {
        return viewType == ExpandableListPosition.GROUP;
    }

    @Override
    public boolean isChild(int viewType) {
        return viewType == SUBCATEGORY_VIEW_TYPE ;
    }

    public void addItems(List<SubCategory> list){
        mList=list;
        notifyDataSetChanged();
        Log.d("myTag", "addItems: size  "+list.size());
    }

    public void addPresenter(SupplierProductSubCategoryPresenter<SupplierProductSubcategoryMvpView> presenter){
        mPresenter=presenter;
    }


}

