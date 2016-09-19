package io.sponges.bot.modules.rest.route.network;

import io.sponges.bot.api.entities.Network;
import io.sponges.bot.api.entities.manager.NetworkModuleManager;
import io.sponges.bot.api.module.Module;
import io.sponges.bot.api.module.ModuleManager;
import io.sponges.bot.modules.rest.RequestWrapper;
import io.sponges.bot.modules.rest.route.generic.GenericNetworkRoute;
import org.json.JSONObject;
import spark.Response;

public class ToggleNetworkModuleRoute extends GenericNetworkRoute {

    private final ModuleManager moduleManager = module.getModuleManager();

    public ToggleNetworkModuleRoute() {
        super(Method.POST, "/modules/:module");
    }

    @Override
    protected JSONObject execute(RequestWrapper request, Response response, JSONObject json, Network network) {
        JSONObject body = request.getBody();
        if (body.isNull("enabled")) {
            setError("Invalid body");
            return json;
        }
        boolean enabled = body.getBoolean("enabled");
        int moduleId = Integer.parseInt(request.getRequest().params("module"));
        if (!moduleManager.isModule(moduleId)) {
            setError("Invalid module");
            return json;
        }
        Module module = moduleManager.getModule(moduleId);
        NetworkModuleManager moduleManager = network.getModuleManager();
        moduleManager.setEnabled(module, enabled);
        json.put("enabled", enabled);
        return json;
    }
}
