package org.august.garbage.utility;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.jetbrains.annotations.NotNull;

public class GarbageExpansion extends PlaceholderExpansion {

    private final String id;
    private final String author;

    public GarbageExpansion(String id, String author) {
        this.id = id;
        this.author = author;
    }

    @Override
    public @NotNull String getIdentifier() {
        return id;
    }

    @Override
    public @NotNull String getAuthor() {
        return author;
    }

    @Override
    public @NotNull String getVersion() {
        return "1.4.1";
    }

}