package io.sponges.bot.modules.rest.route.channel;

import io.sponges.bot.api.entities.channel.Channel;
import io.sponges.bot.modules.rest.RequestWrapper;
import io.sponges.bot.modules.rest.route.generic.GenericChannelRoute;
import org.json.JSONObject;
import spark.Response;

public class GetChannelDataRoute extends GenericChannelRoute {

    public GetChannelDataRoute() {
        super(Method.GET, "/data");
    }

    @Override
    protected JSONObject execute(RequestWrapper request, Response response, JSONObject json, Channel channel) {
        JSONObject object = new JSONObject(module.getStorage().serialize(channel.getData()));
        object.keySet().forEach(key -> {
            Object obj = object.get(key);
            json.put(key, obj);
        });
        return json;
    }
}
