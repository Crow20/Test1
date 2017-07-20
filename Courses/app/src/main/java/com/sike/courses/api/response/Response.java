package com.sike.courses.api.response;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.sike.courses.mvp.model.Course;

import java.util.ArrayList;
import java.util.HashMap;
/**
 * Created by agritsenko on 29.05.2017.
 */

public class Response {


    public class Answer extends HashMap<Object, ArrayList<Course>> {
        public Answer(HashMap<Object, ArrayList<Course>> answer) {
            super(answer);
        }

    }

    @Nullable
    private Answer mAnswer;

    private RequestResult mRequestResult;

    public Response() {
        mRequestResult = RequestResult.ERROR;
    }

    @NonNull
    public RequestResult getRequestResult() {
        return mRequestResult;
    }

    public Response setRequestResult(@NonNull RequestResult requestResult) {
        mRequestResult = requestResult;
        return this;
    }

    @Nullable
    public <T> T getTypedAnswer() {
        if (mAnswer == null) {
            return null;
        }
        return (T) mAnswer;
    }

    @NonNull
    public Response setAnswer(@Nullable HashMap<Object, ArrayList<Course>> answer) {
        if(answer == null){
            mAnswer = null;
            return this;
        }

        mAnswer = new Answer(answer);
        answer = null;
        return this;
    }

    @Nullable
    public HashMap<Object, ArrayList<Course>> getAnswer() {
        return mAnswer;
    }

    public void save(@NonNull Context context, String url, String jsonString) {
    }
}
