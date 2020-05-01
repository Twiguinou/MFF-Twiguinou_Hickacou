package com.twihick.modularexplosions.common.registry;

import com.twihick.modularexplosions.StringID;
import com.twihick.modularexplosions.blocks.ConfigurableBombBlock;
import com.twihick.modularexplosions.blocks.RemoteBombBlock;
import com.twihick.modularexplosions.blocks.TimerBombBlock;
import net.minecraft.block.Block;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = StringID.ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class BlocksList {

    public static final Block TIMER_BOMB = CommonRegistryMethods.buildBlock(TimerBombBlock.class, IDS.TIMER_BOMB.getValue(), null);
    public static final Block REMOTE_BOMB = CommonRegistryMethods.buildBlock(RemoteBombBlock.class, IDS.REMOTE_BOMB.getValue(), null);
    public static final Block CONFIGURABLE_BOMB = CommonRegistryMethods.buildBlock(ConfigurableBombBlock.class, IDS.CONFIGURABLE_BOMB.getValue(), null);

    @SubscribeEvent
    public static void registerBlocks(final RegistryEvent.Register<Block> event) {
        CommonRegistryMethods.registerBlocks(event);
    }

    private enum IDS {
        TIMER_BOMB("timer_bomb"),
        REMOTE_BOMB("remote_bomb"),
        CONFIGURABLE_BOMB("configurable_bomb");

        private final String value;
        IDS(String label) {
            this.value = label;
        }
        public String getValue() {
            return value;
        }
    }

}
