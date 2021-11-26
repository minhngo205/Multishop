package minh.project.multishop.fragment.ordercentre;

import static minh.project.multishop.utils.Statistics.CONFIRMED_ORDER;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import minh.project.multishop.activity.OrderCentreActivity;
import minh.project.multishop.adapter.OrderCenterListAdapter;
import minh.project.multishop.database.entity.User;
import minh.project.multishop.databinding.FragmentConfirmedBinding;
import minh.project.multishop.network.repository.OrderRepository;

public class ConfirmedFragment extends Fragment {

    private FragmentConfirmedBinding mBinding;
    private OrderCenterListAdapter mAdapter;

    private final User mUser;
    private final OrderCentreActivity mActivity;

    public ConfirmedFragment(OrderCentreActivity orderCentreActivity, User user) {
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
        mBinding = FragmentConfirmedBinding.inflate(inflater,container,false);
        initRecycleView();
        loadOrder();
        return mBinding.getRoot();
    }

    private void initRecycleView() {
        mBinding.confirmedOrders.setLayoutManager(new LinearLayoutManager(getContext()));
        mBinding.confirmedOrders.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new OrderCenterListAdapter(mActivity);
        mBinding.confirmedOrders.setAdapter(mAdapter);
    }

    private void loadOrder() {
        OrderRepository.getInstance().getListOrderByStatus(mUser.getAccToken(),CONFIRMED_ORDER).observe(mActivity, getListOrderResponse -> {
            if(null == getListOrderResponse){
                Toast.makeText(getActivity(), "Không có gì cả", Toast.LENGTH_SHORT).show();
                return;
            }

            mAdapter.setListOrder(getListOrderResponse.results);
        });
    }
}