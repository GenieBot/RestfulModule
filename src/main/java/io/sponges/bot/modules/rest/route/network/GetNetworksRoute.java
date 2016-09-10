package io.sponges.bot.modules.rest.route.network;

import io.sponges.bot.api.entities.Client;
import io.sponges.bot.api.entities.Network;
import io.sponges.bot.api.entities.manager.NetworkManager;
import io.sponges.bot.modules.rest.route.generic.GenericClientRoute;
import org.json.JSONArray;
import org.json.JSONObject;
import spark.Request;
import spark.Response;

import java.util.Collection;

public class GetNetworksRoute extends GenericClientRoute {

    public GetNetworksRoute() {
        super(Method.GET, "/networks");
    }

    @Override
    protected void execute(Request request, Response response, JSONObject json, Client client) {
        NetworkManager networkManager = client.getNetworkManager();
        Collection<Network> networks = networkManager.getNetworks().values();
        JSONArray array = new JSONArray();
        networks.forEach(network -> array.put(network.getId()));
        json.put("networks", array);
        json.put("size", networks.size());
    }
}
