package io.sponges.bot.modules.rest.route.client;

import io.sponges.bot.api.entities.Client;
import io.sponges.bot.api.entities.data.ClientData;
import io.sponges.bot.modules.rest.RequestWrapper;
import io.sponges.bot.modules.rest.route.generic.GenericClientRoute;
import org.json.JSONObject;
import spark.Response;

import java.util.Date;
import java.util.Optional;

public class GetClientRoute extends GenericClientRoute {

    public GetClientRoute() {
        super(Method.GET, "");
    }

    @Override
    protected JSONObject execute(RequestWrapper request, Response response, JSONObject json, Client client) {
        json.put("id", client.getId());
        json.put("source_id", client.getSourceId());
        json.put("default_prefix", client.getDefaultPrefix());
        ClientData data = client.getClientData();
        Optional<String> name = data.getName();
        Optional<Date> date = data.getConnectedDate();
        if (name.isPresent()) json.put("name", name.get());
        if (date.isPresent()) json.put("date", date.get().getTime());
        return json;
    }
}
