package minh.project.multishop.viewmodel;

import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import minh.project.multishop.OrderSubmitActivity;
import minh.project.multishop.adapter.OrderItemAdapter;
import minh.project.multishop.base.BaseActivityViewModel;
import minh.project.multishop.database.entity.UserInfo;
import minh.project.multishop.database.repository.UserDBRepository;
import minh.project.multishop.databinding.ActivityOrderSubmitBinding;
import minh.project.multishop.models.OrderItem;
import minh.project.multishop.network.repository.ProductRepository;
import minh.project.multishop.utils.CurrencyFormat;

public class OrderSubmitViewModel extends BaseActivityViewModel<OrderSubmitActivity> {

    private final ActivityOrderSubmitBinding mBinding;
    private RecyclerView rvOrderItems;
    private final OrderItemAdapter adapter;
    private final UserInfo userInfo;

    private static final int SHIPPING_FEE = 30000;
    private final int TotalPrice = 0;

    /**
     * constructor
     *
     * @param orderSubmitActivity Activity object
     */
    public OrderSubmitViewModel(OrderSubmitActivity orderSubmitActivity) {
        super(orderSubmitActivity);
        mBinding = mActivity.getBinding();
        adapter = new OrderItemAdapter(mActivity);
        userInfo = UserDBRepository.getInstance().getUserInfo();
    }

    @Override
    public void initView() {
        rvOrderItems = mBinding.rvItems;
        mBinding.layoutAddress.setUserinfo(userInfo);
        mBinding.toolbarLay.tvTitle.setText("Đặt hàng");
        initRecycleView();
        initPaymentDetail();
    }

    private void initPaymentDetail() {
        mBinding.paymentDetail.setProductPrice(CurrencyFormat.currencyFormat(mActivity.getProductsPrice()));
        mBinding.paymentDetail.setShippingPrice(CurrencyFormat.currencyFormat(SHIPPING_FEE));
        mBinding.paymentDetail.setTotalPrice(CurrencyFormat.currencyFormat(mActivity.getProductsPrice()+SHIPPING_FEE));
        mBinding.tvTotal.setText(CurrencyFormat.currencyFormat(mActivity.getProductsPrice()+SHIPPING_FEE));
    }

    private void initRecycleView() {
        rvOrderItems.setLayoutManager(new LinearLayoutManager(mActivity));
        rvOrderItems.setAdapter(adapter);
        adapter.setOrderItemList(mActivity.getOrderData());
    }

    @Override
    public void onClickEvent(int viewId) {

    }
}
