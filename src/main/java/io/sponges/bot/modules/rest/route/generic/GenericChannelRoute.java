package io.sponges.bot.modules.rest.route.generic;

import io.sponges.bot.api.entities.Network;
import io.sponges.bot.api.entities.channel.Channel;
import io.sponges.bot.api.entities.manager.ChannelManager;
import org.json.JSONObject;
import spark.Request;
import spark.Response;

public abstract class GenericChannelRoute extends GenericNetworkRoute {

    public GenericChannelRoute(Method method, String route) {
        super(method, "/channels/:channel" + route);
    }

    protected abstract JSONObject execute(Request request, Response response, JSONObject json, Channel channel);

    @Override
    protected JSONObject execute(Request request, Response response, JSONObject json, Network network) {
        ChannelManager channelManager = network.getChannelManager();
        String channelId = request.params("channel");
        if (channelId == null) {
            setError("Invalid channel");
            return json;
        }
        if (!channelManager.isChannel(channelId)) {
            Channel channel = channelManager.loadChannelSync(channelId);
            if (channel == null) {
                setError("Channel not found in client");
            } else {
                execute(request, response, json, channel);
            }
        } else {
            Channel channel = channelManager.getChannel(channelId);
            execute(request, response, json, channel);
        }
        return json;
    }
}
