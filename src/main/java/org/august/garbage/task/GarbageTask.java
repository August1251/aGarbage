package org.august.garbage.task;

import org.august.garbage.model.GarbageModel;
import org.august.garbage.storage.GarbageStorage;
import org.bukkit.scheduler.BukkitRunnable;

public class GarbageTask extends BukkitRunnable {

    private final GarbageStorage garbageStorage = GarbageStorage.getInstance();

    @Override
    public void run() {
        for (GarbageModel garbageModel : garbageStorage.getGarbageModels().values()) {
            if (garbageModel.getInventoryManager() != null) garbageModel.getInventoryManager().updateInventory(garbageModel);
            if (garbageModel.isTimeToExecute()) {
                garbageModel.executeMethod();
            } else {
                garbageModel.decreaseTime();
            }
        }
    }

}