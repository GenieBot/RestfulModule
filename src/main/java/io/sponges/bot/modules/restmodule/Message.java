package io.sponges.bot.modules.restmodule;

import io.sponges.bot.api.event.events.channelmsg.ChannelMessageReceiveEvent;

public abstract class Message {

    private final String name;

    public Message(String name) {
        this.name = name;
    }

    public abstract String getResponse(ChannelMessageReceiveEvent event, String[] args);

    public String getName() {
        return name;
    }
}
