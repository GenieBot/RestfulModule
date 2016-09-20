package io.sponges.bot.modules.rest.route.module;

import io.sponges.bot.api.module.Module;
import io.sponges.bot.modules.rest.RequestWrapper;
import io.sponges.bot.modules.rest.route.generic.GenericModuleRoute;
import org.json.JSONObject;
import spark.Response;

public class GetModuleRoute extends GenericModuleRoute {

    public GetModuleRoute() {
        super(Method.GET, "");
    }

    @Override
    protected JSONObject execute(RequestWrapper request, Response response, JSONObject json, Module module) {
        json.put("id", module.getId());
        json.put("name", module.getName());
        json.put("version", module.getVersion());
        json.put("required", module.isRequired());
        return json;
    }
}
