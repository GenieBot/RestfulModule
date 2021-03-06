package io.sponges.bot.modules.rest.route.client;

import io.sponges.bot.api.entities.Client;
import io.sponges.bot.api.entities.manager.ClientManager;
import io.sponges.bot.modules.rest.RequestWrapper;
import io.sponges.bot.modules.rest.Route;
import org.json.JSONArray;
import org.json.JSONObject;
import spark.Response;

import java.util.Collection;

public class GetClientsRoute extends Route {

    private ClientManager clientManager = module.getClientManager();

    public GetClientsRoute() {
        super(Method.GET, "/clients");
    }

    @Override
    protected JSONObject execute(RequestWrapper request, Response response, JSONObject json) {
        Collection<Client> clients = clientManager.getClients().values();
        JSONArray array = new JSONArray();
        clients.forEach(client -> array.put(client.getId()));
        json.put("clients", array);
        json.put("size", clients.size());
        return json;
    }
}
