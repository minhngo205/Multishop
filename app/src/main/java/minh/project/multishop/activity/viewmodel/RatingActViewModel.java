package minh.project.multishop.activity.viewmodel;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import minh.project.multishop.activity.RatingActivity;
import minh.project.multishop.adapter.RatingAdapter;
import minh.project.multishop.base.BaseActivityViewModel;
import minh.project.multishop.databinding.ActivityRatingBinding;
import minh.project.multishop.models.Rating;
import minh.project.multishop.network.repository.RatingRepository;

public class RatingActViewModel extends BaseActivityViewModel<RatingActivity> {

    private final ActivityRatingBinding mBinding;
    private final int productID;
    private final RatingRepository ratingRepository;
    private final RatingAdapter adapter;
    private List<Rating> ratingData;
    private int ratingCount;
    private int rate1count,rate2count,rate3count,rate4count,rate5count;

    /**
     * constructor
     *
     * @param ratingActivity Activity object
     */
    public RatingActViewModel(RatingActivity ratingActivity) {
        super(ratingActivity);
        mBinding = ratingActivity.getBinding();
        productID = ratingActivity.getProductID();
        ratingRepository = RatingRepository.getInstance();
        adapter = new RatingAdapter(mActivity);
        ratingData = new ArrayList<>();
    }

    @Override
    public void initView() {
        mBinding.toolbar.tvTitle.setText("Đánh giá");
        mBinding.layoutReview.rvReview.setLayoutManager(new LinearLayoutManager(mActivity));
        mBinding.layoutReview.rvReview.setAdapter(adapter);
        adapter.setRatingList(ratingData);
        disableSeekbar();
        initRatingInfo();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void disableSeekbar() {
        mBinding.layoutReview.sb1Star.setOnTouchListener((view, motionEvent) -> true);
        mBinding.layoutReview.sb2Star.setOnTouchListener((view, motionEvent) -> true);
        mBinding.layoutReview.sb3Star.setOnTouchListener((view, motionEvent) -> true);
        mBinding.layoutReview.sb4Star.setOnTouchListener((view, motionEvent) -> true);
        mBinding.layoutReview.sb5Star.setOnTouchListener((view, motionEvent) -> true);
    }

    @SuppressLint("SetTextI18n")
    private void initRatingInfo() {
        ratingCount = ratingData.size();
        mBinding.layoutReview.tvReviewRate.setText(String.valueOf(mActivity.getAvgRating()));
        mBinding.layoutReview.tvTotalReview.setText(ratingCount +" đánh giá");
        for(Rating rating : ratingData){
            switch (rating.getRate()){
                case 1:{
                    rate1count++;
                    break;
                }
                case 2:{
                    rate2count++;
                    break;
                }
                case 3:{
                    rate3count++;
                    break;
                }
                case 4:{
                    rate4count++;
                    break;
                }
                case 5:{
                    rate5count++;
                    break;
                }
                default: break;
            }
        }
        mBinding.layoutReview.sb1Star.setProgress(rate1count*100/ratingCount);
        mBinding.layoutReview.sb2Star.setProgress(rate2count*100/ratingCount);
        mBinding.layoutReview.sb3Star.setProgress(rate3count*100/ratingCount);
        mBinding.layoutReview.sb4Star.setProgress(rate4count*100/ratingCount);
        mBinding.layoutReview.sb5Star.setProgress(rate5count*100/ratingCount);
    }

    public void initListRating() {
        ratingRepository.getListRating(productID).observe(mActivity, ratings -> {
            if(null == ratings){
                Toast.makeText(mActivity, "Không thể tải thông tin đánh giá", Toast.LENGTH_SHORT).show();
                return;
            }

            updateView(ratings);
        });
    }

    private void updateView(List<Rating> ratings) {
        if(ratings.isEmpty()){
            mBinding.layoutReview.ratingView.setVisibility(View.GONE);
            mBinding.layoutReview.tvEmpty.setVisibility(View.VISIBLE);
            return;
        }
        ratingData = ratings;
        initView();
    }

    @Override
    public void onClickEvent(int viewId) {

    }
}
