package io.sponges.bot.modules.rest.old.msg;

import io.sponges.bot.api.entities.Client;
import io.sponges.bot.api.entities.Network;
import io.sponges.bot.api.entities.manager.ClientManager;
import io.sponges.bot.api.entities.manager.NetworkManager;
import io.sponges.bot.api.event.events.channelmsg.ChannelMessageReceiveEvent;
import io.sponges.bot.api.module.Module;
import io.sponges.bot.modules.rest.old.Message;
import org.json.JSONArray;
import org.json.JSONObject;

public class ListUsersMessage extends Message {

    // list_users client network

    private final Module module;

    public ListUsersMessage(Module module) {
        super("list_users");
        this.module = module;
    }

    @Override
    public String getResponse(ChannelMessageReceiveEvent event, String[] args) {
        JSONObject json = new JSONObject();
        if (args.length < 2) {
            return json.put("error", "Not enough arguments").toString();
        }
        ClientManager clientManager = module.getClientManager();
        String clientId = args[0].toLowerCase();
        if (!clientManager.isClient(clientId)) {
            return json.put("error", "Invalid client id").toString();
        }
        Client client = clientManager.getClient(clientId);
        NetworkManager networkManager = client.getNetworkManager();
        Network network = networkManager.loadNetworkSync(args[1]);
        if (network == null) {
            return json.put("error", "Invalid network id").toString();
        }
        System.out.println("Got network " + network.getId());
        System.out.println("Sending msg...");
        String response = client.sendMessageSync("list_users " + network.getId());
        System.out.println("Sent msg... " + response);
        JSONArray array = new JSONArray(response); // NPE here
        System.out.println("dam");
        json.put("users", array);
        System.out.println("ok " + json.toString());
        return json.toString();
    }

}
