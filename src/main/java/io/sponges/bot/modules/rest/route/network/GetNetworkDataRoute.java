package io.sponges.bot.modules.rest.route.network;

import io.sponges.bot.api.entities.Network;
import io.sponges.bot.modules.rest.route.generic.GenericNetworkRoute;
import org.json.JSONObject;
import spark.Request;
import spark.Response;

public class GetNetworkDataRoute extends GenericNetworkRoute {

    public GetNetworkDataRoute() {
        super(Method.GET, "/data");
    }

    @Override
    protected JSONObject execute(Request request, Response response, JSONObject json, Network network) {
        return new JSONObject(module.getStorage().serialize(network.getData()));
    }
}
