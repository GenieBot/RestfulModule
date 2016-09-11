package io.sponges.bot.modules.rest;

import org.json.JSONObject;
import spark.Request;

public class RequestWrapper {
        
    private final JSONObject body;
    private final Request request;

    public RequestWrapper(Request request) {
        Route.Method method = Route.Method.valueOf(request.requestMethod().toUpperCase());
        if (method != Route.Method.GET && method != Route.Method.DELETE && method != Route.Method.HEAD
                && method != Route.Method.OPTIONS) {
            this.body = new JSONObject(request.body());
        } else {
            this.body = null;
        }
        this.request = request;
    }

    public Request getRequest() {
        return request;
    }

    public JSONObject getBody() {
        return body;
    }
}