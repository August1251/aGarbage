package org.august.spigot;

import org.august.shade.MessageHandler;
import org.august.spigot.format.ColorFormatter;
import org.bukkit.entity.Player;

public class SpigotMessageHandler extends MessageHandler {

    public static class Holder {
        public static final SpigotMessageHandler INSTANCE = new SpigotMessageHandler();
    }

    public static SpigotMessageHandler getInstance() {
        return Holder.INSTANCE;
    }

    private final ColorFormatter colorFormatter = ColorFormatter.getInstance();

    @Override
    public void sendMessage(Player player, String message) {
        player.sendMessage(colorFormatter.getColoredText(message));
    }

}