package minh.project.multishop;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import minh.project.multishop.base.BaseActivity;
import minh.project.multishop.databinding.ActivityOrderSubmitBinding;
import minh.project.multishop.models.OrderItem;
import minh.project.multishop.viewmodel.OrderSubmitViewModel;

public class OrderSubmitActivity extends BaseActivity implements View.OnClickListener {

    private ActivityOrderSubmitBinding mBinding;
    private OrderSubmitViewModel mVewModel;
    private ArrayList<OrderItem> orderData;
    private List<Integer> cartIDList;
    private int productsPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityOrderSubmitBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);

        mVewModel = new OrderSubmitViewModel(this);

        Intent intent = getIntent();
        if(null!=intent){
            orderData = intent.getParcelableArrayListExtra("ORDER_DATA");
            productsPrice = intent.getIntExtra("TOTAL_PRICE",-1);
            cartIDList = intent.getIntegerArrayListExtra("LIST_CART_ID");
        }
        mVewModel.initView();
    }

    public ActivityOrderSubmitBinding getBinding() {
        return mBinding;
    }

    public ArrayList<OrderItem> getOrderData() {
        return orderData;
    }

    public int getProductsPrice() {
        return productsPrice;
    }

    @Override
    public void onClick(View view) {
        mVewModel.onClickEvent(view.getId());
    }

    public List<Integer> getCartIDList() {
        return cartIDList;
    }
}