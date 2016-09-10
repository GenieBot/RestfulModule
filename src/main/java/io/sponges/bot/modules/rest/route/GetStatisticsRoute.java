package io.sponges.bot.modules.rest.route;

import io.sponges.bot.api.cmd.CommandManager;
import io.sponges.bot.api.entities.Client;
import io.sponges.bot.api.entities.Network;
import io.sponges.bot.api.entities.manager.ChannelManager;
import io.sponges.bot.api.entities.manager.ClientManager;
import io.sponges.bot.api.entities.manager.NetworkManager;
import io.sponges.bot.api.entities.manager.UserManager;
import io.sponges.bot.api.module.ModuleManager;
import io.sponges.bot.api.webhook.WebhookManager;
import io.sponges.bot.modules.rest.Route;
import org.json.JSONObject;
import spark.Request;
import spark.Response;

import java.util.Collection;

public class GetStatisticsRoute extends Route {

    private static final long START_TIME = System.currentTimeMillis();

    private final ModuleManager moduleManager = module.getModuleManager();
    private final CommandManager commandManager = module.getCommandManager();
    private final ClientManager clientManager = module.getClientManager();
    private final WebhookManager webhookManager = module.getWebhookManager();

    public GetStatisticsRoute() {
        super(Method.GET, "/statistics");
    }

    @Override
    protected JSONObject execute(Request request, Response response, JSONObject json) {
        long uptime = System.currentTimeMillis() - START_TIME;
        int modules = moduleManager.getModules().size();
        int commands = commandManager.getCommands().size();
        int clients = clientManager.getClients().size();
        int networks = 0;
        int channels = 0;
        int users = 0;
        {
            Collection<Client> clientsCollection = clientManager.getClients().values();
            for (Client client : clientsCollection) {
                NetworkManager networkManager = client.getNetworkManager();
                Collection<Network> networksCollection = networkManager.getNetworks().values();
                networks += networksCollection.size();
                for (Network network : networksCollection) {
                    ChannelManager channelManager = network.getChannelManager();
                    channels += channelManager.getChannels().size();
                    UserManager userManager = network.getUserManager();
                    users += userManager.getUsers().size();
                }
            }
        }
        int webhooks = webhookManager.getWebhooks().size();
        json.put("uptime", uptime)
                .put("modules", modules)
                .put("commands", commands)
                .put("clients", clients)
                .put("networks", networks)
                .put("channels", channels)
                .put("users", users)
                .put("webhooks", webhooks);
        return json;
    }
}
