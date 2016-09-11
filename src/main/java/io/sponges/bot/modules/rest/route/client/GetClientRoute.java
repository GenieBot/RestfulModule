package io.sponges.bot.modules.rest.route.client;

import io.sponges.bot.api.entities.Client;
import io.sponges.bot.modules.rest.RequestWrapper;
import io.sponges.bot.modules.rest.route.generic.GenericClientRoute;
import org.json.JSONObject;
import spark.Response;

public class GetClientRoute extends GenericClientRoute {

    public GetClientRoute() {
        super(Method.GET, "");
    }

    @Override
    protected JSONObject execute(RequestWrapper request, Response response, JSONObject json, Client client) {
        json.put("default_prefix", client.getDefaultPrefix());
        return json;
    }
}
