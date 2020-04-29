package com.twihick.modularexplosions.tileentities;

import com.twihick.modularexplosions.common.registry.TileEntitiesList;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TimerBombTileEntity extends TileEntity implements ITickableTileEntity {

    public TimerBombTileEntity() {
        super(TileEntitiesList.TIMER_BOMB);
    }

    @Override
    public void tick() {
    }

}
