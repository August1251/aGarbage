package org.august.garbage.model;

import org.august.garbage.manager.InventoryManager;
import org.august.garbage.storage.GarbageState;

import java.util.Objects;

public class GarbageModel {

    private String creator;
    private GarbageState garbageState;
    private InventoryManager inventoryManager;
    private long reloadTime;
    private long lastExecutionTime = 1;
    private int x;
    private int y;
    private int z;

    public GarbageModel() {}

    public GarbageModel(String creator, int x, int y, int z) {
        this.creator = creator;
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

    public long getReloadTime() {
        return reloadTime;
    }

    public void setReloadTime(long reloadTime) {
        this.reloadTime = reloadTime;
    }

    public long getLastExecutionTime() {
        return lastExecutionTime;
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

    public void decreaseTime() {
        lastExecutionTime--;
    }

    public void resetLastExecutionTime() {
        this.lastExecutionTime = reloadTime;
    }

    public boolean isTimeToExecute() {
        return lastExecutionTime == 0;
    }

    public void executeMethod() {
        inventoryManager.resetInventory();
        resetLastExecutionTime();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        GarbageModel that = (GarbageModel) o;
        return x == that.x && y == that.y && z == that.z;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }

}