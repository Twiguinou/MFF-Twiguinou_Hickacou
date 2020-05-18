package com.twihick.modularexplosions.packets;

import com.twihick.modularexplosions.StringID;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public final class Packets {

    public static SimpleChannel INSTANCE;

    public static void init() {
        INSTANCE = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(StringID.ID, "packets"))
                .networkProtocolVersion(() -> "1")
                .clientAcceptedVersions("1"::equals)
                .serverAcceptedVersions("1"::equals)
                .simpleChannel();
        AbstractPacket.registerPacket(ActivateExplosiveBeltPacket.class, new ActivateExplosiveBeltPacket(), INSTANCE);
        {
            AbstractPacket.registerPacket(TimerBombPackets.SetTicks.class, new TimerBombPackets.SetTicks(), INSTANCE);
            AbstractPacket.registerPacket(TimerBombPackets.Activate.class, new TimerBombPackets.Activate(), INSTANCE);
        }
    }

}
