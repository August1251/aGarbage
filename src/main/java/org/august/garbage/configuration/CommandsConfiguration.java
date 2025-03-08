package org.august.garbage.configuration;

import org.august.garbage.aGarbage;
import org.august.garbage.dto.CommandDto;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;
import java.util.Set;

public class CommandsConfiguration {

    public static class Holder {
        public static final CommandsConfiguration INSTANCE = new CommandsConfiguration();
    }

    public static CommandsConfiguration getInstance() {
        return Holder.INSTANCE;
    }

    private static aGarbage garbage;

    public FileConfiguration getConfig() {
        return garbage.getConfig();
    }

    public ConfigurationSection getCommandsSection() {
        return getConfig().getConfigurationSection("command");
    }

    public Set<String> getCommands() {
        return getCommandsSection().getKeys(false);
    }

    public CommandDto getCommand(String index) {
        ConfigurationSection commandSection = getCommandsSection().getConfigurationSection(index);

        String description = commandSection.getString("description");
        String usage = commandSection.getString("usage");
        List<String> aliases = commandSection.getStringList("aliases");

        return new CommandDto(index, description, usage, aliases);
    }

    public void setGarbage(aGarbage garbage) {
        CommandsConfiguration.garbage = garbage;
    }

}