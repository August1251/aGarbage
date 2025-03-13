package org.august.shade;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public abstract class InventoryHandler {

    private Inventory inventory;

    public abstract void makeInventory(int size, String title);
    public abstract void addItem(int slot, ItemStack itemStack, String displayName, List<String> lore);

    public void openInventory(Player player) {
        player.openInventory(inventory);
    }

    public void closeInventory(Player player) {
        player.closeInventory();
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public Inventory getInventory() {
        return inventory;
    }

}