package com.sike.courses.api;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import android.support.annotation.NonNull;
import com.sike.courses.BuildConfig;


/**
 * Created by agritsenko on 29.05.2017.
 */

public class ApiFactory {

    @NonNull
    public static ApiService getApiService() {
        return buildRetrofit().create(ApiService.class);
    }

    @NonNull
    private static Retrofit buildRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.API_ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }



}
