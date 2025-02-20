package org.august.garbage.dto;

import java.util.List;

public class MessageDto {

    private final boolean enabled;
    private final List<String> message;

    public MessageDto(boolean enabled, List<String> message) {
        this.enabled = enabled;
        this.message = message;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public List<String> getMessage() {
        return message;
    }

}