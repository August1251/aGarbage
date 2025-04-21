package org.august.garbage.command.subcommand;

import org.august.garbage.command.SubCommand;
import org.august.garbage.manager.MessageManager;
import org.august.garbage.storage.GarbageStorage;
import org.bukkit.entity.Player;

public class CancelSubCommand extends SubCommand {

    private final GarbageStorage garbageStorage = GarbageStorage.getInstance();
    private final MessageManager messageManager = MessageManager.getInstance();

    @Override
    public void execute(Player player, String[] args) {
        if (!garbageStorage.isGarbageExists(player)) {
            messageManager.sendMessage(player, "garbage-manager-cancel-there-is-nothing-to-cancel", null);
            return;
        }
        messageManager.sendMessage(player, "garbage-manager-cancel-successfully", null);
        garbageStorage.getGarbageModel(player).removePlayer(player);
    }

    @Override
    public String getCommandName() {
        return "cancel";
    }

    @Override
    public String getPermission() {
        return "garbagemanager.cancel";
    }

}