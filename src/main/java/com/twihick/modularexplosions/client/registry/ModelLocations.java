package com.twihick.modularexplosions.client.registry;

public enum ModelLocations {

    CUSTOMS("customs");

    private final String prefix;

    ModelLocations(String prefix) {
        this.prefix = prefix + "/";
    }

    public String getPrefix() {
        return prefix;
    }

    public static String format(ModelLocations prefix, String label) {
        return CUSTOMS.getPrefix() + label;
    }

}
