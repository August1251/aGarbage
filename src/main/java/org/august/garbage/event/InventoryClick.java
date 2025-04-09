package org.august.garbage.event;

import org.august.garbage.dto.ItemDto;
import org.august.garbage.manager.InventoryManager;
import org.august.garbage.storage.GarbageStorage;
import org.august.garbage.storage.InventoriesStorage;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.UUID;

public class InventoryClick implements Listener {

    private final InventoriesStorage inventoriesStorage = InventoriesStorage.getInstance();
    private final GarbageStorage garbageStorage = GarbageStorage.getInstance();

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Inventory inventory = event.getView().getTopInventory();
        UUID uuid = player.getUniqueId();

        InventoryManager inventoryManager = inventoriesStorage.existsConfirm(uuid) ? inventoriesStorage.getConfirm(uuid) : inventoriesStorage.getInventory(uuid);
        if (garbageStorage.isGarbageExists(player)) {
            inventoryManager = garbageStorage.getGarbageModel(player).getInventoryManager();
        } else {
            return;
        }

        if (inventoryManager.existsItemAtSlot(event.getSlot())) {
            ItemDto itemDto = inventoryManager.getItem(event.getSlot());
            if (!event.getClickedInventory().equals(inventoryManager.getInventory())) return;
            switch (itemDto.getItemType()) {
                case THROW -> {
                    if (inventoriesStorage.existsConfirm(uuid)) return;
                    inventoryManager.setInventory(inventory);

                    InventoryManager confirmInventory = new InventoryManager();
                    confirmInventory.makeInventory(player, "confirm", null);
                    confirmInventory.addItems(player, null);
                    confirmInventory.closeInventory(player);

                    inventoriesStorage.addConfirm(uuid, confirmInventory);
                    inventoriesStorage.addInventory(uuid, inventoryManager);

                    confirmInventory.openInventory(player);

                    event.setCancelled(true);
                }
                case ACCEPT -> {
                    if (!inventoriesStorage.existsConfirm(uuid)) return;

                    inventoriesStorage.removeInventory(uuid);
                    inventoriesStorage.removeConfirm(uuid);

                    player.closeInventory();
                    event.setCancelled(true);
                }
                case CANCEL -> {
                    if (!inventoriesStorage.existsConfirm(uuid)) return;
                    InventoryManager garbageInventory = inventoriesStorage.getInventory(uuid);
                    garbageInventory.closeInventory(player);
                    inventoriesStorage.addInventory(uuid, garbageInventory);
                    inventoriesStorage.removeConfirm(uuid);
                    garbageInventory.openInventory(player);

                    event.setCancelled(true);
                }
                case DECORATE -> event.setCancelled(true);
            }
        }

    }

}