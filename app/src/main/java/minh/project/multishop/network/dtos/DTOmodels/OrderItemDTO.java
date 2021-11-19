package minh.project.multishop.network.dtos.DTOmodels;

import com.google.gson.annotations.SerializedName;

public class OrderItemDTO {
    @SerializedName("product")
    private int productID;
    @SerializedName("count")
    private int count;

    public OrderItemDTO(int productID, int count) {
        this.productID = productID;
        this.count = count;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
