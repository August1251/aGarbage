package org.august.garbage.manager;

import org.august.garbage.aGarbage;
import org.august.garbage.configuration.InventoriesConfiguration;
import org.august.garbage.dto.InventoryDto;
import org.august.garbage.dto.ItemDto;
import org.august.garbage.format.TextFormatter;
import org.august.garbage.model.GarbageModel;
import org.august.paper.PaperInventoryHandler;
import org.august.shade.InventoryHandler;
import org.august.spigot.SpigotInventoryHandler;
import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InventoryManager {

    private final InventoriesConfiguration inventoriesConfiguration = InventoriesConfiguration.getInstance();
    private final TextFormatter textFormatter = TextFormatter.getInstance();
    private InventoryHandler inventoryHandler;
    private InventoryDto inventoryDto;
    private static aGarbage garbage;
    private Inventory inventory;
    private List<ItemDto> items;

    public void makeInventory(Player player, String index, GarbageModel garbageModel) {
        inventoryDto = inventoriesConfiguration.getInventory(index);
        if (Bukkit.getVersion().split("-")[1].equals("Spigot")) {
            inventoryHandler = new SpigotInventoryHandler();
            inventoryHandler.makeInventory(inventoryDto.getSize(), textFormatter.getFormattedText(player, inventoryDto.getTitle(), garbageModel));
        } else {
            inventoryHandler = new PaperInventoryHandler();
            inventoryHandler.makeInventory(inventoryDto.getSize(), textFormatter.getFormattedText(player, inventoryDto.getTitle(), garbageModel));
        }
        items = inventoryDto.getItems();
    }

    public void addItems(Player player, GarbageModel garbageModel) {
        for (ItemDto itemDto : items) {
            ItemStack itemStack = new ItemStack(itemDto.getMaterial());
            itemStack.setAmount(itemDto.getAmount());

            ItemMeta itemMeta = itemStack.getItemMeta();
            if (!itemDto.getName().isBlank()) {
                itemMeta.setDisplayName(textFormatter.getFormattedText(player, itemDto.getName(), garbageModel));
            } else {
                itemMeta.setDisplayName(" ");
            }

            HashMap<Integer, Enchantment> enchantments = itemDto.getEnchantments();
            List<String> lore = new ArrayList<>();
            for (int i : enchantments.keySet()){
                itemStack.addEnchantment(enchantments.get(i), i);
            }
            for (ItemFlag itemFlag : itemDto.getItemFlags()) {
                itemMeta.addItemFlags(itemFlag);
            }
            for (String i : itemDto.getLore()) {
                lore.add(textFormatter.getFormattedText(player, i, garbageModel));
            }

            itemStack.setItemMeta(itemMeta);

            inventoryHandler.addItem(itemDto.getSlot(), itemStack, itemMeta.getDisplayName(), lore);
        }
    }

    public void updateInventory(GarbageModel garbageModel) {
        for (int i = 0; i < inventoryDto.getSize(); i++) {
            if (existsItemAtSlot(i)) {
                List<String> lores = new ArrayList<>();
                for (String j : getItem(i).getLore()) {
                    lores.add(textFormatter.getFormattedText(null, j, garbageModel));
                }
                inventoryHandler.updateItem(i, textFormatter.getFormattedText(null, getItem(i).getName(), garbageModel), lores);
            }
        }
    }

    public void resetInventory() {
        for (int i = 0; i < inventoryDto.getSize(); i++) {
            if (!existsItemAtSlot(i)) inventoryHandler.getInventory().remove(inventoryHandler.getInventory().getItem(i));
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

    public void closeInventory(Player player) {
        new BukkitRunnable() {
            @Override
            public void run() {
                inventoryHandler.closeInventory(player);
            }
        }.runTaskLater(garbage, 1L);
    }

    public void openInventory(Player player) {
        new BukkitRunnable() {
            @Override
            public void run() {
                inventoryHandler.openInventory(player);
            }
        }.runTaskLater(garbage, 1L);
    }

    public InventoryDto getInventoryDto() {
        return inventoryDto;
    }

    public Inventory getInventory() {
        return inventoryHandler.getInventory();
    }

    public void setInventory(Inventory inventory) {
        inventoryHandler.setInventory(inventory);
    }

    public static void setGarbage(aGarbage garbage) {
        InventoryManager.garbage = garbage;
    }

}