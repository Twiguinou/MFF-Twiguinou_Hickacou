package com.twihick.modularexplosions.packets;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public interface IPacket<T> {

    void to(T packet, PacketBuffer buffer);

    T from(PacketBuffer buffer);

    void create(T packet, Supplier<NetworkEvent.Context> supplier);

}
