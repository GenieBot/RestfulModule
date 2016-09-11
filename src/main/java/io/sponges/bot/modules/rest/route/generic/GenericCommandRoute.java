package io.sponges.bot.modules.rest.route.generic;

import io.sponges.bot.api.cmd.Command;
import io.sponges.bot.api.cmd.CommandManager;
import io.sponges.bot.modules.rest.RequestWrapper;
import io.sponges.bot.modules.rest.Route;
import org.json.JSONObject;
import spark.Response;

public abstract class GenericCommandRoute extends Route {

    private final CommandManager commandManager = module.getCommandManager();

    public GenericCommandRoute(Method method, String route) {
        super(method, "/commands/:command" + route);
    }

    protected abstract JSONObject execute(RequestWrapper request, Response response, JSONObject json, Command command);

    @Override
    protected JSONObject execute(RequestWrapper request, Response response, JSONObject json) {
        String commandId = request.getRequest().params("command");
        if (commandId == null) {
            setError("Invalid command");
            return json;
        }
        Command command = commandManager.getCommand(commandId);
        if (command == null) {
            setError("Invalid command");
            return json;
        }
        execute(request, response, json, command);
        return json;
    }
}
