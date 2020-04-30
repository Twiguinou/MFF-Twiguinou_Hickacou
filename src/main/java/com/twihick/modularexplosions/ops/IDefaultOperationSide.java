package com.twihick.modularexplosions.ops;

import com.twihick.modularexplosions.common.events.CommonEvents;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;

public interface IDefaultOperationSide {

    default void common(FMLCommonSetupEvent event) {
        MinecraftForge.EVENT_BUS.register(new CommonEvents());
    }

    void client(FMLClientSetupEvent event);

    void server(FMLServerStartingEvent event);

}
