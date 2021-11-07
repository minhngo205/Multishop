package minh.project.multishop.network;

import androidx.annotation.NonNull;

import java.io.IOException;

import minh.project.multishop.utils.AuthenticationInterceptor;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroAuth {
    public static final String BASE_URL = "http://yshuynh.pythonanywhere.com/api/";

    private static Retrofit retrofit;
    private static IAppAPI AppAPI;

    public static Retrofit getRetroInstance(String token) {

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        Interceptor authInterceptor = new Interceptor() {
            @NonNull
            @Override
            public Response intercept(@NonNull Chain chain) throws IOException {
                okhttp3.Request request = chain.request();
                Headers headers = request.headers().newBuilder().add("Authorization", token).build();
                request = request.newBuilder().headers(headers).build();
                return chain.proceed(request);
            }
        };
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(authInterceptor)
                .addInterceptor(loggingInterceptor)
                .build();

        if(retrofit == null) {
            retrofit =  new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static IAppAPI getAppAPI(String token){
        if(AppAPI==null){
            AppAPI = getRetroInstance(token).create(IAppAPI.class);
        }
        return AppAPI;
    }
}


