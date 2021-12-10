package minh.project.multishop.network.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import minh.project.multishop.models.Rating;
import minh.project.multishop.network.IAppAPI;
import minh.project.multishop.network.RetroInstance;
import minh.project.multishop.network.dtos.DTORequest.RateProductRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RatingRepository {
    private final IAppAPI api;
    private static RatingRepository instance;

    private MutableLiveData<List<Rating>> productRatings;
    private MutableLiveData<Rating> rateData;
    private MutableLiveData<List<Rating>> myRateData;

    private RatingRepository(){
        api = RetroInstance.getAppAPI();
    }

    public static RatingRepository getInstance(){
        if(null == instance) instance = new RatingRepository();
        return instance;
    }

    public LiveData<List<Rating>> getListRating(int productID){
        productRatings = new MutableLiveData<>();
        loadRatings(productID);
        return productRatings;
    }

    public LiveData<Rating> rateProduct(String token, RateProductRequest request){
        rateData = new MutableLiveData<>();
        loadRateProduct(token,request);
        return rateData;
    }

    public LiveData<List<Rating>> getMyRate(String token, int productID){
        myRateData = new MutableLiveData<>();
        loadMyRate(token, productID);
        return myRateData;
    }

    private void loadMyRate(String token, int productID) {
        Call<List<Rating>> call = api.getMyRating("Bearer "+token,productID);
        call.enqueue(new Callback<List<Rating>>() {
            @Override
            public void onResponse(@NonNull Call<List<Rating>> call, @NonNull Response<List<Rating>> response) {
                if(response.isSuccessful()){
                    myRateData.postValue(response.body());
                } else myRateData.postValue(null);
            }

            @Override
            public void onFailure(@NonNull Call<List<Rating>> call, @NonNull Throwable t) {
                myRateData.postValue(null);
            }
        });
    }

    private void loadRateProduct(String token, RateProductRequest request) {
        Call<Rating> call = api.rateProduct("Bearer "+token,request);
        call.enqueue(new Callback<Rating>() {
            @Override
            public void onResponse(@NonNull Call<Rating> call, @NonNull Response<Rating> response) {
                if(response.isSuccessful()){
                    rateData.postValue(response.body());
                } else rateData.postValue(null);
            }

            @Override
            public void onFailure(@NonNull Call<Rating> call, @NonNull Throwable t) {
                rateData.postValue(null);
            }
        });
    }

    private void loadRatings(int productID) {
        Call<List<Rating>> call = api.getProductRating(productID);
        call.enqueue(new Callback<List<Rating>>() {
            @Override
            public void onResponse(@NonNull Call<List<Rating>> call, @NonNull Response<List<Rating>> response) {
                if(response.isSuccessful()){
                    productRatings.postValue(response.body());
                } else productRatings.postValue(null);
            }

            @Override
            public void onFailure(@NonNull Call<List<Rating>> call, @NonNull Throwable t) {
                productRatings.postValue(null);
            }
        });
    }


}
