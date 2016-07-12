package io.sponges.bot.modules.restmodule.msg;

import io.sponges.bot.api.entities.Client;
import io.sponges.bot.api.entities.Network;
import io.sponges.bot.api.entities.manager.ClientManager;
import io.sponges.bot.api.entities.manager.ModuleDataManager;
import io.sponges.bot.api.entities.manager.NetworkManager;
import io.sponges.bot.api.event.events.channelmsg.ChannelMessageReceiveEvent;
import io.sponges.bot.api.module.Module;
import io.sponges.bot.api.module.ModuleManager;
import io.sponges.bot.api.storage.DataObject;
import io.sponges.bot.api.storage.ModuleDataObject;
import io.sponges.bot.api.storage.Storage;
import io.sponges.bot.modules.restmodule.Message;
import org.json.JSONObject;

public class ModifyModuleMessage extends Message {

    private final ClientManager clientManager;
    private final ModuleManager moduleManager;
    private final Storage storage;

    public ModifyModuleMessage(Module module) {
        super("modify_module");
        this.clientManager = module.getClientManager();
        this.moduleManager = module.getModuleManager();
        this.storage = module.getStorage();
    }

    @Override
    public String getResponse(ChannelMessageReceiveEvent event, String[] args) {
        JSONObject json = new JSONObject();
        if (args.length < 4) {
            return json.put("error", "Not enough arguments").toString();
        }
        String clientId = args[0].toLowerCase();
        if (!clientManager.isClient(clientId)) {
            return json.put("error", "Invalid client id").toString();
        }
        Client client = clientManager.getClient(clientId);
        NetworkManager networkManager = client.getNetworkManager();
        String networkId = args[1];
        if (!networkManager.isNetwork(networkId)) {
            return json.put("error", "Invalid network id").toString();
        }
        Network network = networkManager.getNetwork(networkId);
        String moduleId = args[2].toLowerCase();
        if (!moduleManager.isModule(moduleId)) {
            return json.put("error", "Invalid module id").toString();
        }
        Module module = moduleManager.getModule(moduleId);
        ModuleDataManager moduleDataManager = network.getModuleDataManager();
        ModuleDataObject moduleDataObject = moduleDataManager.getData(module.getId());
        StringBuilder bodyContent = new StringBuilder();
        for (int i = 3; i < args.length; i++) {
            String arg = args[i];
            bodyContent.append(arg);
            if (i != args.length - 1) {
                bodyContent.append(' ');
            }
        }
        JSONObject body = new JSONObject(bodyContent.toString());
        populate(moduleDataObject, body);
        storage.save(network.getData());
        return json.put("response", "OK").toString();
    }

    private DataObject populate(DataObject object, JSONObject json) {
        for (String key : json.keySet()) {
            Object obj = json.get(key);
            if (obj instanceof JSONObject) {
                object.set(key, populate(new DataObject(), (JSONObject) obj));
            } else {
                object.set(key, obj);
            }
        }
        return object;
    }

}
