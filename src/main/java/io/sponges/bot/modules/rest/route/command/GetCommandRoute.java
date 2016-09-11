package io.sponges.bot.modules.rest.route.command;

import io.sponges.bot.api.cmd.Command;
import io.sponges.bot.modules.rest.RequestWrapper;
import io.sponges.bot.modules.rest.route.generic.GenericCommandRoute;
import org.json.JSONObject;
import spark.Response;

public class GetCommandRoute extends GenericCommandRoute {

    public GetCommandRoute() {
        super(Method.GET, "");
    }

    @Override
    protected JSONObject execute(RequestWrapper request, Response response, JSONObject json, Command command) {
        json.put("id", command.getNames()[0]);
        json.put("names", command.getNames());
        json.put("description", command.getDescription());
        return json;
    }
}
