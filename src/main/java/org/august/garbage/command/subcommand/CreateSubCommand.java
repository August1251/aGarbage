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
            messageManager.sendMessage(player, "garbage-manager-create-you-have-not-finished-creating-the-garbage", null);
            return;
        }

        if (args.length < 2) {
            messageManager.sendMessage(player, "garbage-manager-create-you-need-to-enter-a-reload-time", null);
            return;
        }

        if (!isNumeric(args[1])) {
            messageManager.sendMessage(player, "garbage-manager-create-reload-time-you-enter-not-number", null);
            return;
        }

        messageManager.sendMessage(player, "garbage-manager-create-please-click-on-the-block", null);
        GarbageModel garbageModel = new GarbageModel();
        garbageModel.setReloadTime(Integer.parseInt(args[1]));
        garbageModel.setGarbageState(GarbageState.CREATE);
        garbageModel.setCreator(player.getName());
        garbageStorage.addGarbageModel(player, garbageModel);
    }

    @Override
    public String getCommandName() {
        return "create";
    }

    @Override
    public String getPermission() {
        return "garbagemanager.create";
    }

    private static boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
    }

}