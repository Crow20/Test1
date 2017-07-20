package com.sike.courses.job.http;

/**
 * Created by agritsenko on 29.05.2017.
 */

import com.birbit.android.jobqueue.Params;
import com.sike.courses.api.response.Response;
import com.sike.courses.event.httpEvent.BaseHttpEvent;
import com.sike.courses.event.httpEvent.HttpEvent;
import com.sike.courses.mvp.model.Course;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;

public class GetDataJob extends BaseHttpJob {
    public static final int PRIORITY = 1;


    public GetDataJob() {
        super(new Params(PRIORITY));

    }

    @Override
    protected Call<ResponseBody> apiCall(String time) {
        return service.getData(time);
    }

    @Override
    protected HttpEvent getHttpEvent(Response response) {
        return new BaseHttpEvent(response, BaseHttpEvent.GET_DATA);
    }

    @Override
    protected HashMap<Object, ArrayList<Course>> getMemoryCache() {
        return null;
    }

}