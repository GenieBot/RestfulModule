package io.sponges.bot.modules.rest.route.network;

import io.sponges.bot.api.entities.Network;
import io.sponges.bot.api.module.Module;
import io.sponges.bot.api.module.ModuleManager;
import io.sponges.bot.modules.rest.RequestWrapper;
import io.sponges.bot.modules.rest.route.generic.GenericNetworkRoute;
import org.json.JSONObject;
import spark.Response;

public class UpdateNetworkModuleDataRoute extends GenericNetworkRoute {

    private final ModuleManager moduleManager = module.getModuleManager();

    public UpdateNetworkModuleDataRoute() {
        super(Method.POST, "/modules/:module/data");
    }

    @Override
    protected JSONObject execute(RequestWrapper request, Response response, JSONObject json, Network network) {
        JSONObject body = request.getBody();
        JSONObject content = body.getJSONObject("content");
        int moduleId = Integer.parseInt(request.getRequest().params("module"));
        if (!moduleManager.isModule(moduleId)) {
            setError("Invalid module");
            return json;
        }
        Module module = moduleManager.getModule(moduleId);
        module.getData().save(network, content);
        content.keySet().forEach(key -> json.put(key, content.get(key)));
        return json;
    }
}
