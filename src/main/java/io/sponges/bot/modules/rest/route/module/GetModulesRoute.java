package io.sponges.bot.modules.rest.route.module;

import io.sponges.bot.api.module.Module;
import io.sponges.bot.api.module.ModuleManager;
import io.sponges.bot.modules.rest.RequestWrapper;
import io.sponges.bot.modules.rest.Route;
import org.json.JSONObject;
import spark.Response;

import java.util.Collection;

public class GetModulesRoute extends Route {

    private final ModuleManager moduleManager = module.getModuleManager();

    public GetModulesRoute() {
        super(Method.GET, "/modules");
    }

    @Override
    protected JSONObject execute(RequestWrapper request, Response response, JSONObject json) {
        Collection<Module> modules = moduleManager.getModules();
        JSONObject obj = new JSONObject();
        modules.forEach(module -> obj.put(String.valueOf(module.getId()), module.getName()));
        json.put("modules", obj);
        json.put("size", modules.size());
        return json;
    }
}
