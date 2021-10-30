package minh.project.multishop.viewmodel;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.viewpager.widget.ViewPager;

import java.text.NumberFormat;
import java.util.Locale;

import minh.project.multishop.ProductDetailActivity;
import minh.project.multishop.R;
import minh.project.multishop.adapter.ProductViewPagerAdapter;
import minh.project.multishop.base.BaseActivityViewModel;
import minh.project.multishop.databinding.ActivityProductDetailBinding;
import minh.project.multishop.models.Product;
import minh.project.multishop.network.IAppAPI;
import minh.project.multishop.network.RetroInstance;
import minh.project.multishop.network.dtos.DTOmodels.DTOProduct;
import minh.project.multishop.utils.FetchImage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailViewModel extends BaseActivityViewModel<ProductDetailActivity> {

    private static final String TAG = "ProductDetailViewModel";
    private MutableLiveData<Product> liveProductData;
    private static final int CACHE_PAGE_COUNT = 3;
    private ProductViewPagerAdapter adapter;


    public LiveData<Product> getProductData(){
        if(liveProductData==null){
            liveProductData = new MutableLiveData<>();
            loadProductData(mActivity.getProductID());
        }
        return liveProductData;
    }

    private void loadProductData(int proId) {
        IAppAPI api = RetroInstance.getRetroInstance().create(IAppAPI.class);
        Call<DTOProduct> call = api.getProductByID(proId);
        call.enqueue(new Callback<DTOProduct>() {
            @Override
            public void onResponse(@NonNull Call<DTOProduct> call, @NonNull Response<DTOProduct> response) {
                if(response.isSuccessful()&&response.body()!=null){
                    liveProductData.postValue(response.body().toProductEntity());
                } else {
                    liveProductData.postValue(null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<DTOProduct> call, @NonNull Throwable t) {
                liveProductData.postValue(null);
            }
        });
    }

    /**
     * constructor
     *
     * @param productDetailActivity Activity object
     */
    public ProductDetailViewModel(ProductDetailActivity productDetailActivity) {
        super(productDetailActivity);
    }

    @Override
    public void initView() {

    }

    @Override
    public void onClickEvent(int viewId) {
    }

    public String currencyFormat(int price){
        Locale locale = new Locale("vn", "VN");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
        return currencyFormatter.format(price);
    }

    public void initViewPager(String[] linkIMG) {
        ViewPager viewPager = mActivity.findViewById(R.id.view_pager_product);
        adapter = new ProductViewPagerAdapter(linkIMG, mActivity);

        TextView tvTip = mActivity.findViewById(R.id.tv_count);
        int total = adapter.getCount();
        tvTip.setText(mActivity.getString(R.string.current_position, 1, total));

        viewPager.setOffscreenPageLimit(CACHE_PAGE_COUNT);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {

                tvTip.setText(mActivity.getString(R.string.current_position, position + 1, total));
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        viewPager.setAdapter(adapter);
    }
}
