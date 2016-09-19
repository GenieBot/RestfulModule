package io.sponges.bot.modules.rest;

import io.sponges.bot.api.module.Module;

public class RestModule extends Module {

    static final String API_VERSION = "1";
    static final String API_ROOT = "/api/v1";

    public RestModule() {
        super("Rest", "1.0", true);
    }

    @Override
    public void onEnable() {
        new Routes(this);
    }

    @Override
    public void onDisable() {

    }
}
