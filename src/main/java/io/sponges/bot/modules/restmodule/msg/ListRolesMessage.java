package io.sponges.bot.modules.restmodule.msg;

import io.sponges.bot.api.entities.Client;
import io.sponges.bot.api.entities.Network;
import io.sponges.bot.api.entities.Role;
import io.sponges.bot.api.entities.manager.ClientManager;
import io.sponges.bot.api.entities.manager.NetworkManager;
import io.sponges.bot.api.entities.manager.RoleManager;
import io.sponges.bot.api.event.events.channelmsg.ChannelMessageReceiveEvent;
import io.sponges.bot.api.module.Module;
import io.sponges.bot.modules.restmodule.Message;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Collection;

public class ListRolesMessage extends Message {

    // list_roles clientId networkId

    private final ClientManager clientManager;

    public ListRolesMessage(Module module) {
        super("list_roles");
        this.clientManager = module.getClientManager();
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
        RoleManager roleManager = network.getRoleManager();
        Collection<Role> roles = roleManager.getRoles();
        if (roles.size() == 0) {
            return json.put("error", "Network does not have any roles").toString();
        }
        JSONArray array = new JSONArray();
        for (Role role : roles) {
            array.put(role.getId());
        }
        json.put("roles", array);
        return json.toString();
    }
}
