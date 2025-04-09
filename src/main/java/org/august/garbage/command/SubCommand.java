package org.august.garbage.command;

import org.bukkit.entity.Player;

public abstract class SubCommand {

    public abstract void execute(Player player, String[] args);
    public abstract String getCommandName();
    public abstract String getPermission();

}