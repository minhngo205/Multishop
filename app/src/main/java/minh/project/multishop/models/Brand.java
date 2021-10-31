package minh.project.multishop.models;

import com.google.gson.annotations.SerializedName;

public class Brand {
    @SerializedName("id")
    private int brandID;
    @SerializedName("name")
    private String brandName;

    public Brand(int brandID, String brandName) {
        this.brandID = brandID;
        this.brandName = brandName;
    }

    public int getBrandID() {
        return brandID;
    }

    public void setBrandID(int brandID) {
        this.brandID = brandID;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }
}
