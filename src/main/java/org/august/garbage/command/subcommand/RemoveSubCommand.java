package org.august.garbage.command.subcommand;

import org.august.garbage.command.SubCommand;
import org.august.garbage.manager.MessageManager;
import org.august.garbage.model.GarbageModel;
import org.august.garbage.storage.GarbageState;
import org.august.garbage.storage.GarbageStorage;
import org.bukkit.entity.Player;

public class RemoveSubCommand extends SubCommand {

    private final GarbageStorage garbageStorage = GarbageStorage.getInstance();
    private final MessageManager messageManager = MessageManager.getInstance();

    @Override
    public void execute(Player player, String[] args) {
        messageManager.sendMessage(player, "garbage-manager-remove-click-on-the-trash", null);
        GarbageModel garbageModel = new GarbageModel();
        garbageModel.setGarbageState(GarbageState.REMOVE);
        garbageStorage.addGarbage(garbageModel);
    }

    @Override
    public String getCommandName() {
        return "remove";
    }

    @Override
    public String getPermission() {
        return "garbagemanager.remove";
    }

}