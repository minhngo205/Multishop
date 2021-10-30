package minh.project.multishop.network.dtos.DTOmodels;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

import minh.project.multishop.models.Category;

public class DTOCategory {
    @SerializedName("id")
    public int cateID;
    @SerializedName("name")
    public String cateName;
    @SerializedName("thumbnail")
    public String cateThumbnail;
    @SerializedName("created_at")
    public Date createdAt;
    @SerializedName("updated_at")
    public Date updatedAt;
    @SerializedName("brands")
    public List<DTOBrand> brandList;

    public Category toCateEntity(){
        return new Category(
                cateID,
                cateName,
                brandList
        );
    }
}