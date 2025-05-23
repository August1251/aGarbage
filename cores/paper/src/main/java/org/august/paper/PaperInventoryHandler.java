package org.august.paper;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.august.shade.InventoryHandler;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class PaperInventoryHandler extends InventoryHandler {

    @Override
    public void makeInventory(int size, String title) {
        setInventory(Bukkit.createInventory(null, size, MiniMessage.miniMessage().deserialize(title)));
    }

    @Override
    public void addItem(int slot, ItemStack itemStack, String displayName, List<String> lore) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (!displayName.isBlank()) {
            itemMeta.displayName(MiniMessage.miniMessage().deserialize(displayName));
        }

        List<Component> lores = new ArrayList<>();
        if (!lore.getFirst().isBlank()) {
            for (String text : lore) lores.add(MiniMessage.miniMessage().deserialize(text));
            itemMeta.lore(lores);
        }

        itemStack.setItemMeta(itemMeta);

        getInventory().setItem(slot, itemStack);
    }

    @Override
    public void updateItem(int slot, String displayName, List<String> lore) {
        ItemStack itemStack = getInventory().getItem(slot);

        ItemMeta itemMeta = itemStack.getItemMeta();
        if (!displayName.isBlank()) {
            itemMeta.displayName(MiniMessage.miniMessage().deserialize(displayName));
        } else {
            itemMeta.displayName(Component.empty());
        }

        List<Component> lores = new ArrayList<>();

        if (!lore.getFirst().isBlank()) {
            for (String i : lore) {
                lores.add(MiniMessage.miniMessage().deserialize(i));
            }
            itemMeta.lore(lores);
        }

        itemStack.setItemMeta(itemMeta);
    }

}