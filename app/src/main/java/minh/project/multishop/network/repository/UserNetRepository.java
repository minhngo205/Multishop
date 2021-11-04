package minh.project.multishop.network.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import minh.project.multishop.models.UserProfile;
import minh.project.multishop.network.IAppAPI;
import minh.project.multishop.network.RetroAuth;
import minh.project.multishop.network.RetroInstance;
import minh.project.multishop.network.dtos.DTORequest.LoginRequest;
import minh.project.multishop.network.dtos.DTOResponse.LoginResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserNetRepository {

    private final IAppAPI api;
    private MutableLiveData<LoginResponse> loginData;
    private MutableLiveData<UserProfile> profileData;

    private static UserNetRepository instance;

    public UserNetRepository() {
        this.api = RetroInstance.getAppAPI();
    }

    public static UserNetRepository getInstance(){
        if(instance==null){
            instance = new UserNetRepository();
        }
        return instance;
    }

    public LiveData<LoginResponse> getLoginData(LoginRequest loginRequest){
        loginData = new MutableLiveData<>();
        login(loginRequest);
        return loginData;
    }

    public LiveData<UserProfile> getUserProfile(String accessToken){
        profileData = new MutableLiveData<>();
        loadProfileData(accessToken);
        return profileData;
    }

    private void loadProfileData(String accessToken) {
//        IAppAPI authAPI = RetroAuth.getAppAPI(accessToken);
        Call<UserProfile> call = api.getProfile(accessToken);
        call.enqueue(new Callback<UserProfile>() {
            @Override
            public void onResponse(@NonNull Call<UserProfile> call, @NonNull Response<UserProfile> response) {
                if(response.isSuccessful()&&response.body()!=null){
                    profileData.postValue(response.body());
                } else profileData.postValue(null);
            }

            @Override
            public void onFailure(@NonNull Call<UserProfile> call, @NonNull Throwable t) {
                profileData.postValue(null);
            }
        });
    }

    private void login(LoginRequest loginRequest) {
        Call<LoginResponse> call = api.login(loginRequest);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                if(response.isSuccessful()&&response.body()!=null){
                    loginData.postValue(response.body());
                } else loginData.postValue(null);
            }

            @Override
            public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {
                loginData.postValue(null);
            }
        });
    }
}
