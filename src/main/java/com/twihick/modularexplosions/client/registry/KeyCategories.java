package com.twihick.modularexplosions.client.registry;

public enum KeyCategories {

    GAMEPLAY("gameplay");

    private final String value;

    KeyCategories(String suffix) {
        this.value = "key.categories." + suffix;
    }

    public String getValue() {
        return value;
    }

}
