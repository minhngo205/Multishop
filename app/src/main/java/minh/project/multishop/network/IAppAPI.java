package minh.project.multishop.network;

import java.util.List;

import minh.project.multishop.models.CartItem;
import minh.project.multishop.models.Category;
import minh.project.multishop.models.Product;
import minh.project.multishop.models.UserProfile;
import minh.project.multishop.network.dtos.DTORequest.CreateOrderRequest;
import minh.project.multishop.network.dtos.DTOResponse.OrderDetailResponse;
import minh.project.multishop.network.dtos.DTOResponse.EditCartResponse;
import minh.project.multishop.network.dtos.DTORequest.LoginRequest;
import minh.project.multishop.network.dtos.DTORequest.RefreshAccessTokenRequest;
import minh.project.multishop.network.dtos.DTORequest.EditCartRequest;
import minh.project.multishop.network.dtos.DTOResponse.GetListProductResponse;
import minh.project.multishop.network.dtos.DTOResponse.LoginResponse;
import minh.project.multishop.network.dtos.DTOResponse.RefreshAccessTokenResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IAppAPI {

    //Product API
    @GET("products?page=1&page_size=220")
    Call<GetListProductResponse> getAllProduct();

    @GET("products?page=1&page_size=20")
    Call<GetListProductResponse> getHomeListProduct();

    @GET("products/{id}")
    Call<Product> getProductByID(@Path("id") int id);

    @GET("categories")
    Call<List<Category>> getAllCategory();

    @GET("products")
    Call<GetListProductResponse> getProductByCategory(@Query("category") int cateID, @Query("page_size") int size);

    //UserAPI
    @POST("refresh")
    Call<RefreshAccessTokenResponse> refreshToken(@Body RefreshAccessTokenRequest param);

    @POST("login")
    Call<LoginResponse> login(@Body LoginRequest params);

    @GET("user/me")
    Call<UserProfile> getProfile(@Header("Authorization") String value);

    //Cart API
    @GET("user/carts")
    Call<List<CartItem>> getCartList(@Header("Authorization") String value);

    @PUT("user/carts/add")
    Call<EditCartResponse> addProductToCart(@Header("Authorization") String value, @Body EditCartRequest params);

    @PUT("user/carts/remove")
    Call<EditCartResponse> removeProductFromCart(@Header("Authorization") String value, @Body EditCartRequest params);

    @DELETE("user/carts/{cartID}")
    Call<String> deleteCartItem(@Header("Authorization") String value, @Path("cartID") int cartID);

    //Order API
    @POST("user/orders")
    Call<OrderDetailResponse> createOrder(@Header("Authorization") String value, @Body CreateOrderRequest params);

    @GET("user/orders/{orderID}")
    Call<OrderDetailResponse> getOrderDetail(@Header("Authorization") String value, @Path("orderID") int orderID);
}