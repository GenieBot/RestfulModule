package io.sponges.bot.modules.rest.route.channel;

import io.sponges.bot.api.entities.channel.Channel;
import io.sponges.bot.api.entities.channel.GroupChannel;
import io.sponges.bot.api.entities.channel.PrivateChannel;
import io.sponges.bot.modules.rest.RequestWrapper;
import io.sponges.bot.modules.rest.route.generic.GenericChannelRoute;
import org.json.JSONObject;
import spark.Response;

public class GetChannelRoute extends GenericChannelRoute {

    public GetChannelRoute() {
        super(Method.GET, "");
    }

    @Override
    protected JSONObject execute(RequestWrapper request, Response response, JSONObject json, Channel channel) {
        json.put("id", channel.getId());
        json.put("group", channel instanceof GroupChannel);
        json.put("private", channel instanceof PrivateChannel);
        return json;
    }
}
