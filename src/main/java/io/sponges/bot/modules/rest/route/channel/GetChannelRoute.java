package io.sponges.bot.modules.rest.route.channel;

import io.sponges.bot.api.entities.channel.Channel;
import io.sponges.bot.api.entities.channel.GroupChannel;
import io.sponges.bot.api.entities.channel.PrivateChannel;
import io.sponges.bot.api.entities.data.ChannelData;
import io.sponges.bot.modules.rest.RequestWrapper;
import io.sponges.bot.modules.rest.route.generic.GenericChannelRoute;
import org.json.JSONObject;
import spark.Response;

import java.util.Optional;

public class GetChannelRoute extends GenericChannelRoute {

    public GetChannelRoute() {
        super(Method.GET, "");
    }

    @Override
    protected JSONObject execute(RequestWrapper request, Response response, JSONObject json, Channel channel) {
        json.put("id", channel.getId());
        json.put("source_id", channel.getSourceId());
        json.put("group", channel instanceof GroupChannel);
        json.put("private", channel instanceof PrivateChannel);
        ChannelData data = channel.getChannelData();
        Optional<String> name = data.getName();
        Optional<String> topic = data.getTopic();
        if (name.isPresent()) json.put("name", name.get());
        if (topic.isPresent()) json.put("topic", topic.get());
        return json;
    }
}
