package org.august.garbage.event;

import org.august.garbage.aGarbage;
import org.august.garbage.dto.InventoryDto;
import org.august.garbage.manager.InventoryManager;
import org.august.garbage.model.GarbageModel;
import org.august.garbage.storage.GarbageState;
import org.august.garbage.storage.GarbageStorage;
import org.august.garbage.storage.InventoriesStorage;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class InventoryClose implements Listener {

    private final InventoriesStorage inventoriesStorage = InventoriesStorage.getInstance();
    private final GarbageStorage garbageStorage = GarbageStorage.getInstance();
    private final aGarbage garbage;

    public InventoryClose(aGarbage garbage) {
        this.garbage = garbage;
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        Inventory inventory = player.getOpenInventory().getTopInventory();
        UUID uuid = player.getUniqueId();

        if (garbageStorage.isGarbageExists(player)) {
            if (garbageStorage.getGarbageModel(player).getGarbageState().equals(GarbageState.OPEN)) {
                GarbageModel garbageModel = garbageStorage.getGarbageModel(player);
                garbageModel.setGarbageState(GarbageState.CLOSE);
                garbageModel.removePlayer(player);
                garbageStorage.addGarbage(garbageModel);
            }
        }

        if (!inventoriesStorage.existsInventory(uuid)) {
            return;
        }

        if (inventoriesStorage.existsInventory(uuid) && !inventoriesStorage.existsConfirm(uuid)) {
            if (!inventoriesStorage.getInventory(uuid).getInventory().equals(inventory)) return;
            InventoryManager garbageInventory = inventoriesStorage.getInventory(uuid);
            InventoryDto inventoryDto = garbageInventory.getInventoryDto();

            int[] garbageSlots = new int[(inventoryDto.getSize())];
            int garbageSlotCount = 0;
            int[] inventorySlots = new int[36];
            int inventorySlotsCount = 0;

            for (int i = 0; i < inventoryDto.getSize(); i++) {
                if (garbageInventory.getItem(i) == null) garbageSlots[garbageSlotCount++] = i;
            }

            for (int i = 0; i < garbageSlotCount; i++) {
                ItemStack itemStack = garbageInventory.getInventory().getItem(garbageSlots[i]);

                if (itemStack != null) {
                    if (player.getInventory().getItem(garbageSlots[i]) == null) {
                        inventorySlots[inventorySlotsCount++] = i;
                    }
                }
            }

            for (int i = 0; i < garbageSlotCount; i++) {
                ItemStack itemStack = garbageInventory.getInventory().getItem(garbageSlots[i]);

                if (itemStack != null) {
                    if (player.getInventory().getItem(inventorySlots[i]) == null) {
                        player.getInventory().addItem(itemStack);
                    } else {
                        player.getWorld().dropItem(player.getLocation(), itemStack);
                    }
                }
            }

            inventoriesStorage.removeInventory(uuid);

        }

        if (inventoriesStorage.existsConfirm(uuid)) {
            if (!inventoriesStorage.getConfirm(uuid).getInventory().equals(inventory)) return;
            InventoryManager garbageInventory = inventoriesStorage.getInventory(uuid);
            garbageInventory.openInventory(player);
            new BukkitRunnable() {
                @Override
                public void run() {
                    inventoriesStorage.addInventory(uuid, garbageInventory);
                    inventoriesStorage.removeConfirm(uuid);
                }
            }.runTaskLater(garbage, 1L);
        }
    }
}