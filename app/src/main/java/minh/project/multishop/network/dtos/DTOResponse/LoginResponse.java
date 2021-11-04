package minh.project.multishop.network.dtos.DTOResponse;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("refresh_token")
    private String refreshToken;
    @SerializedName("access_token")
    private String accessToken;


    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }
}
