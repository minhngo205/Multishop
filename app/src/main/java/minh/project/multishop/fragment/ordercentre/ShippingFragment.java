package minh.project.multishop.fragment.ordercentre;

import static minh.project.multishop.utils.Statistics.SHIPPING_ORDER;
import static minh.project.multishop.utils.Statistics.WAITING_CONFIRM;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import minh.project.multishop.R;
import minh.project.multishop.activity.OrderCentreActivity;
import minh.project.multishop.adapter.OrderCenterListAdapter;
import minh.project.multishop.database.entity.User;
import minh.project.multishop.databinding.FragmentShippingBinding;
import minh.project.multishop.network.repository.OrderRepository;
import minh.project.multishop.utils.CustomProgress;

public class ShippingFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

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
        dialog.showProgress(getContext(),"Đang tải...",true);
        OrderRepository.getInstance().getListOrderByStatus(mUser.getAccToken(),SHIPPING_ORDER).observe(mActivity, getListOrderResponse -> {
            if(null == getListOrderResponse){
                Toast.makeText(getActivity(), "Không có gì cả", Toast.LENGTH_SHORT).show();
                return;
            }

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