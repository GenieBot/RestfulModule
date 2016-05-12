package io.sponges.bot.modules.restmodule.msg;

import io.sponges.bot.api.entities.Client;
import io.sponges.bot.api.entities.manager.ClientManager;
import io.sponges.bot.api.event.events.channelmsg.ChannelMessageReceiveEvent;
import io.sponges.bot.api.module.Module;
import io.sponges.bot.modules.restmodule.Message;
import org.json.JSONArray;
import org.json.JSONObject;

public class ListClientsMessage extends Message {

    private final Module module;
    private final ClientManager clientManager;

    public ListClientsMessage(Module module) {
        super("list_clients");
        this.module = module;
        this.clientManager = module.getClientManager();
    }

    @Override
    public String getResponse(ChannelMessageReceiveEvent event, String[] args) {
        JSONObject json = new JSONObject();
        JSONArray array = new JSONArray();
        for (Client client : clientManager.getClients().values()) {
            JSONObject object = new JSONObject();
            object.put("id", client.getId());
            object.put("prefix", client.getDefaultPrefix());
            object.put("networks", client.getNetworkManager().getNetworks().values().size());
            array.put(object);
        }
        json.put("clients", array);
        return json.toString();
    }
}
