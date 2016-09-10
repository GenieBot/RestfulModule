package io.sponges.bot.modules.rest.route.client;

import io.sponges.bot.api.entities.Client;
import io.sponges.bot.modules.rest.route.generic.GenericClientRoute;
import org.json.JSONObject;
import spark.Request;
import spark.Response;

public class GetClientRoute extends GenericClientRoute {

    public GetClientRoute() {
        super(Method.GET, "");
    }

    @Override
    protected void execute(Request request, Response response, JSONObject json, Client client) {
        json.put("id", client.getId());
        json.put("default_prefix", client.getDefaultPrefix());
    }
}
