package minh.project.multishop.network.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import minh.project.multishop.network.IAppAPI;
import minh.project.multishop.network.RetroInstance;
import minh.project.multishop.network.dtos.DTORequest.CreateOrderRequest;
import minh.project.multishop.network.dtos.DTOResponse.CreateOrderResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderRepository {
    private final IAppAPI api;
    private static OrderRepository instance;
    private MutableLiveData<CreateOrderResponse> orderData;

    private OrderRepository(){
        this.api = RetroInstance.getAppAPI();
    }

    public static OrderRepository getInstance(){
        if(instance==null) instance = new OrderRepository();
        return instance;
    }

    public LiveData<CreateOrderResponse> getOrderData(String token, CreateOrderRequest request){
        orderData = new MutableLiveData<>();
        loadCreateOrder(token, request);
        return orderData;
    }

    private void loadCreateOrder(String token, CreateOrderRequest request) {
        Call<CreateOrderResponse> call = api.createOrder("Bearer "+token,request);
        call.enqueue(new Callback<CreateOrderResponse>() {
            @Override
            public void onResponse(@NonNull Call<CreateOrderResponse> call, @NonNull Response<CreateOrderResponse> response) {
                if(response.isSuccessful()){
                    orderData.postValue(response.body());
                } else orderData.postValue(null);
            }

            @Override
            public void onFailure(@NonNull Call<CreateOrderResponse> call, @NonNull Throwable t) {
                orderData.postValue(null);
            }
        });
    }
}
