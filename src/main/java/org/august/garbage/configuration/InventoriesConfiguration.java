package org.august.garbage.configuration;

import org.august.garbage.aGarbage;
import org.august.garbage.dto.InventoryDto;
import org.august.garbage.dto.ItemDto;
import org.august.garbage.dto.ItemType;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;

import java.util.*;

public class InventoriesConfiguration {

    public static class Holder {
        public static final InventoriesConfiguration INSTANCE = new InventoriesConfiguration();
    }

    public static InventoriesConfiguration getInstance() {
        return Holder.INSTANCE;
    }

    private static aGarbage garbage;

    private FileConfiguration getConfig() {
        return garbage.getConfig();
    }

    private ConfigurationSection getInventoriesSection() {
        return getConfig().getConfigurationSection("inventories");
    }

    public InventoryDto getInventory(String index) {
        ConfigurationSection inventorySection = getInventoriesSection().getConfigurationSection(index);

        String name = inventorySection.getName();
        int size = inventorySection.getInt("size");
        String title = inventorySection.getString("title");
        String slots = String.join("", inventorySection.getStringList("slots"));
        ConfigurationSection itemsSection = inventorySection.getConfigurationSection("items");

        return new InventoryDto(name, size, title, slots, getItems(itemsSection, slots));
    }

    private List<ItemDto> getItems(ConfigurationSection itemsSection, String slots) {
        List<ItemDto> items = new ArrayList<>();
        for (int i = 0; i < slots.length(); i++) {
            if (slots.charAt(i) == 'x' || slots.charAt(i) == 'X') continue;
            ConfigurationSection item = itemsSection.getConfigurationSection(String.valueOf(slots.charAt(i)));

            ItemType itemType = ItemType.valueOf(item.getString("type").toUpperCase());
            Material material = Material.getMaterial(item.getString("material").toUpperCase());
            int amount = item.getInt("amount");
            String displayName = item.getString("name");
            List<String> lore = item.getStringList("lore");

            HashMap<Integer, Enchantment> enchantments = new HashMap<>();
            for (String enchantment : item.getStringList("enchantments")) {
                if (enchantment.isEmpty()) continue;
                String[] split = enchantment.split(":");
                enchantments.put(Integer.parseInt(split[1]), Registry.ENCHANTMENT.get(NamespacedKey.minecraft(split[0])));
            }

            Set<ItemFlag> itemFlags = new HashSet<>();
            for (String itemFlag : item.getStringList("itemFlags")) {
                if (itemFlag.isEmpty()) continue;
                itemFlags.add(ItemFlag.valueOf(itemFlag));
            }

            items.add(new ItemDto(i, itemType, material, amount, displayName, lore, enchantments, itemFlags));
        }
        return items;
    }

    public void setGarbage(aGarbage garbage) {
        InventoriesConfiguration.garbage = garbage;
    }

}