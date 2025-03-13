package org.august.shade;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public abstract class MessageHandler {

    public abstract void sendMessage(Player player, String message);

    public void sendMessage(String message) {
        Bukkit.getLogger().info(message);
    }

}