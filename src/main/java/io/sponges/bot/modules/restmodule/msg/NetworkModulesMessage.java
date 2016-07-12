package io.sponges.bot.modules.restmodule.msg;

import io.sponges.bot.api.entities.Client;
import io.sponges.bot.api.entities.Network;
import io.sponges.bot.api.entities.manager.ClientManager;
import io.sponges.bot.api.entities.manager.ModuleDataManager;
import io.sponges.bot.api.entities.manager.NetworkManager;
import io.sponges.bot.api.event.events.channelmsg.ChannelMessageReceiveEvent;
import io.sponges.bot.api.module.Module;
import io.sponges.bot.api.module.ModuleManager;
import io.sponges.bot.api.storage.ModuleDataObject;
import io.sponges.bot.api.storage.Storage;
import io.sponges.bot.modules.restmodule.Message;
import org.json.JSONObject;

import java.util.Collection;

public class NetworkModulesMessage extends Message {

    private final ClientManager clientManager;
    private final ModuleManager moduleManager;
    private final Storage storage;

    public NetworkModulesMessage(Module module) {
        super("network_modules");
        this.clientManager = module.getClientManager();
        this.moduleManager = module.getModuleManager();
        this.storage = module.getStorage();
    }

    @Override
    public String getResponse(ChannelMessageReceiveEvent event, String[] args) {
        JSONObject json = new JSONObject();
        if (args.length < 2) {
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
        ModuleDataManager moduleDataManager = network.getModuleDataManager();
        Collection<Module> modules = moduleManager.getModules();
        JSONObject object = new JSONObject();
        modules.forEach(module -> {
            ModuleDataObject data = moduleDataManager.getData(module.getId());
            String serialized = storage.serialize(data);
            JSONObject moduleObject = new JSONObject(serialized);
            moduleObject.remove("module_data_object");
            object.put(module.getId(), moduleObject);
        });
        return json.put("modules", object).toString();
    }
}
