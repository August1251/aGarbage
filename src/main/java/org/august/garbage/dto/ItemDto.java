package org.august.garbage.dto;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class ItemDto {

    private final int slot;
    private final ItemType itemType;
    private final Material material;
    private final int amount;
    private final String name;
    private final List<String> lore;
    private final HashMap<Integer, Enchantment> enchantments;
    private final Set<ItemFlag> itemFlags;

    public ItemDto(int slot, ItemType itemType, Material material, int amount, String name, List<String> lore, HashMap<Integer, Enchantment> enchantments, Set<ItemFlag> itemFlags) {
        this.slot = slot;
        this.itemType = itemType;
        this.material = material;
        this.amount = amount;
        this.name = name;
        this.lore = lore;
        this.enchantments = enchantments;
        this.itemFlags = itemFlags;
    }

    public int getSlot() {
        return slot;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public Material getMaterial() {
        return material;
    }

    public int getAmount() {
        return amount;
    }

    public String getName() {
        return name;
    }

    public List<String> getLore() {
        return lore;
    }

    public HashMap<Integer, Enchantment> getEnchantments() {
        return enchantments;
    }

    public Set<ItemFlag> getItemFlags() {
        return itemFlags;
    }

}