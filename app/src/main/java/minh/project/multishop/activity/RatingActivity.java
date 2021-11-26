package minh.project.multishop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import minh.project.multishop.activity.viewmodel.RatingActViewModel;
import minh.project.multishop.base.BaseActivity;
import minh.project.multishop.databinding.ActivityRatingBinding;

public class RatingActivity extends BaseActivity {

    private ActivityRatingBinding mBinding;
    private int productID;
    private double avgRating;
    private RatingActViewModel mViewModel;
    private boolean isInit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityRatingBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        Intent intent = getIntent();
        if(null != intent){
            productID = intent.getIntExtra("PRODUCT_ID",-1);
            avgRating = intent.getDoubleExtra("AVG_RATING",-1);
        }
        Toast.makeText(this, "Product ID: "+productID, Toast.LENGTH_SHORT).show();
        mViewModel = new RatingActViewModel(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!isInit){
            mViewModel.initListRating();
            isInit = true;
        }
    }

    public int getProductID() {
        return productID;
    }

    public ActivityRatingBinding getBinding() {
        return mBinding;
    }

    public double getAvgRating() {
        return avgRating;
    }
}