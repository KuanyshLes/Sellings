package kz.production.kuanysh.sellings.ui.content_suppiler.fragments.statistics;


import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kz.production.kuanysh.sellings.R;
import kz.production.kuanysh.sellings.di.component.ActivityComponent;
import kz.production.kuanysh.sellings.ui.base.BaseFragment;
import kz.production.kuanysh.sellings.ui.content_owner.fragments.basket.BasketFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class StatisticsFragment extends BaseFragment implements StatisticsMvpView{

    @Inject
    StatisticsPresenter<StatisticsMvpView> mPresenter;

    @BindView(R.id.supplier_statistics_order_amount)
    TextView orderAmount;

    @BindView(R.id.supplier_statistics_income_amount)
    TextView incomeAmount;

    @BindView(R.id.supplier_statistics_debtors_amount)
    TextView debtorsAmount;

    @BindView(R.id.supplier_statistics_toolbar_drawer)
    ImageView back;

    @BindView(R.id.supplier_statistics_order_time)
    TextView supplier_statistics_order_time;

    @BindView(R.id.supplier_statistics_income_time)
    TextView supplier_statistics_income_time;

    @BindView(R.id.supplier_statistics_debtors_time)
    TextView supplier_statistics_debtors_time;


    private static DrawerLayout drawer;


    public StatisticsFragment() {
        // Required empty public constructor
    }

    public static StatisticsFragment newInstance() {
        Bundle args = new Bundle();
        StatisticsFragment fragment = new StatisticsFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_statistics, container, false);

        drawer = (DrawerLayout) getActivity().findViewById(R.id.supplier_drawer_layout);

        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            setUnBinder(ButterKnife.bind(this, view));
            mPresenter.onAttach(this);
        }

        return view;
    }

    @OnClick(R.id.supplier_statistics_toolbar_drawer)
    public void openDrawerS(){
       mPresenter.onDrawerClick();
    }


    @Override
    public void updateStatistics(int order,int income,int debtors,String time) {
        orderAmount.setText(String.valueOf(order));
        incomeAmount.setText(String.valueOf(income));
        debtorsAmount.setText(String.valueOf(debtors));
        supplier_statistics_order_time.setText(String.valueOf(time));
        supplier_statistics_income_time.setText(String.valueOf(time));
        supplier_statistics_debtors_time.setText(String.valueOf(time));

    }

    @Override
    public void openDrawer() {
        drawer.openDrawer(GravityCompat.START);
    }

    @Override
    protected void setUp(View view) {
        mPresenter.onViewPrepared(getActivity());
    }

    @Override
    public void onDestroyView() {
        mPresenter.onDetach();
        super.onDestroyView();
    }
}
