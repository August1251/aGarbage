package org.august.garbage.model;

import org.august.garbage.manager.InventoryManager;
import org.august.garbage.storage.GarbageState;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class GarbageModel {

    private String creator;
    private GarbageState garbageState;
    private InventoryManager inventoryManager;
    private final Set<Player> players = new HashSet<>();
    private static int time;
    private String world;
    private int x;
    private int y;
    private int z;

    public GarbageModel() {}

    public GarbageModel(String creator, String world, int x, int y, int z) {
        this.creator = creator;
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public GarbageState getGarbageState() {
        return garbageState;
    }

    public void setGarbageState(GarbageState garbageState) {
        this.garbageState = garbageState;
    }

    public InventoryManager getInventoryManager() {
        return inventoryManager;
    }

    public void setInventoryManager(InventoryManager inventoryManager) {
        this.inventoryManager = inventoryManager;
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public boolean existsPlayer(Player player) {
        for (Player p : players) {
            if (p.equals(player)) return true;
        }
        return false;
    }

    public void removePlayer(Player player) {
        players.remove(player);
    }

    public Set<Player> getPlayers() {
        return players;
    }

    public String getWorld() {
        return world;
    }

    public void setWorld(String world) {
        this.world = world;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        GarbageModel.time = time;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public void executeMethod(int reloadTime) {
        setTime(reloadTime);
        getInventoryManager().resetInventory();
    }


    @Override
    public boolean equals(Object o) {
        if (!(o instanceof GarbageModel)) return false;
        GarbageModel that = (GarbageModel) o;
        return x == that.x && y == that.y && z == that.z && Objects.equals(world, that.world);
    }

    @Override
    public int hashCode() {
        return Objects.hash(world, x, y, z);
    }

}