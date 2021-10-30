package minh.project.multishop.network.dtos.DTOmodels;

import com.google.gson.annotations.SerializedName;

import minh.project.multishop.models.Brand;

public class DTOBrand {
    @SerializedName("id")
    public int brandID;
    @SerializedName("name")
    public String brandName;

    public Brand toBrandEntity(){
        return new Brand(brandID,brandName);
    }
}
