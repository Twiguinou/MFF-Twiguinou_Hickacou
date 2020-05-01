package com.twihick.modularexplosions.configs;

import net.minecraftforge.common.ForgeConfigSpec;

public class ClientConfig {

    public ClientConfig(ForgeConfigSpec.Builder builder) {
        builder.comment("CLIENT CONFIG");
        builder.push("client");
        builder.pop();
    }

}
