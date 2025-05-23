package org.august.garbage.command;

import org.august.garbage.manager.InventoryManager;
import org.august.garbage.manager.MessageManager;
import org.august.garbage.storage.InventoriesStorage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class GarbageCommand extends Command {

    private final InventoriesStorage inventoriesStorage = InventoriesStorage.getInstance();
    private final MessageManager messageManager = MessageManager.getInstance();

    public GarbageCommand(@NotNull String name, @NotNull String description, @NotNull String usageMessage, @NotNull List<String> aliases) {
        super(name, description, usageMessage, aliases);
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if (sender instanceof Player) {
            InventoryManager inventoryManager = new InventoryManager();

            Player player = (Player) sender;
            if (!player.hasPermission(getName() + ".command")) {
                messageManager.sendMessage(player, "garbage-command-do-not-have-permission", null);
                return false;
            }

            messageManager.sendMessage(player, "garbage-command-open", null);

            inventoryManager.makeInventory(player, "garbage", null);
            inventoryManager.addItems(player, null);
            inventoryManager.openInventory(player);

            inventoriesStorage.addInventory(player.getUniqueId(), inventoryManager);
            return true;
        }
        return false;
    }
}