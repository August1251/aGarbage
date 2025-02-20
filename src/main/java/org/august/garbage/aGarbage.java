package org.august.garbage;

import org.august.garbage.command.GarbageCommand;
import org.august.garbage.configuration.InventoriesConfiguration;
import org.august.garbage.configuration.MessagesConfiguration;
import org.august.garbage.event.InventoryClick;
import org.august.garbage.event.InventoryClose;
import org.august.garbage.manager.InventoryManager;
import org.august.garbage.repository.GarbageRepository;
import org.bukkit.plugin.java.JavaPlugin;

public final class aGarbage extends JavaPlugin {

    @Override
    public void onEnable() {
        configure();
        registerCommands();
        registerEvents();
    }

    @Override
    public void onDisable() {

    }

    private void registerCommands() {
        getCommand("garbage").setExecutor(new GarbageCommand());
    }

    private void configure() {
        boolean folder = getDataFolder().mkdir();

        InventoriesConfiguration inventoriesConfiguration = InventoriesConfiguration.getInstance();
        MessagesConfiguration messagesConfiguration = MessagesConfiguration.getInstance();
        GarbageRepository garbageRepository = GarbageRepository.getInstance();
        inventoriesConfiguration.setGarbage(this);
        messagesConfiguration.setGarbage(this);
        garbageRepository.setGarbage(this);
        InventoryManager.setGarbage(this);

        if (!folder) garbageRepository.makeFile();
        garbageRepository.load();
    }

    private void registerEvents() {
        getServer().getPluginManager().registerEvents(new InventoryClick(), this);
        getServer().getPluginManager().registerEvents(new InventoryClose(this), this);
    }

}
