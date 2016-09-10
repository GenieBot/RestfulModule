package io.sponges.bot.modules.rest;

import io.sponges.bot.api.module.Module;
import org.json.JSONObject;
import spark.Request;
import spark.Response;

public abstract class Route {

    private static final String CONTENT_TYPE_HEADER = "application/json";

    protected static Module module = null;

    private final Method method;
    private final String route;

    private String error = null;

    public Route(Method method, String route) {
        this.method = method;
        this.route = route;
    }

    protected abstract void execute(Request request, Response response, JSONObject json);

    Object internalExecute(Request request, Response response) {
        response.header("Content-Type", CONTENT_TYPE_HEADER);
        JSONObject json = new JSONObject();
        execute(request, response, json);
        JSONObject body = new JSONObject();
        body.put("content", json);
        body.put("version", RestModule.API_VERSION);
        boolean hasError = error != null;
        if (hasError) {
            JSONObject errorObject = new JSONObject();
            errorObject.put("message", error);
            body.put("error", errorObject);
        } else {
            body.put("error", false);
        }
        String bodyString;
        if (method == Method.GET && request.queryParams().contains("pretty")
                && Boolean.parseBoolean(request.queryParams("pretty"))) {
            bodyString = body.toString(4);
        } else {
            bodyString = body.toString();
        }
        return bodyString;
    }

    public enum Method {
        GET, POST, PUT, PATCH, DELETE, OPTIONS
    }

    Method getMethod() {
        return method;
    }

    String getRoute() {
        return route;
    }

    protected void setError(String error) {
        this.error = error;
    }

    static void setModule(Module module) {
        Route.module = module;
    }

}
