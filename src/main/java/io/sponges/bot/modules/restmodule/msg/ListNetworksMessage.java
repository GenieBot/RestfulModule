package io.sponges.bot.modules.restmodule.msg;

import io.sponges.bot.api.entities.Client;
import io.sponges.bot.api.entities.Network;
import io.sponges.bot.api.entities.manager.ClientManager;
import io.sponges.bot.api.entities.manager.NetworkManager;
import io.sponges.bot.api.event.events.channelmsg.ChannelMessageReceiveEvent;
import io.sponges.bot.api.module.Module;
import io.sponges.bot.modules.restmodule.Message;
import io.sponges.bot.modules.restmodule.RestModule;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Collection;

public class ListNetworksMessage extends Message {

    private final ClientManager clientManager;

    public ListNetworksMessage(Module module) {
        super("list_networks");
        this.clientManager = module.getClientManager();
    }

    @Override
    public String getResponse(ChannelMessageReceiveEvent event, String[] args) {
        JSONObject json = new JSONObject();
        if (args.length < 1) {
            return json.put("error", "Not enough arguments").toString();
        }
        String clientId = args[0].toLowerCase();
        if (!clientManager.isClient(clientId)) {
            return json.put("error", "Invalid client id").toString();
        }
        Client client = clientManager.getClient(clientId);
        NetworkManager manager = client.getNetworkManager();
        JSONArray array = new JSONArray();
        Collection<Network> networks = manager.getNetworks().values();
        if (networks.size() == 0) {
            return json.put("error", "Client has no loaded networks").toString();
        }
        boolean checkForUser = false;
        String user = null;
        if (args.length == 2) {
            checkForUser = true;
            user = args[1];
        }
        for (Network network : networks) {
            if (checkForUser) {
                if (!network.getUsers().containsKey(user)) {
                    continue;
                }
                if (!network.getUser(user).hasPermission(RestModule.DASHBOARD_MANAGE_PERMISSION)) {
                    continue;
                }
            }
            JSONObject object = new JSONObject();
            object.put("id", network.getId());
            array.put(object);
        }
        if (array.length() == 0) {
            return json.put("error", "User does not have permission or is not in any networks.").toString();
        }
        json.put("networks", array);
        return json.toString();
    }
}
