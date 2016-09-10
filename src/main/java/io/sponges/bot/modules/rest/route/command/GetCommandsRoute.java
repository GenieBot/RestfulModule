package io.sponges.bot.modules.rest.route.command;

import io.sponges.bot.api.cmd.Command;
import io.sponges.bot.api.cmd.CommandManager;
import io.sponges.bot.modules.rest.Route;
import org.json.JSONArray;
import org.json.JSONObject;
import spark.Request;
import spark.Response;

import java.util.Collection;

public class GetCommandsRoute extends Route {

    private final CommandManager commandManager = module.getCommandManager();

    public GetCommandsRoute() {
        super(Method.GET, "/commands");
    }

    @Override
    protected JSONObject execute(Request request, Response response, JSONObject json) {
        Collection<Command> commands = commandManager.getCommands();
        JSONArray array = new JSONArray();
        commands.forEach(command -> array.put(command.getNames()[0]));
        json.put("commands", array);
        json.put("size", commands.size());
        return json;
    }
}
