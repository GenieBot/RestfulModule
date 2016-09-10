package io.sponges.bot.modules.rest.route;

import io.sponges.bot.modules.rest.Route;
import org.json.JSONObject;
import spark.Request;
import spark.Response;

public class IndexRoute extends Route {

    public IndexRoute() {
        super(Method.GET, "/");
    }

    @Override
    protected void execute(Request request, Response response, JSONObject json) {
        json.put("man", "dont");
    }
}
