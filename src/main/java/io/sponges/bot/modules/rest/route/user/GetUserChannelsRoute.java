package io.sponges.bot.modules.rest.route.user;

import io.sponges.bot.api.entities.User;
import io.sponges.bot.api.entities.channel.Channel;
import io.sponges.bot.modules.rest.RequestWrapper;
import io.sponges.bot.modules.rest.route.generic.GenericUserRoute;
import org.json.JSONArray;
import org.json.JSONObject;
import spark.Response;

import java.util.Collection;

public class GetUserChannelsRoute extends GenericUserRoute {

    public GetUserChannelsRoute() {
        super(Method.GET, "/channels");
    }

    @Override
    protected JSONObject execute(RequestWrapper request, Response response, JSONObject json, User user) {
        Collection<Channel> channels = user.getChannels();
        JSONArray array = new JSONArray();
        channels.forEach(channel -> array.put(channel.getId()));
        json.put("channels", array);
        json.put("size", channels.size());
        return json;
    }
}
