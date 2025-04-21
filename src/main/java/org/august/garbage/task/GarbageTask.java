package org.august.garbage.task;

import org.august.garbage.configuration.SettingsConfiguration;
import org.august.garbage.dto.SettingsDto;
import org.august.garbage.model.GarbageModel;
import org.august.garbage.storage.GarbageStorage;
import org.bukkit.scheduler.BukkitRunnable;

public class GarbageTask extends BukkitRunnable {

    private final SettingsDto settingsDto = SettingsConfiguration.getInstance().getSettings();
    private final GarbageStorage garbageStorage = GarbageStorage.getInstance();
    private int time = settingsDto.getReloadTime();

    @Override
    public void run() {
        time--;
        for (GarbageModel garbageModel : garbageStorage.getGarbages()) {
            garbageModel.setTime(time);
            if (garbageModel.getInventoryManager() != null) {
                if (time == 0) {
                    garbageModel.executeMethod(settingsDto.getReloadTime());
                    time = settingsDto.getReloadTime();
                }
                garbageModel.getInventoryManager().updateInventory(garbageModel);
            }
        }
    }

}