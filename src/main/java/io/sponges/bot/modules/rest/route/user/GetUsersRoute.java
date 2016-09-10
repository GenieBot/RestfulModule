package io.sponges.bot.modules.rest.route.user;

import io.sponges.bot.api.entities.Network;
import io.sponges.bot.api.entities.User;
import io.sponges.bot.api.entities.manager.UserManager;
import io.sponges.bot.modules.rest.route.generic.GenericNetworkRoute;
import org.json.JSONArray;
import org.json.JSONObject;
import spark.Request;
import spark.Response;

import java.util.Collection;

public class GetUsersRoute extends GenericNetworkRoute {

    public GetUsersRoute() {
        super(Method.GET, "/users");
    }

    @Override
    protected void execute(Request request, Response response, JSONObject json, Network network) {
        UserManager userManager = network.getUserManager();
        Collection<User> users = userManager.getUsers().values();
        JSONArray array = new JSONArray();
        users.forEach(user -> array.put(user.getId()));
        json.put("users", array);
        json.put("size", users.size());
    }
}
