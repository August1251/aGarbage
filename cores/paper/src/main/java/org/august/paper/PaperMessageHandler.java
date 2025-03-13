package org.august.paper;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.august.shade.MessageHandler;
import org.bukkit.entity.Player;

public class PaperMessageHandler extends MessageHandler {

    public static class Holder {
        public static final PaperMessageHandler INSTANCE = new PaperMessageHandler();
    }

    public static PaperMessageHandler getInstance() {
        return Holder.INSTANCE;
    }

    @Override
    public void sendMessage(Player player, String message) {
        player.sendMessage(MiniMessage.miniMessage().deserialize(message));
    }

}