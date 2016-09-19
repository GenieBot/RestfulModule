package io.sponges.bot.modules.rest.route.generic;

import io.sponges.bot.api.entities.Client;
import io.sponges.bot.api.entities.Network;
import io.sponges.bot.api.entities.manager.NetworkManager;
import io.sponges.bot.modules.rest.RequestWrapper;
import org.json.JSONObject;
import spark.Response;

import java.util.UUID;

public abstract class GenericNetworkRoute extends GenericClientRoute {

    public GenericNetworkRoute(Method method, String route) {
        super(method, "/networks/:network" + route);
    }

    protected abstract JSONObject execute(RequestWrapper request, Response response, JSONObject json, Network network);

    @Override
    protected JSONObject execute(RequestWrapper request, Response response, JSONObject json, Client client) {
        NetworkManager networkManager = client.getNetworkManager();
        UUID networkId = UUID.fromString(request.getRequest().params("network"));
        if (!networkManager.isNetwork(networkId)) {
            Network network = networkManager.loadNetworkSync(networkId);
            if (network == null) {
                setError("Network not found in database");
            } else {
                execute(request, response, json, network);
            }
        } else {
            Network network = networkManager.getNetwork(networkId);
            execute(request, response, json, network);
        }
        return json;
    }
}
