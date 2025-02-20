package org.august.garbage.manager;

import org.august.garbage.aGarbage;
import org.august.garbage.configuration.InventoriesConfiguration;
import org.august.garbage.dto.InventoryDto;
import org.august.garbage.dto.ItemDto;
import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.List;

public class InventoryManager {

    private final InventoriesConfiguration inventoriesConfiguration = InventoriesConfiguration.getInstance();
    private InventoryDto inventoryDto;
    private static aGarbage garbage;
    private Inventory inventory;
    private List<ItemDto> items;

    public void makeInventory(String index) {
        inventoryDto = inventoriesConfiguration.getInventory(index);
        inventory = Bukkit.createInventory(null, inventoryDto.getSize(), inventoryDto.getTitle());
        items = inventoryDto.getItems();
    }

    public void addItems() {
        for (ItemDto itemDto : items) {
            ItemStack itemStack = new ItemStack(itemDto.getMaterial());
            itemStack.setAmount(itemDto.getAmount());

            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName(itemDto.getName());
            if (itemDto.getName().isEmpty()) itemMeta.setDisplayName(" ");
            if (!itemDto.getLore().getFirst().isEmpty()) itemMeta.setLore(itemDto.getLore());

            HashMap<Integer, Enchantment> enchantments = itemDto.getEnchantments();
            for (int i : enchantments.keySet()){
                itemStack.addEnchantment(enchantments.get(i), i);
            }
            for (ItemFlag itemFlag : itemDto.getItemFlags()) {
                itemMeta.addItemFlags(itemFlag);
            }

            itemStack.setItemMeta(itemMeta);

            inventory.setItem(itemDto.getSlot(), itemStack);
        }
    }

    public boolean existsItemAtSlot(int slot) {
        for (ItemDto itemDto : items) {
            if (itemDto.getSlot() == slot) return true;
        }
        return false;
    }

    public ItemDto getItem(int slot) {
        for (ItemDto itemDto : items) {
            if (itemDto.getSlot() == slot) return itemDto;
        }
        return null;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public void closeInventory(Player player) {
        new BukkitRunnable() {
            @Override
            public void run() {
                player.closeInventory();
            }
        }.runTaskLater(garbage, 1L);
    }

    public void openInventory(Player player) {
        new BukkitRunnable() {
            @Override
            public void run() {
                player.openInventory(inventory);
            }
        }.runTaskLater(garbage, 1L);
    }

    public InventoryDto getInventoryDto() {
        return inventoryDto;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public static void setGarbage(aGarbage garbage) {
        InventoryManager.garbage = garbage;
    }

}