package org.august.garbage.manager;

import org.august.garbage.configuration.MessagesConfiguration;
import org.august.garbage.dto.MessageDto;
import org.bukkit.entity.Player;

public class MessageManager {

    public static class Holder {
        public static final MessageManager INSTANCE = new MessageManager();
    }

    public static MessageManager getInstance() {
        return Holder.INSTANCE;
    }

    private final MessagesConfiguration messagesConfiguration = MessagesConfiguration.getInstance();

    public void sendMessage(Player player, String index) {
        MessageDto messageDto = messagesConfiguration.getMessage(index);

        if (!messageDto.isEnabled()) return;
        for (String message : messageDto.getMessage()) {
            player.sendMessage(message);
        }
    }

}