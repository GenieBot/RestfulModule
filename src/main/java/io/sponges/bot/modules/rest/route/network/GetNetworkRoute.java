package io.sponges.bot.modules.rest.route.network;

import io.sponges.bot.api.entities.Network;
import io.sponges.bot.modules.rest.route.generic.GenericNetworkRoute;
import org.json.JSONObject;
import spark.Request;
import spark.Response;

public class GetNetworkRoute extends GenericNetworkRoute {

    public GetNetworkRoute() {
        super(Method.GET, "");
    }

    @Override
    protected void execute(Request request, Response response, JSONObject json, Network network) {
        json.put("id", network.getId());
    }
}
