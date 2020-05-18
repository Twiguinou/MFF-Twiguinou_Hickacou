package com.twihick.modularexplosions.packets;

import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public abstract class AbstractPacket<T> implements IPacket<T> {

    private static int index = 0;
    public final ITextComponent name;

    protected AbstractPacket(ITextComponent component) {
        this.name = component;
    }

    public static <T> void registerPacket(Class<T> clazz, IPacket<T> packet, SimpleChannel channel) {
        channel.registerMessage(index++, clazz, packet::to, packet::from, packet::create);
    }

}
