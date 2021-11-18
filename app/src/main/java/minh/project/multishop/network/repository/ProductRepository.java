package minh.project.multishop.network.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import minh.project.multishop.models.Product;
import minh.project.multishop.network.IAppAPI;
import minh.project.multishop.network.RetroInstance;
import minh.project.multishop.network.dtos.DTOResponse.GetListProductResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductRepository {
    private final IAppAPI api;
    private static final String TAG = "ProductRepository";
    private MutableLiveData<List<Product>> allProduct;
    private static ProductRepository instance;

    ProductRepository() {
        api = RetroInstance.getAppAPI();
    }

    public static ProductRepository getInstance(){
        if(instance==null) instance = new ProductRepository();
        return instance;
    }

    public LiveData<List<Product>> getAllProduct(){
        if(allProduct==null){
            allProduct = new MutableLiveData<>();
            loadAllProduct();
        }
        return allProduct;
    }

    private void loadAllProduct() {
        Call<GetListProductResponse> call = api.getAllProduct();
        call.enqueue(new Callback<GetListProductResponse>() {
            @Override
            public void onResponse(@NonNull Call<GetListProductResponse> call, @NonNull Response<GetListProductResponse> response) {
                if(response.isSuccessful() && response.body()!=null){
                    allProduct.postValue(response.body().productList);
                } else allProduct.postValue(null);
            }

            @Override
            public void onFailure(@NonNull Call<GetListProductResponse> call, @NonNull Throwable t) {
                allProduct.postValue(null);
            }
        });
    }
}