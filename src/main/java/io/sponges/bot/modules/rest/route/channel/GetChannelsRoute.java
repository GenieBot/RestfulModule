package io.sponges.bot.modules.rest.route.channel;

import io.sponges.bot.api.entities.Network;
import io.sponges.bot.api.entities.channel.Channel;
import io.sponges.bot.api.entities.manager.ChannelManager;
import io.sponges.bot.modules.rest.route.generic.GenericNetworkRoute;
import org.json.JSONArray;
import org.json.JSONObject;
import spark.Request;
import spark.Response;

import java.util.Collection;

public class GetChannelsRoute extends GenericNetworkRoute {

    public GetChannelsRoute() {
        super(Method.GET, "/channels");
    }

    @Override
    protected void execute(Request request, Response response, JSONObject json, Network network) {
        ChannelManager channelManager = network.getChannelManager();
        Collection<Channel> channels = channelManager.getChannels().values();
        JSONArray array = new JSONArray();
        channels.forEach(channel -> array.put(channel.getId()));
        json.put("channels", array);
        json.put("size", channels.size());
    }
}
