package org.august.spigot.format;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.bukkit.ChatColor.COLOR_CHAR;

public class ColorFormatter {

    public static class Holder {
        public static final ColorFormatter INSTANCE = new ColorFormatter();
    }

    public static ColorFormatter getInstance() {
        return Holder.INSTANCE;
    }

    public String getColoredText(String text) {
        final Pattern hexPattern = Pattern.compile("&#" + "([A-Fa-f0-9]{6})");
        Matcher matcher = hexPattern.matcher(text);
        StringBuilder buffer = new StringBuilder(text.length() + 4 * 8);
        while (matcher.find())
        {
            String group = matcher.group(1);
            matcher.appendReplacement(buffer, COLOR_CHAR + "x"
                    + COLOR_CHAR + group.charAt(0) + COLOR_CHAR + group.charAt(1)
                    + COLOR_CHAR + group.charAt(2) + COLOR_CHAR + group.charAt(3)
                    + COLOR_CHAR + group.charAt(4) + COLOR_CHAR + group.charAt(5)
            );
        }
        return matcher.appendTail(buffer).toString();
    }

}