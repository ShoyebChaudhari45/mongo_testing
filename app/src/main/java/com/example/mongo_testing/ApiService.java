package com.example.mongo_testing;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {

    @POST("login")
    Call<ApiResponse> login(@Body User user);

    @POST("signup")
    Call<ApiResponse> signup(@Body User user);
}
