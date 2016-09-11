package io.sponges.bot.modules.rest.route.client;

import io.sponges.bot.api.entities.Client;
import io.sponges.bot.modules.rest.RequestWrapper;
import io.sponges.bot.modules.rest.route.generic.GenericClientRoute;
import org.json.JSONObject;
import spark.Response;

public class GetClientDataRoute extends GenericClientRoute {

    public GetClientDataRoute() {
        super(Method.GET, "/data");
    }

    @Override
    protected JSONObject execute(RequestWrapper request, Response response, JSONObject json, Client client) {
        JSONObject object = new JSONObject(module.getStorage().serialize(client.getData()));
        object.keySet().forEach(key -> {
            Object obj = object.get(key);
            json.put(key, obj);
        });
        return json;
    }
}
