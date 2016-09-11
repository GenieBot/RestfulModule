package io.sponges.bot.modules.rest.route.module;

import io.sponges.bot.api.cmd.Command;
import io.sponges.bot.api.cmd.CommandManager;
import io.sponges.bot.api.module.Module;
import io.sponges.bot.modules.rest.RequestWrapper;
import io.sponges.bot.modules.rest.route.generic.GenericModuleRoute;
import org.json.JSONArray;
import org.json.JSONObject;
import spark.Response;

import java.util.Collection;

public class GetModuleCommandsRoute extends GenericModuleRoute {

    private final CommandManager commandManager = module.getCommandManager();

    public GetModuleCommandsRoute() {
        super(Method.GET, "/commands");
    }

    @Override
    protected JSONObject execute(RequestWrapper request, Response response, JSONObject json, Module module) {
        Collection<Command> commands = commandManager.getCommands(module);
        JSONArray array = new JSONArray();
        commands.forEach(command -> array.put(command.getNames()[0]));
        json.put("commands", array);
        json.put("size", commands.size());
        return json;
    }
}
