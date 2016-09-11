package io.sponges.bot.modules.rest.route;

import io.sponges.bot.modules.rest.RequestWrapper;
import io.sponges.bot.modules.rest.Route;
import org.json.JSONObject;
import spark.Response;

public class GetIndexRoute extends Route {

    public GetIndexRoute() {
        super(Method.GET, "/");
    }

    @Override
    protected JSONObject execute(RequestWrapper request, Response response, JSONObject json) {
        return json;
    }
}
