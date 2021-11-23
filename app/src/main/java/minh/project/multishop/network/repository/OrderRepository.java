package minh.project.multishop.network.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import minh.project.multishop.network.IAppAPI;
import minh.project.multishop.network.RetroInstance;
import minh.project.multishop.network.dtos.DTORequest.CreateOrderRequest;
import minh.project.multishop.network.dtos.DTOResponse.OrderDetailResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderRepository {
    private final IAppAPI api;
    private static OrderRepository instance;
    private MutableLiveData<OrderDetailResponse> orderData;

    private OrderRepository(){
        this.api = RetroInstance.getAppAPI();
    }

    public static OrderRepository getInstance(){
        if(instance==null) instance = new OrderRepository();
        return instance;
    }

    public LiveData<OrderDetailResponse> getOrderData(String token, CreateOrderRequest request){
        orderData = new MutableLiveData<>();
        loadCreateOrder(token, request);
        return orderData;
    }

    public LiveData<OrderDetailResponse> getOrderDetailByID(String token, int orderID){
        orderData = new MutableLiveData<>();
        loadOrderDataByID(token, orderID);
        return orderData;
    }

    private void loadOrderDataByID(String token, int orderID) {
        Call<OrderDetailResponse> call = api.getOrderDetail("Bearer "+token,orderID);
        call.enqueue(new Callback<OrderDetailResponse>() {
            @Override
            public void onResponse(@NonNull Call<OrderDetailResponse> call, @NonNull Response<OrderDetailResponse> response) {
                if(response.isSuccessful()){
                    orderData.postValue(response.body());
                } else orderData.postValue(null);
            }

            @Override
            public void onFailure(@NonNull Call<OrderDetailResponse> call, @NonNull Throwable t) {
                orderData.postValue(null);
            }
        });
    }

    private void loadCreateOrder(String token, CreateOrderRequest request) {
        Call<OrderDetailResponse> call = api.createOrder("Bearer "+token,request);
        call.enqueue(new Callback<OrderDetailResponse>() {
            @Override
            public void onResponse(@NonNull Call<OrderDetailResponse> call, @NonNull Response<OrderDetailResponse> response) {
                if(response.isSuccessful()){
                    orderData.postValue(response.body());
                } else orderData.postValue(null);
            }

            @Override
            public void onFailure(@NonNull Call<OrderDetailResponse> call, @NonNull Throwable t) {
                orderData.postValue(null);
            }
        });
    }
}
