package minh.project.multishop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import minh.project.multishop.base.BaseActivity;
import minh.project.multishop.databinding.ActivityOrderSubmitBinding;
import minh.project.multishop.models.OrderItem;
import minh.project.multishop.models.Product;
import minh.project.multishop.viewmodel.OrderSubmitViewModel;

public class OrderSubmitActivity extends BaseActivity {

    private ActivityOrderSubmitBinding mBinding;
    private OrderSubmitViewModel mVewModel;
    private ArrayList<OrderItem> orderData;
    private int productsPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityOrderSubmitBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);

        mVewModel = new OrderSubmitViewModel(this);

        Intent intent = getIntent();
        orderData = intent.getParcelableArrayListExtra("ORDER_DATA");
        productsPrice = intent.getIntExtra("TOTAL_PRICE",-1);
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
}