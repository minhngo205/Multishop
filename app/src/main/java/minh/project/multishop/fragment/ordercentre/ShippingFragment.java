package minh.project.multishop.fragment.ordercentre;

import static minh.project.multishop.utils.Statistics.SHIPPING_ORDER;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;

import minh.project.multishop.activity.OrderCentreActivity;
import minh.project.multishop.adapter.OrderCenterListAdapter;
import minh.project.multishop.base.BaseFragment;
import minh.project.multishop.database.entity.User;
import minh.project.multishop.databinding.FragmentShippingBinding;
import minh.project.multishop.network.repository.OrderRepository;
import minh.project.multishop.utils.CustomProgress;

public class ShippingFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    private FragmentShippingBinding mBinding;
    private OrderCenterListAdapter mAdapter;

    private final User mUser;
    private final OrderCentreActivity mActivity;

    public ShippingFragment(OrderCentreActivity orderCentreActivity, User user) {
        this.mActivity = orderCentreActivity;
        this.mUser = user;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = FragmentShippingBinding.inflate(inflater,container,false);
        initRecycleView();
        loadOrder();
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBinding.layoutRefreshShipping.setOnRefreshListener(this);
        mBinding.layoutRefreshShipping.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    private void initRecycleView() {
        mBinding.shippingOrders.setLayoutManager(new LinearLayoutManager(mActivity));
        mBinding.shippingOrders.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new OrderCenterListAdapter(mActivity);
        mBinding.shippingOrders.setAdapter(mAdapter);
    }

    private void loadOrder() {
        CustomProgress dialog = CustomProgress.getInstance();
        dialog.showProgress(getContext(),"Đang tải...",false);
        OrderRepository.getInstance().getListOrderByStatus(mUser.getAccToken(),SHIPPING_ORDER).observe(mActivity, getListOrderResponse -> {
            if(null == getListOrderResponse || getListOrderResponse.results.isEmpty()){
                mBinding.noItem.setVisibility(View.VISIBLE);
                dialog.hideProgress();
                mBinding.layoutRefreshShipping.setRefreshing(false);
                return;
            }

            mBinding.noItem.setVisibility(View.GONE);
            mAdapter.setListOrder(getListOrderResponse.results);
            dialog.hideProgress();
            mBinding.layoutRefreshShipping.setRefreshing(false);
        });
    }

    @Override
    public void onRefresh() {
        mAdapter.setListOrder(new ArrayList<>());
        loadOrder();
    }
}