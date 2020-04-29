package com.twihick.modularexplosions.common.registry;

import com.twihick.modularexplosions.StringID;
import com.twihick.modularexplosions.blocks.TimerBombBlock;
import net.minecraft.block.Block;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = StringID.ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class BlocksList {

    public static final Block TIMER_BOMB = RegistryMethods.buildBlock(TimerBombBlock.class, IDS.TIMER_BOMB.getValue(), null);
    public static final Block REMOTE_BOMB =

    @SubscribeEvent
    public static void registerBlocks(final RegistryEvent.Register<Block> event) {
        RegistryMethods.registerBlocks(event);
    }

    private enum IDS {
        TIMER_BOMB("timer_bomb"),
        REMOTE_BOMB("remote_bomb");

        private final String value;
        IDS(String label) {
            this.value = label;
        }
        public String getValue() {
            return value;
        }
    }

}
