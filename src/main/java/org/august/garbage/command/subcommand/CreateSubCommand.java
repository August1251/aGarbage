package org.august.garbage.command.subcommand;

import org.august.garbage.command.SubCommand;
import org.august.garbage.manager.MessageManager;
import org.august.garbage.model.GarbageModel;
import org.august.garbage.storage.GarbageState;
import org.august.garbage.storage.GarbageStorage;
import org.bukkit.entity.Player;

public class CreateSubCommand extends SubCommand {

    private final GarbageStorage garbageStorage = GarbageStorage.getInstance();
    private final MessageManager messageManager = MessageManager.getInstance();

    @Override
    public void execute(Player player, String[] args) {
        if (garbageStorage.isGarbageExists(player)) {
            if (garbageStorage.getGarbageModel(player).getGarbageState() == GarbageState.CREATE || garbageStorage.getGarbageModel(player).getGarbageState() == GarbageState.REMOVE) {
                messageManager.sendMessage(player, "garbage-manager-create-you-have-not-finished-creating-the-garbage", null);
                return;
            }
        }

        messageManager.sendMessage(player, "garbage-manager-create-please-click-on-the-block", null);
        GarbageModel garbageModel = new GarbageModel();
        garbageModel.setGarbageState(GarbageState.CREATE);
        garbageModel.setCreator(player.getName());
        garbageModel.addPlayer(player);
        garbageStorage.addGarbage(garbageModel);
    }

    @Override
    public String getCommandName() {
        return "create";
    }

    @Override
    public String getPermission() {
        return "garbagemanager.create";
    }

}