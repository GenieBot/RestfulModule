package io.sponges.bot.modules.rest.old.msg;

import io.sponges.bot.api.event.events.channelmsg.ChannelMessageReceiveEvent;
import io.sponges.bot.api.module.Module;
import io.sponges.bot.modules.rest.old.Message;
import org.json.JSONObject;

public class StatisticsMessage extends Message {

    private static final long START_TIME = System.currentTimeMillis();

    private final Module module;

    public StatisticsMessage(Module module) {
        super("statistics");
        this.module = module;
    }

    @Override
    public String getResponse(ChannelMessageReceiveEvent event, String[] args) {
        JSONObject json = new JSONObject();
        long uptime = System.currentTimeMillis() - START_TIME;
        json.put("uptime", uptime);
        return new JSONObject().put("statistics", json).toString();
    }
}
