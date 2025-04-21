package org.august.garbage.dto;

public class SettingsDto {

    private final int reloadTime;

    public SettingsDto(int reloadTime) {
        this.reloadTime = reloadTime;
    }

    public int getReloadTime() {
        return reloadTime;
    }

}