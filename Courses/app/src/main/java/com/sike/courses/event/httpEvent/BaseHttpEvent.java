package com.sike.courses.event.httpEvent;

/**
 * Created by agritsenko on 29.05.2017.
 */
import com.sike.courses.api.response.Response;

public class BaseHttpEvent extends HttpEvent {

    public final static int GET_DATA = 0;

    public int type;

    public BaseHttpEvent(Response data, int type) {
        super(data);

        this.type = type;
    }
}

