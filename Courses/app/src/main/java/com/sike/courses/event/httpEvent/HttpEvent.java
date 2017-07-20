package com.sike.courses.event.httpEvent;

/**
 * Created by agritsenko on 29.05.2017.
 */
import com.sike.courses.api.response.Response;

public abstract class HttpEvent {

    protected Response data;

    public HttpEvent(Response data) {
        this.data = data;
    }

    public Response getResult() {
        return data;
    }
}
