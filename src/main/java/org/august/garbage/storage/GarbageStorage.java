package org.august.garbage.storage;

import org.august.garbage.model.GarbageModel;
import org.august.garbage.task.GarbageTask;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.Set;

public class GarbageStorage {

    private static final Set<GarbageModel> garbages = new HashSet<>();
    private static GarbageTask garbageTask;

    private static class Holder {
        public static final GarbageStorage INSTANCE = new GarbageStorage();
    }

    public static GarbageStorage getInstance() {
        return Holder.INSTANCE;
    }

    public void addGarbage(GarbageModel garbageModel) {
        garbages.add(garbageModel);
    }

    public GarbageModel getGarbageModel(GarbageModel garbageModel) {
        for (GarbageModel i : garbages) {
            if (i.equals(garbageModel)) return i;
        }
        return null;
    }

    public void removeGarbage(GarbageModel garbageModel) {
        garbages.removeIf(i -> i.equals(garbageModel));
    }

    public GarbageModel getGarbageModel(Player player) {
        for (GarbageModel i : garbages) {
            for (Player p : i.getPlayers()) {
                if (p.equals(player)) return i;
            }
        }
        return null;
    }

    public boolean isGarbageExists(Player player) {
        for (GarbageModel garbageModel : garbages) {
            for (Player p : garbageModel.getPlayers()) {
                if (p.getName().equals(player.getName())) return true;
            }
        }
        return false;
    }

    public boolean isGarbageExists(GarbageModel garbageModel) {
        for (GarbageModel gm : garbages) {
            if (gm.equals(garbageModel)) return true;
        }
        return false;
    }

    public void setGarbageTask(BukkitRunnable bukkitRunnable) {
        GarbageStorage.garbageTask = (GarbageTask) bukkitRunnable;
    }

    public Set<GarbageModel> getGarbages() {
        return garbages;
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