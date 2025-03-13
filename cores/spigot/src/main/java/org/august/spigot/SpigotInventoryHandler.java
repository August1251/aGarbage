package org.august.spigot;

import org.august.shade.InventoryHandler;
import org.august.spigot.format.ColorFormatter;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class SpigotInventoryHandler extends InventoryHandler {

    private final ColorFormatter colorFormatter = ColorFormatter.getInstance();

    @Override
    public void makeInventory(int size, String title) {
        setInventory(Bukkit.createInventory(null, size, colorFormatter.getColoredText(title)));
    }

    @Override
    public void addItem(int slot, ItemStack itemStack, String displayName, List<String> lore) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (displayName.isEmpty()) itemMeta.setDisplayName(colorFormatter.getColoredText(displayName));

        List<String> lores = new ArrayList<>();
        if (!lore.getFirst().isEmpty()) for (String text : lore) lores.add(colorFormatter.getColoredText(text));
        itemMeta.setLore(lores);

        getInventory().setItem(slot, itemStack);
    }

}