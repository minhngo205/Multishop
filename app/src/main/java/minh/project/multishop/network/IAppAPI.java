package minh.project.multishop.network;

import java.util.List;

import minh.project.multishop.network.dtos.DTOmodels.DTOCategory;
import minh.project.multishop.network.dtos.DTOmodels.DTOProduct;
import minh.project.multishop.network.dtos.Response.GetListProductResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IAppAPI {

    @GET("products?page=1&page_size=20")
    Call<GetListProductResponse> getHomeListProduct();

    @GET("products/{id}")
    Call<DTOProduct> getProductByID(@Path("id") int id);

    @GET("categories")
    Call<List<DTOCategory>> getAllCategory();

    @GET("products")
    Call<GetListProductResponse> getProductByCategory(@Query("category") int cateID, @Query("page_size") int size);
}