package io.sponges.bot.modules.rest.route.generic;

import io.sponges.bot.api.module.Module;
import io.sponges.bot.api.module.ModuleManager;
import io.sponges.bot.modules.rest.RequestWrapper;
import io.sponges.bot.modules.rest.Route;
import org.json.JSONObject;
import spark.Response;

public abstract class GenericModuleRoute extends Route {

    private final ModuleManager moduleManager = module.getModuleManager();

    public GenericModuleRoute(Method method, String route) {
        super(method, "/modules/:module" + route);
    }

    protected abstract JSONObject execute(RequestWrapper request, Response response, JSONObject json, Module module);

    @Override
    protected JSONObject execute(RequestWrapper request, Response response, JSONObject json) {
        int moduleId = Integer.parseInt(request.getRequest().params("module"));
        if (!moduleManager.isModule(moduleId)) {
            setError("Invalid module");
            return json;
        }
        Module module = moduleManager.getModule(moduleId);
        execute(request, response, json, module);
        return json;
    }
}
