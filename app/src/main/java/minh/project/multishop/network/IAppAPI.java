package minh.project.multishop.network;

import java.util.List;

import minh.project.multishop.models.Category;
import minh.project.multishop.models.Product;
import minh.project.multishop.models.UserProfile;
import minh.project.multishop.network.dtos.DTORequest.LoginRequest;
import minh.project.multishop.network.dtos.DTOResponse.GetListProductResponse;
import minh.project.multishop.network.dtos.DTOResponse.LoginResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IAppAPI {

    //Product API
    @GET("products?page=1&page_size=20")
    Call<GetListProductResponse> getHomeListProduct();

    @GET("products/{id}")
    Call<Product> getProductByID(@Path("id") int id);

    @GET("categories")
    Call<List<Category>> getAllCategory();

    @GET("products")
    Call<GetListProductResponse> getProductByCategory(@Query("category") int cateID, @Query("page_size") int size);

    //UserAPI
    @POST("login")
    Call<LoginResponse> login(@Body LoginRequest params);

    @GET("user/me")
    Call<UserProfile> getProfile(@Header("AUTHORIZATION") String token);
}