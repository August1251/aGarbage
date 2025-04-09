package org.august.garbage.event;

import org.august.garbage.manager.InventoryManager;
import org.august.garbage.manager.MessageManager;
import org.august.garbage.model.GarbageModel;
import org.august.garbage.repository.GarbageRepository;
import org.august.garbage.storage.GarbageStorage;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

public class PlayerInteract implements Listener {

    private final GarbageRepository garbageRepository = GarbageRepository.getInstance();
    private final MessageManager messageManager = MessageManager.getInstance();
    private final GarbageStorage garbageStorage = GarbageStorage.getInstance();

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        String playerName = player.getName();
        if (!event.getHand().equals(EquipmentSlot.HAND)) return;
        if (event.getClickedBlock() == null) return;
        int x = event.getClickedBlock().getX();
        int y = event.getClickedBlock().getY();
        int z = event.getClickedBlock().getZ();
        GarbageModel garbageModel = garbageStorage.isGarbageExists(player) ? garbageStorage.getGarbageModel(player) : new GarbageModel(playerName, x, y, z);
        garbageModel.setX(x);
        garbageModel.setY(y);
        garbageModel.setZ(z);

        if (garbageStorage.isGarbageExists(garbageModel)) {
            if (garbageModel.getGarbageState() != null) {
                switch (garbageStorage.getGarbageModel(player).getGarbageState()) {
                    case CREATE -> {
                        if (garbageRepository.isGarbage(garbageModel)) {
                            messageManager.sendMessage(player, "garbage-manager-create-the-block-you-clicked-on-has-a-garbage-status", garbageModel);
                            event.setCancelled(true);
                            return;
                        }
                        garbageRepository.addGarbage(garbageModel);
                        messageManager.sendMessage(player, "garbage-manager-create-successfully", garbageModel);
                        garbageStorage.removeGarbageModel(player);
                        event.setCancelled(true);
                    }
                    case REMOVE -> {
                        if (!garbageRepository.isGarbage(garbageModel)) {
                            messageManager.sendMessage(player, "garbage-manager-remove-trash-does-not-exist-at-this-location", garbageModel);
                            event.setCancelled(true);
                            return;
                        }
                        garbageRepository.removeGarbage(garbageModel);
                        messageManager.sendMessage(player, "garbage-manager-remove-successfully", garbageModel);
                        garbageStorage.removeGarbageModel(player);
                        event.setCancelled(true);
                    }
                }
                return;
            }
        }

        if (garbageRepository.isGarbage(garbageModel) && !garbageStorage.isGarbageExists(garbageModel)) {
            InventoryManager inventoryManager = new InventoryManager();
            garbageModel.setInventoryManager(inventoryManager);
            garbageModel.setReloadTime(garbageRepository.getGarbage(garbageModel).getReloadTime());
            garbageStorage.addGarbageModel(player, garbageModel);
            inventoryManager.makeInventory(player, "trash", garbageModel);
            inventoryManager.addItems(player, garbageModel);
            inventoryManager.openInventory(player);
            event.setCancelled(true);
        } else if (garbageRepository.isGarbage(garbageModel) && garbageStorage.isGarbageExists(garbageModel)) {
            InventoryManager inventoryManager = garbageStorage.getGarbageModel(player).getInventoryManager();
            inventoryManager.openInventory(player);
            event.setCancelled(true);
        }
    }

}