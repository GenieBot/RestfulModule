package io.sponges.bot.modules.rest.route.client;

import io.sponges.bot.api.entities.Client;
import io.sponges.bot.modules.rest.route.generic.GenericClientRoute;
import org.json.JSONObject;
import spark.Request;
import spark.Response;

public class GetClientDataRoute extends GenericClientRoute {

    public GetClientDataRoute() {
        super(Method.GET, "/data");
    }

    @Override
    protected JSONObject execute(Request request, Response response, JSONObject json, Client client) {
        return new JSONObject(module.getStorage().serialize(client.getData()));
    }
}
