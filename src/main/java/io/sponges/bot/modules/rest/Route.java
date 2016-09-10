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

    protected abstract void execute(Request request, Response response, JSONObject json);

    private boolean isAuthenticated(Request request) {
        if (method != Method.GET && method != Method.DELETE && method != Method.HEAD && method != Method.OPTIONS) {
            JSONObject requestBody;
            try {
                requestBody = new JSONObject(request.body());
            } catch (Exception ignored) {
                error = "API key required";
                return false;
            }
            if (requestBody.isNull("key")) {
                error = "API key required";
                return false;
            }
            String key = requestBody.getString("key");
            if (!key.equals(TEMP_API_KEY)) {
                error = "Invalid API key";
                return false;
            }
        } else {
            if (!request.queryParams().contains("key")) {
                error = "API key required";
                return false;
            }
            String key = request.queryParams("key");
            if (!key.equals(TEMP_API_KEY)) {
                error = "Invalid API key";
                return false;
            }
        }
        return true;
    }

    Object internalExecute(Request request, Response response) {
        response.header("Content-Type", CONTENT_TYPE_HEADER);
        JSONObject json = new JSONObject();
        if (isAuthenticated(request)) {
            execute(request, response, json);
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
