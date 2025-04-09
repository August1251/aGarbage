package org.august.garbage;

import org.august.garbage.command.GarbageCommand;
import org.august.garbage.command.GarbageManagerCommand;
import org.august.garbage.command.subcommand.CancelSubCommand;
import org.august.garbage.command.subcommand.CreateSubCommand;
import org.august.garbage.command.subcommand.HelpSubCommand;
import org.august.garbage.command.subcommand.RemoveSubCommand;
import org.august.garbage.configuration.CommandsConfiguration;
import org.august.garbage.configuration.InventoriesConfiguration;
import org.august.garbage.configuration.MessagesConfiguration;
import org.august.garbage.dto.CommandDto;
import org.august.garbage.event.InventoryClick;
import org.august.garbage.event.InventoryClose;
import org.august.garbage.event.PlayerInteract;
import org.august.garbage.format.TextFormatter;
import org.august.garbage.manager.InventoryManager;
import org.august.garbage.manager.MessageManager;
import org.august.garbage.repository.GarbageRepository;
import org.august.garbage.task.GarbageTask;
import org.august.garbage.utility.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;

public final class aGarbage extends JavaPlugin {

    @Override
    public void onEnable() {
        configure();
        registerCommands();
        registerEvents();

        Metrics metrics = new Metrics(this, 24996);
        new GarbageTask().runTaskTimer(this, 0, 20);
    }

    @Override
    public void onDisable() {

    }

    private void registerCommands() {
        GarbageManagerCommand garbageManagerCommand = getGarbageManagerCommand();

        getCommand("garbagemanager").setExecutor(garbageManagerCommand);

        CommandsConfiguration commandsConfiguration = CommandsConfiguration.getInstance();

        for (String command : commandsConfiguration.getCommands()) {
            CommandDto commandDto = commandsConfiguration.getCommand(command);
            try {
                Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");
                bukkitCommandMap.setAccessible(true);
                CommandMap commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());
                commandMap.register("aGarbage", new GarbageCommand(commandDto.getName(), commandDto.getDescription(), commandDto.getUsage(), commandDto.getAliases()));
            } catch (Exception exception) {
                throw new RuntimeException(exception);
            }
        }
    }

    @NotNull
    private GarbageManagerCommand getGarbageManagerCommand() {
        GarbageManagerCommand garbageManagerCommand = new GarbageManagerCommand();

        CreateSubCommand createSubCommand = new CreateSubCommand();
        RemoveSubCommand removeSubCommand = new RemoveSubCommand();
        CancelSubCommand cancelSubCommand = new CancelSubCommand();
        HelpSubCommand helpSubCommand = new HelpSubCommand();
        garbageManagerCommand.registerCommand(createSubCommand.getCommandName(), createSubCommand);
        garbageManagerCommand.registerCommand(removeSubCommand.getCommandName(), removeSubCommand);
        garbageManagerCommand.registerCommand(cancelSubCommand.getCommandName(), cancelSubCommand);
        garbageManagerCommand.registerCommand(helpSubCommand.getCommandName(), helpSubCommand);

        return garbageManagerCommand;
    }

    private void configure() {
        getDataFolder().mkdir();
        saveDefaultConfig();

        InventoriesConfiguration inventoriesConfiguration = InventoriesConfiguration.getInstance();
        MessagesConfiguration messagesConfiguration = MessagesConfiguration.getInstance();
        CommandsConfiguration commandsConfiguration = CommandsConfiguration.getInstance();
        GarbageRepository garbageRepository = GarbageRepository.getInstance();
        inventoriesConfiguration.setGarbage(this);
        messagesConfiguration.setGarbage(this);
        commandsConfiguration.setGarbage(this);
        garbageRepository.setGarbage(this);
        InventoryManager.setGarbage(this);
        MessageManager.setGarbage(this);
        TextFormatter.setGarbage(this);

        garbageRepository.makeFile();
        garbageRepository.load();
    }

    private void registerEvents() {
        getServer().getPluginManager().registerEvents(new PlayerInteract(), this);
        getServer().getPluginManager().registerEvents(new InventoryClick(), this);
        getServer().getPluginManager().registerEvents(new InventoryClose(this), this);
    }

}
