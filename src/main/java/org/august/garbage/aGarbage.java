package org.august.garbage;

import org.august.garbage.repository.GarbageRepository;
import org.bukkit.plugin.java.JavaPlugin;

public final class aGarbage extends JavaPlugin {

    @Override
    public void onEnable() {
        configure();
    }

    @Override
    public void onDisable() {

    }

    private void configure() {
        boolean folder = getDataFolder().mkdir();

        GarbageRepository garbageRepository = GarbageRepository.getInstance();
        garbageRepository.setTrash(this);

        if (folder) garbageRepository.makeFile();
        garbageRepository.load();
    }

}
