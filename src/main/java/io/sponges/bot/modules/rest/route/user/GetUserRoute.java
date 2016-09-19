package io.sponges.bot.modules.rest.route.user;

import io.sponges.bot.api.entities.User;
import io.sponges.bot.api.entities.data.UserData;
import io.sponges.bot.modules.rest.RequestWrapper;
import io.sponges.bot.modules.rest.route.generic.GenericUserRoute;
import org.json.JSONObject;
import spark.Response;

import java.util.Optional;

public class GetUserRoute extends GenericUserRoute {

    public GetUserRoute() {
        super(Method.GET, "");
    }

    @Override
    protected JSONObject execute(RequestWrapper request, Response response, JSONObject json, User user) {
        json.put("id", user.getId());
        json.put("source_id", user.getSourceId());
        json.put("op", user.isOp());
        json.put("admin", user.isPlatformAdmin());
        UserData data = user.getUserData();
        Optional<String> username = data.getUsername();
        Optional<String> displayName = data.getDisplayName();
        Optional<String> status = data.getStatus();
        Optional<String> mood = data.getMood();
        Optional<String> profileUrl = data.getProfileUrl();
        Optional<String> profileImage = data.getProfileImage();
        if (username.isPresent()) json.put("username", username.get());
        if (displayName.isPresent()) json.put("displayName", displayName.get());
        if (status.isPresent()) json.put("status", status.get());
        if (mood.isPresent()) json.put("mood", mood.get());
        if (profileUrl.isPresent()) json.put("profileUrl", profileUrl.get());
        if (profileImage.isPresent()) json.put("profileImage", profileImage.get());
        return json;
    }
}
