package minh.project.multishop.network.dtos.Response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import minh.project.multishop.network.dtos.DTOmodels.DTOLink;
import minh.project.multishop.network.dtos.DTOmodels.DTOProduct;

public class GetListProductResponse {
    @SerializedName("links")
    public DTOLink links;
    @SerializedName("total")
    public int total;
    @SerializedName("page")
    public int page;
    @SerializedName("page_size")
    public int pageSize;
    @SerializedName("results")
    public List<DTOProduct> productList;
}
