package io.sponges.bot.modules.rest.route.network;

import io.sponges.bot.api.entities.Network;
import io.sponges.bot.api.entities.manager.ModuleDataManager;
import io.sponges.bot.api.module.Module;
import io.sponges.bot.api.module.ModuleManager;
import io.sponges.bot.api.storage.ModuleDataObject;
import io.sponges.bot.modules.rest.RequestWrapper;
import io.sponges.bot.modules.rest.route.generic.GenericNetworkRoute;
import org.json.JSONObject;
import spark.Response;

public class GetNetworkModuleRoute extends GenericNetworkRoute {

    private final ModuleManager moduleManager = module.getModuleManager();

    public GetNetworkModuleRoute() {
        super(Method.GET, "/modules/:module");
    }

    @Override
    protected JSONObject execute(RequestWrapper request, Response response, JSONObject json, Network network) {
        String moduleId = request.getRequest().params("module");
        if (moduleId == null || !moduleManager.isModule(moduleId)) {
            setError("Invalid module");
            return json;
        }
        Module module = moduleManager.getModule(moduleId);
        ModuleDataManager moduleDataManager = network.getModuleDataManager();
        ModuleDataObject dataObject = moduleDataManager.getData(module.getId());
        json.put("enabled", dataObject.isEnabled());
        return json;
    }
}
