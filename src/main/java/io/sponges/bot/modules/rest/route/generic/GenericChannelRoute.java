package io.sponges.bot.modules.rest.route.generic;

import io.sponges.bot.api.entities.Network;
import io.sponges.bot.api.entities.channel.Channel;
import io.sponges.bot.api.entities.manager.ChannelManager;
import io.sponges.bot.modules.rest.RequestWrapper;
import org.json.JSONObject;
import spark.Response;

import java.util.UUID;

public abstract class GenericChannelRoute extends GenericNetworkRoute {

    public GenericChannelRoute(Method method, String route) {
        super(method, "/channels/:channel" + route);
    }

    protected abstract JSONObject execute(RequestWrapper request, Response response, JSONObject json, Channel channel);

    @Override
    protected JSONObject execute(RequestWrapper request, Response response, JSONObject json, Network network) {
        ChannelManager channelManager = network.getChannelManager();
        UUID channelId = UUID.fromString(request.getRequest().params("channel"));
        if (!channelManager.isChannel(channelId)) {
            Channel channel = channelManager.loadChannelSync(channelId);
            if (channel == null) {
                setError("Channel not found in database");
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
