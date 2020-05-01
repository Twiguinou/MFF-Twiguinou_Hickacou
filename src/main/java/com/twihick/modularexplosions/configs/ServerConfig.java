package com.twihick.modularexplosions.configs;

import net.minecraftforge.common.ForgeConfigSpec;

public class ServerConfig {

    public ServerConfig(ForgeConfigSpec.Builder builder) {
        builder.comment("SERVER CONFIG");
        builder.push("server");
        builder.pop();
    }

}
