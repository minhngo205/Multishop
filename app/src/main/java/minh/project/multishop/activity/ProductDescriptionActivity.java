package minh.project.multishop.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import minh.project.multishop.databinding.ActivityProductDecriptionBinding;

public class ProductDescriptionActivity extends AppCompatActivity {

    private ActivityProductDecriptionBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityProductDecriptionBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        Intent intent = getIntent();
        if(intent!=null){
            String data = intent.getStringExtra("DESCRIPTION");
            setUpData(data);
        }

        setupToolBar();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void setUpData(String data) {
        mBinding.webviewPage.loadDataWithBaseURL(
                null,
                "<style>img{display: inline;height: auto;max-width: 100%;}</style>" + data,
                "text/html",
                "UTF-8",
                null);
    }

    @SuppressLint("SetTextI18n")
    private void setupToolBar() {
        mBinding.toolbarLay.ivBack.setOnClickListener(v -> finish());
        mBinding.toolbarLay.tvTitle.setText("Chi tiết sản phẩm");
    }
}