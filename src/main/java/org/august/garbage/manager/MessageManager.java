package org.august.garbage.manager;

import org.august.garbage.aGarbage;
import org.august.garbage.configuration.MessagesConfiguration;
import org.august.garbage.dto.MessageDto;
import org.august.garbage.format.TextFormatter;
import org.august.garbage.model.GarbageModel;
import org.august.paper.PaperMessageHandler;
import org.august.spigot.SpigotMessageHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class MessageManager {

    public static class Holder {
        public static final MessageManager INSTANCE = new MessageManager();
    }

    public static MessageManager getInstance() {
        return Holder.INSTANCE;
    }

    private final MessagesConfiguration messagesConfiguration = MessagesConfiguration.getInstance();
    private final TextFormatter textFormatter = TextFormatter.getInstance();
    private static aGarbage garbage;

    public void sendMessage(Player player, String index, GarbageModel garbageModel) {
        MessageDto messageDto = messagesConfiguration.getMessage(index);

        if (!messageDto.isEnabled()) return;
        if (Bukkit.getVersion().split("-")[1].equals("Spigot")) {
            for (String message : messageDto.getMessage()) {
                SpigotMessageHandler.getInstance().sendMessage(player, textFormatter.getFormattedText(player, message, garbageModel));
            }
        } else {
            for (String message : messageDto.getMessage()) {
                PaperMessageHandler.getInstance().sendMessage(player, textFormatter.getFormattedText(player, message, garbageModel));
            }
        }
    }

    public static void setGarbage(aGarbage garbage) {
        MessageManager.garbage = garbage;
    }

}