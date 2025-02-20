package org.august.garbage.model;

import java.util.Objects;

public class GarbageModel {

    private String creator;
    private int x;
    private int y;
    private int z;

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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        GarbageModel that = (GarbageModel) o;
        return x == that.x && y == that.y && z == that.z && Objects.equals(creator, that.creator);
    }

    @Override
    public int hashCode() {
        return Objects.hash(creator, x, y, z);
    }

}