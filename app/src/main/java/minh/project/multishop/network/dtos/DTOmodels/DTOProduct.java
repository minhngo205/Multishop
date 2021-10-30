package minh.project.multishop.network.dtos.DTOmodels;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

import minh.project.multishop.models.Product;
import minh.project.multishop.models.ProductSpecs;
import minh.project.multishop.models.Rating;

public class DTOProduct {
    @SerializedName("id")
    public int productID;

    @SerializedName("name")
    public String productName;

    @SerializedName("thumbnail")
    public String imgThumbnail;

    @SerializedName("brand")
    public DTOBrand brand;

    @SerializedName("created_at")
    public Date createdAt;

    @SerializedName("updated_at")
    public Date updatedAt;

    @SerializedName("category")
    public DTOCategory category;

    @SerializedName("specifications")
    public List<ProductSpecs> specifications;

    @SerializedName("short_description")
    public String descriptionShort;

    @SerializedName("description")
    public String description;

    @SerializedName("price")
    public int productPrice;

    @SerializedName("sale_price")
    public int salePrice;

    @SerializedName("discount")
    public int isDiscount;

    @SerializedName("avg_rating")
    public double avgRating;

    @SerializedName("ratings")
    public List<Rating> listRatings;

    @SerializedName("images")
    public DTOImage[] listImages;

    public Product toProductEntity(){
        String[] lisUrl = new String[0];
        if(listImages!=null){
            lisUrl  = new String[listImages.length];
            for(int i=0;i<listImages.length;i++){
                lisUrl[i] = listImages[i].url;
            }
        }
        return new Product(
                productID,
                productName,
                imgThumbnail,
                brand.toBrandEntity(),
                category.toCateEntity(),
                specifications,
                descriptionShort,
                description,
                productPrice,
                salePrice,
                isDiscount,
                avgRating,
                listRatings,
                lisUrl
        );
    }
}