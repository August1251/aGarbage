package org.august.garbage.configuration;

import org.august.garbage.aGarbage;
import org.august.garbage.dto.SettingsDto;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

public class SettingsConfiguration {

    public static class Holder {
        public static final SettingsConfiguration INSTANCE = new SettingsConfiguration();
    }

    public static SettingsConfiguration getInstance() {
        return Holder.INSTANCE;
    }

    private static aGarbage garbage;

    public FileConfiguration getConfig() {
        return garbage.getConfig();
    }

    public ConfigurationSection getSettingsSection() {
        return getConfig().getConfigurationSection("settings");
    }

    public SettingsDto getSettings() {
        ConfigurationSection settingsSection = getSettingsSection();

        int reloadTime = settingsSection.getInt("reload-time");

        return new SettingsDto(reloadTime);
    }

    public void setGarbage(aGarbage garbage) {
        SettingsConfiguration.garbage = garbage;
    }

}