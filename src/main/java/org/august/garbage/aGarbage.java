package org.august.garbage;

import org.august.garbage.configuration.InventoriesConfiguration;
import org.august.garbage.configuration.MessagesConfiguration;
import org.august.garbage.manager.InventoryManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class aGarbage extends JavaPlugin {

    @Override
    public void onEnable() {
        configure();
    }

    @Override
    public void onDisable() {

    }

    private void configure() {
        InventoriesConfiguration inventoriesConfiguration = InventoriesConfiguration.getInstance();
        MessagesConfiguration messagesConfiguration = MessagesConfiguration.getInstance();
        inventoriesConfiguration.setGarbage(this);
        messagesConfiguration.setGarbage(this);
        InventoryManager.setGarbage(this);
    }

}
