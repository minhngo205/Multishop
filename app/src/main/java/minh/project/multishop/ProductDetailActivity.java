package minh.project.multishop;

import static minh.project.multishop.utils.CurrencyFormat.currencyFormat;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Toast;

import minh.project.multishop.base.BaseActivity;
import minh.project.multishop.databinding.ActivityProductDetailBinding;
import minh.project.multishop.models.Product;
import minh.project.multishop.viewmodel.ProductDetailViewModel;

public class ProductDetailActivity extends BaseActivity {

    private static final String TAG = "ProductDetailActivity";

    private ActivityProductDetailBinding productDetailBinding;
    private ProductDetailViewModel viewModel;
    private Product productDetail;
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

        Intent intent = getIntent();
        if(intent!=null){
            productID = intent.getIntExtra("productID",-1);
        }

        productDetailBinding.back.setOnClickListener(view -> finish());
        viewModel = new ProductDetailViewModel(this);

        viewModel.getProductData().observe(this, product -> {
            if(product==null){
                Toast.makeText(ProductDetailActivity.this, "No product founded", Toast.LENGTH_SHORT).show();
                return;
            }
            productDetail = product;
            viewModel.initViewPager(productDetail.getImageList());
            productDetailBinding.textPrice.setText(currencyFormat(productDetail.getProductPrice()));
            productDetailBinding.textCategory.setText(productDetail.getCategory().getName());
            productDetailBinding.textName.setText(productDetail.getProductName());
            productDetailBinding.textBrand.setText(productDetail.getBrand().getBrandName());
            productDetailBinding.textDescription.setText(Html.fromHtml(productDetail.getDescription()));
        });

    }
}