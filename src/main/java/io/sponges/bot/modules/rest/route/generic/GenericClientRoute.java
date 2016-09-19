package io.sponges.bot.modules.rest.route.generic;

import io.sponges.bot.api.entities.Client;
import io.sponges.bot.api.entities.manager.ClientManager;
import io.sponges.bot.modules.rest.RequestWrapper;
import io.sponges.bot.modules.rest.Route;
import org.json.JSONObject;
import spark.Response;

import java.util.UUID;

public abstract class GenericClientRoute extends Route {

    private final ClientManager clientManager = module.getClientManager();

    public GenericClientRoute(Method method, String route) {
        super(method, "/clients/:client" + route);
    }

    protected abstract JSONObject execute(RequestWrapper request, Response response, JSONObject json, Client client);

    @Override
    protected JSONObject execute(RequestWrapper request, Response response, JSONObject json) {
        UUID clientId = UUID.fromString(request.getRequest().params("client"));
        if (!clientManager.isClient(clientId)) {
            setError("Invalid client");
            return json;
        }
        Client client = clientManager.getClient(clientId);
        execute(request, response, json, client);
        return json;
    }
}
