package io.sponges.bot.modules.rest.route.network;

import io.sponges.bot.api.entities.Network;
import io.sponges.bot.modules.rest.RequestWrapper;
import io.sponges.bot.modules.rest.route.generic.GenericNetworkRoute;
import org.json.JSONObject;
import spark.Response;

public class GetNetworkDataRoute extends GenericNetworkRoute {

    public GetNetworkDataRoute() {
        super(Method.GET, "/data");
    }

    @Override
    protected JSONObject execute(RequestWrapper request, Response response, JSONObject json, Network network) {
        JSONObject object = new JSONObject(module.getStorage().serialize(network.getData()));
        object.keySet().forEach(key -> json.put(key, object.get(key)));
        return json;
    }
}
