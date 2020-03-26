package com.example.esemenyszervezes.api;

import androidx.constraintlayout.solver.widgets.Barrier;

import com.example.esemenyszervezes.BuildConfig;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.android.material.appbar.AppBarLayout;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitBuilder {

    private static final String BASE_URL = "http://10.0.2.2/laravel/allamvizsga_backend/public/api/";
  //  private final static OkHttpClient client = buildClient();
    private static Retrofit retrofit;
    private static ApiService apiService;

    public static Retrofit getRetrofitInstance(){
        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public  static ApiService getApiService(){
        return apiService;
    }
}
