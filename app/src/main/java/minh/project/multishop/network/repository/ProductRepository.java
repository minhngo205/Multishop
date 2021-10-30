package minh.project.multishop.network.repository;

import android.util.Log;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import minh.project.multishop.models.Product;
import minh.project.multishop.network.IAppAPI;
import minh.project.multishop.network.RetroInstance;
import minh.project.multishop.network.dtos.DTOmodels.DTOProduct;
import minh.project.multishop.network.dtos.Response.GetListProductResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductRepository {
    private final IAppAPI api;
    private static final String TAG = "ProductRepository";
    private List<Product> result;

    public ProductRepository() {
        api = RetroInstance.getRetroInstance().create(IAppAPI.class);
        result = new ArrayList<>();
    }

    public List<Product> getHomeProduct(){
        new Thread(() -> {
            try{
                Call<GetListProductResponse> call = api.getHomeListProduct();
                call.enqueue(new Callback<GetListProductResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<GetListProductResponse> call, @NonNull Response<GetListProductResponse> response) {
                        if(response.isSuccessful()){
                            List<DTOProduct> responseProductList;
                            if(response.body()!=null){
                                responseProductList = response.body().productList;
                                for (DTOProduct product: responseProductList ) {
                                    result.add(product.toProductEntity());
                                }
//                        Log.d(TAG, "Response size: "+result.size());
                            } else {
                                result.add(null);
                            }
                        }

                    }

                    @Override
                    public void onFailure(@NonNull Call<GetListProductResponse> call, @NonNull Throwable t) {
                        result.add(null);
                    }
                });
            } catch (Exception e){
                e.printStackTrace();
            }
        }).start();

        Log.d(TAG, "Result size: "+result.size());
        return result;
    }
}