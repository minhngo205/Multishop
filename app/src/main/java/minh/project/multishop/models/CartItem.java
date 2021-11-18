package minh.project.multishop.models;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class CartItem {
    @SerializedName("id")
    private int ID;
    @SerializedName("product")
    private Product product;
    @SerializedName("count")
    private int count;
    @SerializedName("create_at")
    private Date created_at;
    @SerializedName("updated_at")
    private Date updated_at;

    private boolean Choose;

    public int getID() {
        return ID;
    }

    public Product getProduct() {
        return product;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setChoose(boolean choose) {
        Choose = choose;
    }

    public boolean isChoose() {
        return Choose;
    }
}
