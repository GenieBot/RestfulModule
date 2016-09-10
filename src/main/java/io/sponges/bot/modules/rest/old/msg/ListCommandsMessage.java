package io.sponges.bot.modules.rest.old.msg;

import io.sponges.bot.api.cmd.Command;
import io.sponges.bot.api.cmd.CommandManager;
import io.sponges.bot.api.event.events.channelmsg.ChannelMessageReceiveEvent;
import io.sponges.bot.api.module.Module;
import io.sponges.bot.modules.rest.old.Message;
import org.json.JSONArray;
import org.json.JSONObject;

public class ListCommandsMessage extends Message {

    private final CommandManager commandManager;

    public ListCommandsMessage(Module module) {
        super("list_commands");
        this.commandManager = module.getCommandManager();
    }

    @Override
    public String getResponse(ChannelMessageReceiveEvent event, String[] args) {
        JSONObject json = new JSONObject();
        JSONArray array = new JSONArray();
        for (Command command : commandManager.getCommands()) {
            JSONObject object = new JSONObject();
            object.put("primary-name", command.getNames()[0]);
            object.put("names", command.getNames());
            object.put("description", command.getDescription());
            array.put(object);
        }
        json.put("commands", array);
        return json.toString();
    }
}
