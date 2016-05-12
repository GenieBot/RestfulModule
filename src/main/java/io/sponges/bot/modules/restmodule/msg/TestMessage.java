package io.sponges.bot.modules.restmodule.msg;

import io.sponges.bot.api.event.events.channelmsg.ChannelMessageReceiveEvent;
import io.sponges.bot.modules.restmodule.Message;
import org.json.JSONObject;

public class TestMessage extends Message {

    public TestMessage() {
        super("test");
    }

    @Override
    public String getResponse(ChannelMessageReceiveEvent event, String[] args) {
        return new JSONObject().put("response", "Testing, 1 2 3").toString();
    }
}
