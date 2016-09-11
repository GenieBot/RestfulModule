package io.sponges.bot.modules.rest.route.user;

import io.sponges.bot.api.entities.User;
import io.sponges.bot.modules.rest.RequestWrapper;
import io.sponges.bot.modules.rest.route.generic.GenericUserRoute;
import org.json.JSONObject;
import spark.Response;

public class GetUserDataRoute extends GenericUserRoute {

    public GetUserDataRoute() {
        super(Method.GET, "/data");
    }

    @Override
    protected JSONObject execute(RequestWrapper request, Response response, JSONObject json, User user) {
        JSONObject object = new JSONObject(module.getStorage().serialize(user.getData()));
        object.keySet().forEach(key -> {
            Object obj = object.get(key);
            json.put(key, obj);
        });
        return json;
    }
}
