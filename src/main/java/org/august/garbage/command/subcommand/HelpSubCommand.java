package org.august.garbage.command.subcommand;

import org.august.garbage.command.SubCommand;
import org.august.garbage.manager.MessageManager;
import org.bukkit.entity.Player;

public class HelpSubCommand extends SubCommand {

    private final MessageManager messageManager = MessageManager.getInstance();

    @Override
    public void execute(Player player, String[] args) {
        messageManager.sendMessage(player, "garbage-manager-help", null);
    }

    @Override
    public String getCommandName() {
        return "help";
    }

    @Override
    public String getPermission() {
        return "garbagemanager.help";
    }

}