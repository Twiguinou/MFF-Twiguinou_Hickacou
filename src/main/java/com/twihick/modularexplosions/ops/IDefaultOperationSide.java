package com.twihick.modularexplosions.ops;

import com.twihick.modularexplosions.common.events.CommonEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.loading.FMLEnvironment;

public interface IDefaultOperationSide {

    default void common(FMLCommonSetupEvent event) {
        MinecraftForge.EVENT_BUS.register(new CommonEvents());
    }

    void client(FMLClientSetupEvent event);

    void server(FMLServerStartingEvent event);

    static ServerOperationSide createInstance() {
        if(FMLEnvironment.dist == Dist.DEDICATED_SERVER) {
            return new ServerOperationSide();
        }
        return new ClientOperationSide();
    }

}
