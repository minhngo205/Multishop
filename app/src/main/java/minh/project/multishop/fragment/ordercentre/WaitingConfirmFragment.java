package minh.project.multishop.fragment.ordercentre;

import static minh.project.multishop.utils.Statistics.WAITING_CONFIRM;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;

import minh.project.multishop.activity.OrderCentreActivity;
import minh.project.multishop.adapter.OrderCenterListAdapter;
import minh.project.multishop.database.entity.User;
import minh.project.multishop.databinding.FragmentWaitingConfirmBinding;
import minh.project.multishop.network.repository.OrderRepository;
import minh.project.multishop.utils.CustomProgress;

public class WaitingConfirmFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private FragmentWaitingConfirmBinding mBinding;
    private OrderCenterListAdapter mAdapter;

    private final User mUser;
    private final OrderCentreActivity mActivity;

    public WaitingConfirmFragment(OrderCentreActivity orderCentreActivity, User user) {
        this.mUser = user;
        this.mActivity = orderCentreActivity;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = FragmentWaitingConfirmBinding.inflate(inflater,container,false);
        initRecycleView();
        loadOrder();
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBinding.layoutWaitingConfirm.setOnRefreshListener(this);
        mBinding.layoutWaitingConfirm.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    private void initRecycleView() {
        mBinding.waitingOrders.setLayoutManager(new LinearLayoutManager(getContext()));
        mBinding.waitingOrders.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new OrderCenterListAdapter(mActivity);
        mBinding.waitingOrders.setAdapter(mAdapter);
    }

    private void loadOrder() {
        CustomProgress dialog = CustomProgress.getInstance();
        dialog.showProgress(getContext(),"Đang tải...",true);
        OrderRepository.getInstance().getListOrderByStatus(mUser.getAccToken(),WAITING_CONFIRM).observe(mActivity, getListOrderResponse -> {
            if(null == getListOrderResponse){
                Toast.makeText(getActivity(), "Không có gì cả", Toast.LENGTH_SHORT).show();
                return;
            }

            mAdapter.setListOrder(getListOrderResponse.results);
            dialog.hideProgress();
            mBinding.layoutWaitingConfirm.setRefreshing(false);
        });
    }

    @Override
    public void onRefresh() {
        mAdapter.setListOrder(new ArrayList<>());
        loadOrder();
    }
}