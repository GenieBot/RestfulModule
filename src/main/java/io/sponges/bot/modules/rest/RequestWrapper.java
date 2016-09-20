package io.sponges.bot.modules.rest;

import org.json.JSONObject;
import spark.Request;

public class RequestWrapper {

    private static final Route.Method[] BODY_METHODS = {
            Route.Method.POST, Route.Method.DELETE, Route.Method.HEAD, Route.Method.OPTIONS
    };
        
    private final JSONObject body;
    private final Request request;

    public RequestWrapper(Request request) {
        this.request = request;
        Route.Method method = Route.Method.valueOf(request.requestMethod().toUpperCase());
        for (Route.Method bMethod : BODY_METHODS) {
            if (method == bMethod) {
                String requestBody = request.body();
                if (requestBody != null) {
                    this.body = new JSONObject(request.body());
                } else {
                    this.body = null;
                }
                return;
            }
        }
        this.body = null;
    }

    public Request getRequest() {
        return request;
    }

    public JSONObject getBody() {
        return body;
    }
}