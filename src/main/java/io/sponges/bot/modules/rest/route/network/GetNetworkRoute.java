package io.sponges.bot.modules.rest.route.network;

import io.sponges.bot.api.entities.Network;
import io.sponges.bot.api.entities.data.NetworkData;
import io.sponges.bot.modules.rest.RequestWrapper;
import io.sponges.bot.modules.rest.route.generic.GenericNetworkRoute;
import org.json.JSONObject;
import spark.Response;

import java.util.Optional;

public class GetNetworkRoute extends GenericNetworkRoute {

    public GetNetworkRoute() {
        super(Method.GET, "");
    }

    @Override
    protected JSONObject execute(RequestWrapper request, Response response, JSONObject json, Network network) {
        json.put("id", network.getId());
        json.put("source_id", network.getId());
        NetworkData data = network.getNetworkData();
        Optional<String> name = data.getName();
        Optional<String> image = data.getImage();
        Optional<String> description = data.getDescription();
        if (name.isPresent()) json.put("name", name.get());
        if (image.isPresent()) json.put("image", image.get());
        if (description.isPresent()) json.put("description", description.get());
        return json;
    }
}
