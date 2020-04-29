package com.twihick.modularexplosions.ops;

import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;

public interface IDefaultOperationSide {

    default void common(FMLCommonSetupEvent event) {
    }

    void client(FMLClientSetupEvent event);

    void server(FMLServerStartingEvent event);

}
