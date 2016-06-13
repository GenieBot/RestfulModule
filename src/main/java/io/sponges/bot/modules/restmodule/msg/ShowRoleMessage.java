package io.sponges.bot.modules.restmodule.msg;

import io.sponges.bot.api.entities.Client;
import io.sponges.bot.api.entities.Network;
import io.sponges.bot.api.entities.Role;
import io.sponges.bot.api.entities.User;
import io.sponges.bot.api.entities.data.UserData;
import io.sponges.bot.api.entities.manager.ClientManager;
import io.sponges.bot.api.entities.manager.NetworkManager;
import io.sponges.bot.api.entities.manager.RoleManager;
import io.sponges.bot.api.event.events.channelmsg.ChannelMessageReceiveEvent;
import io.sponges.bot.api.module.Module;
import io.sponges.bot.modules.restmodule.Message;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Collection;

public class ShowRoleMessage extends Message {

    // list_roles clientId networkId

    private final ClientManager clientManager;

    public ShowRoleMessage(Module module) {
        super("show_role");
        this.clientManager = module.getClientManager();
    }

    @Override
    public String getResponse(ChannelMessageReceiveEvent event, String[] args) {
        JSONObject json = new JSONObject();
        if (args.length < 3) {
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
        String roleId = args[2];
        Role role = null;
        for (Role r : roles) {
            String id = r.getId();
            if (roleId.equals(id)) {
                role = r;
                break;
            }
        }
        if (role == null) {
            return json.put("error", "Invalid role").toString();
        }
        JSONObject obj = new JSONObject()
                .put("id", role.getId())
                .put("permissions", new JSONArray(role.getPermissions().toArray()));
        if (roleManager.hasUsers(role)) {
            Collection<User> users = roleManager.getUsersWithRole(role);
            JSONArray array = new JSONArray();
            for (User user : users) {
                UserData data = user.getUserData();
                JSONObject object = new JSONObject().put("id", user.getId());
                if (data.getUsername().isPresent()) {
                    object.put("username", data.getUsername());
                }
                array.put(object);
            }
            obj.put("users", array);
        }
        json.put("role", obj);
        return json.toString();
    }
}
