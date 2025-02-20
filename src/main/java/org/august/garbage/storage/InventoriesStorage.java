package org.august.garbage.storage;

import org.august.garbage.manager.InventoryManager;

import java.util.HashMap;
import java.util.UUID;

public class InventoriesStorage {

    public static class Holder {
        public static final InventoriesStorage INSTANCE = new InventoriesStorage();
    }

    public static InventoriesStorage getInstance() {
        return Holder.INSTANCE;
    }

    private static final HashMap<UUID, InventoryManager> inventories = new HashMap<>();
    private static final HashMap<UUID, InventoryManager> confirm = new HashMap<>();

    public void addInventory(UUID uuid, InventoryManager inventoryManager) {
        inventories.put(uuid, inventoryManager);
    }

    public void addConfirm(UUID uuid, InventoryManager inventoryManager) {
        confirm.put(uuid, inventoryManager);
    }

    public boolean existsInventory(UUID uuid) {
        return inventories.containsKey(uuid);
    }

    public boolean existsConfirm(UUID uuid) {
        return confirm.containsKey(uuid);
    }

    public void removeInventory(UUID uuid) {
        inventories.remove(uuid);
    }

    public void removeConfirm(UUID uuid) {
        confirm.remove(uuid);
    }

    public InventoryManager getInventory(UUID uuid) {
        return inventories.get(uuid);
    }

    public InventoryManager getConfirm(UUID uuid) {
        return confirm.get(uuid);
    }

}