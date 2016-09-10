package io.sponges.bot.modules.rest.route.user;

import io.sponges.bot.api.entities.User;
import io.sponges.bot.modules.rest.route.generic.GenericUserRoute;
import org.json.JSONObject;
import spark.Request;
import spark.Response;

public class GetUserRoute extends GenericUserRoute {

    public GetUserRoute() {
        super(Method.GET, "");
    }

    @Override
    protected void execute(Request request, Response response, JSONObject json, User user) {
        json.put("id", user.getId());
        json.put("op", user.isOp());
        json.put("admin", user.isPlatformAdmin());
    }
}
