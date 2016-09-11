package io.sponges.bot.modules.rest;

import io.sponges.bot.api.module.Module;
import org.json.JSONObject;
import spark.Request;
import spark.Response;

public abstract class Route {

    private static final String TEMP_API_KEY = "dummy";
    private static final String CONTENT_TYPE_HEADER = "application/json";

    protected static Module module = null;

    private final Method method;
    private final String route;

    private String error = null;

    public Route(Method method, String route) {
        this.method = method;
        this.route = route;
    }

    protected abstract JSONObject execute(RequestWrapper request, Response response, JSONObject json);

    private boolean isAuthenticated(RequestWrapper request) {
        if (method != Method.GET && method != Method.DELETE && method != Method.HEAD && method != Method.OPTIONS) {
            JSONObject body = request.getBody();
            if (body.isNull("key")) {
                error = "API key required";
                return false;
            }
            String key = body.getString("key");
            if (!key.equals(TEMP_API_KEY)) {
                error = "Invalid API key";
                return false;
            }
        } else {
            if (!request.getRequest().queryParams().contains("key")) {
                error = "API key required";
                return false;
            }
            String key = request.getRequest().queryParams("key");
            if (!key.equals(TEMP_API_KEY)) {
                error = "Invalid API key";
                return false;
            }
        }
        return true;
    }

    // spaghetti
    Object internalExecute(Request sparkRequest, Response response) {
        RequestWrapper request = new RequestWrapper(sparkRequest);
        response.header("Content-Type", CONTENT_TYPE_HEADER);
        JSONObject json;
        if (isAuthenticated(request)) {
            json = execute(request, response, new JSONObject());
        } else {
            json = new JSONObject();
        }
        JSONObject body = new JSONObject();
        body.put("content", json);
        body.put("api_version", RestModule.API_VERSION);
        boolean hasError = error != null;
        body.put("error", hasError);
        if (hasError) {
            body.put("error_message", error);
        }
        error = null;
        String bodyString = body.toString();
        if (method != Method.GET && method != Method.DELETE && method != Method.HEAD && method != Method.OPTIONS) {
            if (!request.getBody().isNull("pretty") && request.getBody().getBoolean("pretty")) {
                bodyString = body.toString(4);
            }
        } else {
            if (sparkRequest.queryParams().contains("pretty")
                    && Boolean.parseBoolean(sparkRequest.queryParams("pretty"))) {
                bodyString = body.toString(4);
            }
        }
        return bodyString;
    }

    protected JSONObject getJson(Request request) {
        return new JSONObject(request.body());
    }

    public enum Method {
        GET, POST, PUT, PATCH, DELETE, HEAD, OPTIONS
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
