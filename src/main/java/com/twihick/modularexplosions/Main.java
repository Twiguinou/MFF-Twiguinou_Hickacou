package com.twihick.modularexplosions;

import com.twihick.modularexplosions.common.registry.ItemsList;
import com.twihick.modularexplosions.configs.CommonConfig;
import com.twihick.modularexplosions.ops.ClientOperationSide;
import com.twihick.modularexplosions.ops.ServerOperationSide;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(StringID.ID)
public class Main {

    private static Logger logger = LogManager.getLogger();
    private static final ServerOperationSide ops = DistExecutor.runForDist(() -> ClientOperationSide::new, () -> ServerOperationSide::new);
    private static final ItemGroup group = new ItemGroup(StringID.ID) {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(ItemsList.THROWABLE_DYNAMITE);
        }
    };

    public Main() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, CommonConfig.commonConfigSpec);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, CommonConfig.clientConfigSpec);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::common);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::client);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::server);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void common(final FMLCommonSetupEvent event) {
        ops.common(event);
    }

    private void client(final FMLClientSetupEvent event) {
        ops.client(event);
    }

    private void server(final FMLServerStartingEvent event) {
        ops.server(event);
    }

    public static Logger getLogger() {
        return logger;
    }

    public static ItemGroup getGroup() {
        return group;
    }

}
