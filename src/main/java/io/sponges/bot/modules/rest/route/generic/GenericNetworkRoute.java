package io.sponges.bot.modules.rest.route.generic;

import io.sponges.bot.api.entities.Client;
import io.sponges.bot.api.entities.Network;
import io.sponges.bot.api.entities.manager.NetworkManager;
import org.json.JSONObject;
import spark.Request;
import spark.Response;

public abstract class GenericNetworkRoute extends GenericClientRoute {

    public GenericNetworkRoute(Method method, String route) {
        super(method, "/networks/:network" + route);
    }

    protected abstract JSONObject execute(Request request, Response response, JSONObject json, Network network);

    @Override
    protected JSONObject execute(Request request, Response response, JSONObject json, Client client) {
        NetworkManager networkManager = client.getNetworkManager();
        String networkId = request.params("network");
        if (networkId == null) {
            setError("Invalid network");
            return json;
        }
        if (!networkManager.isNetwork(networkId)) {
            Network network = networkManager.loadNetworkSync(networkId);
            if (network == null) {
                setError("Network not found in client");
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
