package com.twihick.modularexplosions.common.registry;

public enum SoundLocations {

    BLOCK("block"),
    ITEM("item");

    private final String prefix;

    SoundLocations(String prefix) {
        this.prefix = ":" + prefix + ".";
    }

    public String getPrefix() {
        return prefix;
    }

}
