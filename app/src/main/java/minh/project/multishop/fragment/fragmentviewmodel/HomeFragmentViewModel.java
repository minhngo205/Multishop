package minh.project.multishop.fragment.fragmentviewmodel;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.GridLayoutManager;

import java.util.ArrayList;
import java.util.List;

import minh.project.multishop.R;
import minh.project.multishop.adapter.HomeProductAdapter;
import minh.project.multishop.base.BaseFragmentViewModel;
import minh.project.multishop.databinding.FragmentHomeBinding;
import minh.project.multishop.fragment.HomeFragment;
import minh.project.multishop.models.Product;
import minh.project.multishop.network.IAppAPI;
import minh.project.multishop.network.RetroInstance;
import minh.project.multishop.network.dtos.DTOmodels.DTOProduct;
import minh.project.multishop.network.dtos.Response.GetListProductResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragmentViewModel extends BaseFragmentViewModel<HomeFragment> {

    private static final String TAG = "HomeFragmentViewModel";
    private MutableLiveData<List<Product>> liveData;
    private FragmentHomeBinding homeBinding;
    private HomeProductAdapter adapter;

    /**
     * constructor
     *
     * @param homeFragment Fragment object
     */
    public HomeFragmentViewModel(HomeFragment homeFragment) {
        super(homeFragment);
    }

    public LiveData<List<Product>> getListProduct(){
        if(liveData==null){
            liveData = new MutableLiveData<>();
            loadProductData();
        }
        return liveData;
    }

    private void loadProductData() {
        IAppAPI api = RetroInstance.getRetroInstance().create(IAppAPI.class);
        Call<GetListProductResponse> call = api.getHomeListProduct();
        call.enqueue(new Callback<GetListProductResponse>() {
            @Override
            public void onResponse(@NonNull Call<GetListProductResponse> call, @NonNull Response<GetListProductResponse> response) {
                if(response.isSuccessful()){
                    List<DTOProduct> responseProductList;
                    List<Product> result = new ArrayList<>();
                    if(response.body()!=null){
                        responseProductList = response.body().productList;
                        for (DTOProduct product: responseProductList ) {
                            result.add(product.toProductEntity());
                        }
                        liveData.postValue(result);
//                        Log.d(TAG, "Response size: "+result.size());
                    } else {
                        liveData.postValue(null);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetListProductResponse> call, @NonNull Throwable t) {
                liveData.postValue(null);
            }
        });
    }

    @Override
    public void initView(View view) {
        homeBinding = mFragment.getHomeBinding();
    }

    public void initHomeProductRecyclerView() {
        GridLayoutManager layoutManager = new GridLayoutManager(mFragment.getActivity(), 2);
        homeBinding.recyclerRecommendation.setLayoutManager(layoutManager);

        getListProduct().observe(mFragment.getViewLifecycleOwner(), products -> {
            if(products==null){
                Toast.makeText(mFragment.getContext(), "Could not get product Data", Toast.LENGTH_SHORT).show();
                return;
            }
            adapter = new HomeProductAdapter(products,mFragment.getContext());
            homeBinding.recyclerRecommendation.setAdapter(adapter);
            homeBinding.recyclerRecommendation.setNestedScrollingEnabled(false);
            homeBinding.recyclerRecommendation.scheduleLayoutAnimation();
        });
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClickEvent(int viewId) {
        switch (viewId) {
            case R.id.card_new:

            case R.id.card_article1:

            case R.id.card_article2:

            default:
                break;
        }
    }
}