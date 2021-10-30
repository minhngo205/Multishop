package minh.project.multishop;

import static minh.project.multishop.utils.CurrencyFormat.currencyFormat;

import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.View;

import minh.project.multishop.base.BaseActivity;
import minh.project.multishop.databinding.ActivityProductDetailBinding;
import minh.project.multishop.models.Product;
import minh.project.multishop.utils.FetchImage;
import minh.project.multishop.viewmodel.ProductDetailViewModel;

public class ProductDetailActivity extends BaseActivity {

    private static final String TAG = "ProductDetailActivity";

    private ActivityProductDetailBinding productDetailBinding;
    private ProductDetailViewModel viewModel;
    private Product productDetail;
    private Handler mHandle;
    private int productID;

    public int getProductID() {
        return productID;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        productDetailBinding = ActivityProductDetailBinding.inflate(getLayoutInflater());
        View viewRoot = productDetailBinding.getRoot();
        setContentView(viewRoot);
        mHandle = new Handler();

        Intent intent = getIntent();
        if(intent!=null){
            productID = intent.getIntExtra("productID",-1);
        }

        productDetailBinding.back.setOnClickListener(view -> finish());
        viewModel = new ProductDetailViewModel(this);

        viewModel.getProductData().observe(this, new Observer<Product>() {
            @Override
            public void onChanged(Product product) {
                productDetail = product;
                Log.d(TAG, "onChanged: "+productDetail.getProductName());
                viewModel.initViewPager(productDetail.getImageList());
                productDetailBinding.textPrice.setText(currencyFormat(productDetail.getProductPrice()));
                productDetailBinding.textCategory.setText(productDetail.getCategory().getName());
                productDetailBinding.textName.setText(productDetail.getProductName());
                productDetailBinding.textBrand.setText(productDetail.getBrand().getBrandName());
                productDetailBinding.textDescription.setText(Html.fromHtml(productDetail.getDescription()));
            }
        });

    }
}