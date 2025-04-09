package org.august.garbage.command;

import org.august.garbage.manager.MessageManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GarbageManagerCommand implements CommandExecutor, TabCompleter {

    private static final HashMap<String, SubCommand> subCommands = new HashMap<>();
    private final MessageManager messageManager = MessageManager.getInstance();

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!subCommands.containsKey(strings[0])) {
            return false;
        }

        Player player = (Player) commandSender;
        SubCommand subCommand = subCommands.get(strings[0]);

        if (!player.hasPermission(subCommand.getPermission())) {
            messageManager.sendMessage(player, "garbage-command-do-not-have-permission", null);
            return false;
        }

        subCommand.execute(player, strings);

        return true;
    }

    public void registerCommand(String name, SubCommand subCommand) {
        subCommands.put(name, subCommand);
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (strings.length == 1) {
            return new ArrayList<>(subCommands.keySet());
        }
        return List.of();
    }
}