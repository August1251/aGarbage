package org.august.garbage.format;

import me.clip.placeholderapi.PlaceholderAPI;
import org.august.garbage.aGarbage;
import org.august.garbage.model.GarbageModel;
import org.bukkit.entity.Player;

public class TextFormatter {

    public static class Holder {
        public static final TextFormatter INSTANCE = new TextFormatter();
    }

    public static TextFormatter getInstance() {
        return Holder.INSTANCE;
    }

    private static aGarbage garbage;

    public String getFormattedText(Player player, String text, GarbageModel garbageModel) {
        if (garbage.getServer().getPluginManager().getPlugin("PlaceholderAPI") != null && player != null) {
            text = PlaceholderAPI.setPlaceholders(player, text);
        }

        if (garbageModel == null) return text;

        int time = (int) garbageModel.getLastExecutionTime();
        int hours = time / 3600;
        int minutes = (time % 3600) / 60;
        int seconds = time % 60;

        text = text.replaceAll("%hours%", String.valueOf(hours));
        text = text.replaceAll("%minutes%", String.valueOf(minutes));
        text = text.replaceAll("%seconds%", String.valueOf(seconds));

        return text;
    }

    public static void setGarbage(aGarbage garbage) {
        TextFormatter.garbage = garbage;
    }
}