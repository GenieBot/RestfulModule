package io.sponges.bot.modules.rest.route.channel;

import io.sponges.bot.api.entities.channel.Channel;
import io.sponges.bot.modules.rest.route.generic.GenericChannelRoute;
import org.json.JSONObject;
import spark.Request;
import spark.Response;

public class GetChannelDataRoute extends GenericChannelRoute {

    public GetChannelDataRoute() {
        super(Method.GET, "/data");
    }

    @Override
    protected JSONObject execute(Request request, Response response, JSONObject json, Channel channel) {
        return new JSONObject(module.getStorage().serialize(channel.getData()));
    }
}
