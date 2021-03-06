package com.twihick.modularexplosions.common.registry;

import com.twihick.modularexplosions.StringID;
import com.twihick.modularexplosions.tileentities.ConfigurableBombTileEntity;
import com.twihick.modularexplosions.tileentities.RemoteBombTileEntity;
import com.twihick.modularexplosions.tileentities.TimerBombTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = StringID.ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class TileEntitiesList {

    public static final TileEntityType<TimerBombTileEntity> TIMER_BOMB = CommonRegistryMethods.buildTileEntity(TimerBombTileEntity.class, IDS.TIMER_BOMB.getValue(), BlocksList.TIMER_BOMB);
    public static final TileEntityType<RemoteBombTileEntity> REMOTE_BOMB = CommonRegistryMethods.buildTileEntity(RemoteBombTileEntity.class, IDS.REMOTE_BOMB.getValue(), BlocksList.REMOTE_BOMB);
    public static final TileEntityType<ConfigurableBombTileEntity> CONFIGURABLE_BOMB = CommonRegistryMethods.buildTileEntity(ConfigurableBombTileEntity.class, IDS.CONFIGURABLE_BOMB.getValue(), BlocksList.CONFIGURABLE_BOMB);

    @SubscribeEvent
    public static void registerTileEntities(final RegistryEvent.Register<TileEntityType<?>> event) {
        CommonRegistryMethods.registerTileEntities(event);
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
