package com.sike.courses.job.http;

/**
 * Created by agritsenko on 29.05.2017.
 */

import com.birbit.android.jobqueue.Params;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sike.courses.api.ApiFactory;
import com.sike.courses.api.ApiService;
import com.sike.courses.api.response.RequestResult;
import com.sike.courses.api.response.Response;
import com.sike.courses.event.httpEvent.HttpEvent;
import com.sike.courses.job.BaseJob;
import com.sike.courses.mvp.model.Course;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.concurrent.atomic.AtomicInteger;

import okhttp3.ResponseBody;
import retrofit2.Call;



public abstract class BaseHttpJob extends BaseJob {

    public final static String TAG = "HttpJob";

    protected Response response;

    private final int id;
    private static final AtomicInteger jobCounter = new AtomicInteger(0);
    protected ApiService service = ApiFactory.getApiService();
    protected String jsonString;
    protected String url;
    protected HashMap<Object, ArrayList<Course>> answer;
    protected static final String GROUP = "https";
    protected static final String time="1498817708070";
    protected BaseHttpJob(Params params) {
        super(params.groupBy(GROUP).addTags(TAG));
        id = jobCounter.incrementAndGet();
    }

    @Override
    public void onRun() throws Throwable {
        try {
            response = new Response();
            if(getMemoryCache() == null) {
                Call<ResponseBody> call = apiCall(time);
                getUrl(call);
                response.setAnswer(getHttpAnswer(call));
                checkResponseAnswer(response);
                if (response.getRequestResult() == RequestResult.SUCCESS) {
                    onSuccess();
                } else {
                    onError();
                }
            }else {
                response.setAnswer(getMemoryCache());
                response.setRequestResult(RequestResult.SUCCESS);
            }
        } catch (IOException e) {
            onError();
        }
        EventBus.getDefault().post(getHttpEvent(response));
    }

    protected void onSuccess() {
        saveResponse();
        saveMemoryCache(answer);
    }

    protected void onError(){
        HashMap<Object, ArrayList<Course>>  cache = getHttpCache();

        if(cache == null) {
            response.setRequestResult(RequestResult.ERROR);
            return;
        }

        response.setAnswer(cache)
                .setRequestResult(RequestResult.CACHE);
    }

    protected void checkResponseAnswer(Response response){
        if(response.getAnswer() == null) {
            response.setRequestResult(RequestResult.ERROR);
        }else if(!(response.getAnswer().isEmpty())){
            response.setRequestResult(RequestResult.SUCCESS);
        }else {
            response.setRequestResult(RequestResult.SERVER_ERROR);
        }
    }

    protected abstract Call<ResponseBody> apiCall(String time);

    protected abstract HttpEvent getHttpEvent(Response response);
    protected void parseResponse(retrofit2.Response<ResponseBody> retrofitResponse) throws IOException {
        jsonString = retrofitResponse.body().string();
    }

    protected void getUrl(Call call){
        url = call.request().url().toString();
    }

    protected HashMap<Object, ArrayList<Course>> getHttpAnswer(Call<ResponseBody> call) throws IOException {

        retrofit2.Response<ResponseBody> retrofitResponse = call.execute();
        parseResponse(retrofitResponse);
        answer = new Gson().fromJson(jsonString, new TypeToken<HashMap<Object, ArrayList<Course>>>() {}.getType());
        return answer;
    }

    protected void saveMemoryCache(HashMap<Object, ArrayList<Course>> list){

    }

    protected HashMap<Object, ArrayList<Course>>  getMemoryCache(){
        return null;
    }

    protected void saveResponse(){

    }

    protected HashMap<Object, ArrayList<Course>>  getHttpCache(){
        return null;
    }

}