package minh.project.multishop.utils;

import android.os.Handler;

import androidx.annotation.NonNull;

import java.util.List;

import minh.project.multishop.models.Product;
import minh.project.multishop.network.IAppAPI;
import minh.project.multishop.network.dtos.DTOmodels.DTOProduct;
import minh.project.multishop.network.dtos.Response.GetListProductResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetListProduct extends Thread{
    List<Product> productList;
    IAppAPI api;
    Handler mHandler;



    @Override
    public void run() {
        Call<GetListProductResponse> call = api.getHomeListProduct();
        call.enqueue(new Callback<GetListProductResponse>() {
            @Override
            public void onResponse(@NonNull Call<GetListProductResponse> call, @NonNull Response<GetListProductResponse> response) {
                if(response.isSuccessful()){
                    List<DTOProduct> responseProductList;
                    if(response.body()!=null){
                        responseProductList = response.body().productList;
                        mHandler.post(() -> {
                            for(DTOProduct p : responseProductList){
                                productList.add(p.toProductEntity());
                            }
                        });
                    }

                }
            }

            @Override
            public void onFailure(@NonNull Call<GetListProductResponse> call, @NonNull Throwable t) {

            }
        });
    }
}
