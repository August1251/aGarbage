package org.august.garbage.dto;

import java.util.List;

public class CommandDto {

    private final String name;
    private final String description;
    private final String usage;
    private final List<String> aliases;

    public CommandDto(String name, String description, String usage, List<String> aliases) {
        this.name = name;
        this.description = description;
        this.usage = usage;
        this.aliases = aliases;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getUsage() {
        return usage;
    }

    public List<String> getAliases() {
        return aliases;
    }

}