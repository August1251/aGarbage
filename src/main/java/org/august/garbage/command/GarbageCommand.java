package org.august.garbage.command;

import org.august.garbage.manager.InventoryManager;
import org.august.garbage.manager.MessageManager;
import org.august.garbage.storage.InventoriesStorage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class GarbageCommand implements CommandExecutor {

    private final InventoriesStorage inventoriesStorage = InventoriesStorage.getInstance();
    private final MessageManager messageManager = MessageManager.getInstance();

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (commandSender instanceof Player) {
            InventoryManager inventoryManager = new InventoryManager();

            Player player = (Player) commandSender;
            messageManager.sendMessage(player, "garbage-command-open");

            inventoryManager.makeInventory("garbage");
            inventoryManager.addItems();
            inventoryManager.openInventory(player);

            inventoriesStorage.addInventory(player.getUniqueId(), inventoryManager);
            return true;
        }
        return false;
    }



}