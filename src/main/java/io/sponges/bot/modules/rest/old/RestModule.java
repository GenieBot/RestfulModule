package io.sponges.bot.modules.rest.old;

public class RestModule {

    public static final String DASHBOARD_MANAGE_PERMISSION = "dashboard.manage";

    /*private final Map<String, Message> messages = new HashMap<>();

    public RestModule() {
        super("REST", "1.01");
    }

    @Override
    public void onEnable() {
        register(
                new TestMessage(),
                new ListClientsMessage(this),
                new ListModulesMessage(this),
                new ListCommandsMessage(this),
                new ListNetworksMessage(this),
                new ListUsersMessage(this),
                new StatisticsMessage(this),
                new NetworkModulesMessage(this),
                new ModifyModuleMessage(this)
        );

        this.getEventManager().register(this, ChannelMessageReceiveEvent.class, event -> {
            String message = event.getMessage();
            String[] split = message.split(" ");
            message = split[0];
            String[] args = new String[split.length - 1];
            for (int i = 1; i < split.length; i++) {
                args[i - 1] = split[i];
            }
            if (messages.containsKey(message)) {
                event.reply(messages.get(message).getResponse(event, args));
            }
        });
    }

    @Override
    public void onDisable() {

    }

    private void register(Message... messages) {
        for (Message message : messages) {
            this.messages.put(message.getName(), message);
        }
    }*/
}
