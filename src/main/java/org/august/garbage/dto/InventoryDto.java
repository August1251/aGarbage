package org.august.garbage.dto;

import java.util.List;

public class InventoryDto {

    private final String name;
    private final int size;
    private final String title;
    private final String slots;
    private final List<ItemDto> items;

    public InventoryDto(String name, int size, String title, String slots, List<ItemDto> items) {
        this.name = name;
        this.size = size;
        this.title = title;
        this.slots = slots;
        this.items = items;
    }

    public String getName() {
        return name;
    }

    public int getSize() {
        return size;
    }

    public String getTitle() {
        return title;
    }

    public String getSlots() {
        return slots;
    }

    public List<ItemDto> getItems() {
        return items;
    }

}