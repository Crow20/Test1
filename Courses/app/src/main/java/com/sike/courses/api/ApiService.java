package com.sike.courses.api;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


/**
 * Created by agritsenko on 29.05.2017.
 */
public interface ApiService {
    @GET("indicators")
    Call <ResponseBody> getData(@Query("_") String format);
}
