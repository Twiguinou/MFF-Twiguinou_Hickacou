package com.twihick.modularexplosions.configs;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class CommonConfig {

    private static final Pair<ClientConfig, ForgeConfigSpec> clientPair = new ForgeConfigSpec.Builder().configure(ClientConfig::new);
    private static final Pair<CommonConfig, ForgeConfigSpec> commonPair = new ForgeConfigSpec.Builder().configure(CommonConfig::new);
    private static final Pair<ServerConfig, ForgeConfigSpec> serverPair = new ForgeConfigSpec.Builder().configure(ServerConfig::new);
    public static final ForgeConfigSpec clientConfigSpec = clientPair.getRight(), commonConfigSpec = commonPair.getRight(), serverConfigSpec = serverPair.getRight();

    public CommonConfig(ForgeConfigSpec.Builder builder) {
        builder.comment("COMMON CONFIG");
        builder.push("common");
        builder.pop();
    }

}
