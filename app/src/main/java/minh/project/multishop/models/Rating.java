package minh.project.multishop.models;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

import minh.project.multishop.network.dtos.DTOmodels.DTOComment;
import minh.project.multishop.network.dtos.DTOmodels.DTOUser;

public class Rating {
    @SerializedName("id")
    private int id;
    @SerializedName("rate")
    private int rate;
    @SerializedName("comment")
    private String comment;
    @SerializedName("created_at")
    private Date created_at;
    @SerializedName("updated_at")
    private Date updated_at;
    @SerializedName("product")
    private int productID;
    @SerializedName("user")
    private DTOUser user;
    @SerializedName("is_solved")
    private boolean isSolved;
    @SerializedName("responses")
    private List<DTOComment> responses;
}
