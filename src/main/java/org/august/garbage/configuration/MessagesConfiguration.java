package org.august.garbage.configuration;

import org.august.garbage.aGarbage;
import org.august.garbage.dto.MessageDto;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class MessagesConfiguration {

    public static class Holder {
        public static final MessagesConfiguration INSTANCE = new MessagesConfiguration();
    }

    public static MessagesConfiguration getInstance() {
        return Holder.INSTANCE;
    }

    private static aGarbage garbage;

    private FileConfiguration getConfig() {
        return garbage.getConfig();
    }

    private ConfigurationSection getMessageSection() {
        return getConfig().getConfigurationSection("messages");
    }

    public MessageDto getMessage(String index) {
        ConfigurationSection messageSection = getMessageSection().getConfigurationSection(index);

        boolean enabled = messageSection.getBoolean("enabled");
        List<String> message = messageSection.getStringList("message");

        return new MessageDto(enabled, message);
    }

    public void setGarbage(aGarbage garbage) {
        MessagesConfiguration.garbage = garbage;
    }

}