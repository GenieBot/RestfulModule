package io.sponges.bot.modules.restmodule.msg;

import io.sponges.bot.api.cmd.CommandManager;
import io.sponges.bot.api.event.events.channelmsg.ChannelMessageReceiveEvent;
import io.sponges.bot.api.module.Module;
import io.sponges.bot.api.module.ModuleManager;
import io.sponges.bot.modules.restmodule.Message;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Collection;

public class ListModulesMessage extends Message {

    private final ModuleManager moduleManager;
    private final CommandManager commandManager;

    public ListModulesMessage(Module module) {
        super("list_modules");
        this.moduleManager = module.getModuleManager();
        this.commandManager = module.getCommandManager();
    }

    @Override
    public String getResponse(ChannelMessageReceiveEvent event, String[] args) {
        JSONObject json = new JSONObject();
        JSONArray array = new JSONArray();
        Collection<Module> modules = moduleManager.getModules();
        for (Module module : modules) {
            JSONObject object = new JSONObject();
            object.put("id", module.getId());
            object.put("version", module.getVersion());
            if (commandManager.hasCommands(module)) {
                object.put("commands", commandManager.getCommands(module).size());
            } else {
                object.put("commands", 0);
            }
            array.put(object);
        }
        json.put("modules", array);
        return json.toString();
    }
}
