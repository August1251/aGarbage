package org.august.garbage.storage;

import org.august.garbage.model.GarbageModel;
import org.august.garbage.task.GarbageTask;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

public class GarbageStorage {

    private static final Map<Player, GarbageModel> garbageModels = new HashMap<>();
    private static GarbageTask garbageTask;

    private static class Holder {
        public static final GarbageStorage INSTANCE = new GarbageStorage();
    }

    public static GarbageStorage getInstance() {
        return Holder.INSTANCE;
    }

    public void addGarbageModel(Player player, GarbageModel garbageModel) {
        garbageModels.put(player, garbageModel);
    }

    public GarbageModel getGarbageModel(Player player) {
        return garbageModels.get(player);
    }

    public void removeGarbageModel(Player player) {
        garbageModels.remove(player);
    }

    public boolean isGarbageExists(Player player) {
        return garbageModels.containsKey(player);
    }

    public boolean isGarbageExists(GarbageModel garbageModel) {
        return garbageModels.containsValue(garbageModel);
    }

    public void setGarbageTask(BukkitRunnable bukkitRunnable) {
        GarbageStorage.garbageTask = (GarbageTask) bukkitRunnable;
    }

    public Map<Player, GarbageModel> getGarbageModels() {
        return garbageModels;
    }

    public GarbageTask getGarbageTask() {
        return garbageTask;
    }

    public boolean existGarbageTask() {
        return garbageTask != null;
    }

    public void removeGarbageTask() {
        garbageTask = null;
    }

}