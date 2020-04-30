package com.twihick.modularexplosions.tileentities;

import com.twihick.modularexplosions.blocks.TimerBombBlock;
import com.twihick.modularexplosions.common.registry.TileEntitiesList;
import com.twihick.modularexplosions.util.world.CustomExplosion;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;

public class TimerBombTileEntity extends TileEntity implements ITickableTileEntity {

    private int initialTicks = 500;
    private int runningTicks;

    public TimerBombTileEntity() {
        super(TileEntitiesList.TIMER_BOMB);
        this.runningTicks = initialTicks;
    }

    @Override
    public void tick() {
        if(this.isBlockActivated()) {
            runningTicks--;
            if(runningTicks%40 == 0) {
                this.world.playSound((PlayerEntity)null, this.getPos(), SoundEvents.BLOCK_WOODEN_BUTTON_CLICK_ON, SoundCategory.BLOCKS, 0.6F, 0.8F);
            }
            if(runningTicks <= 0) {
                this.world.setBlockState(this.getPos(), Blocks.AIR.getDefaultState());
                CustomExplosion customExplosion = new CustomExplosion(this.world, this.getPos(), 4, 0.65F);
                customExplosion.explode();
                this.remove();
            }
        }
    }

    public int getInitialTicks() {
        return initialTicks;
    }

    public void setInitialTicks(int ticks) {
        if(ticks < 20) {
            this.initialTicks = 20;
        }else {
            this.initialTicks = ticks;
        }
        this.runningTicks = initialTicks;
    }

    public int getRunningTicks() {
        return runningTicks;
    }

    public boolean isBlockActivated() {
        return this.getBlockState().get(TimerBombBlock.ACTIVATED).booleanValue();
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        compound.putInt("Initial Ticks", this.initialTicks);
        compound.putInt("Running Ticks", this.runningTicks);
        return super.write(compound);
    }

    @Override
    public void read(CompoundNBT compound) {
        if(compound.contains("Initial Ticks")) {
            this.initialTicks = compound.getInt("Initial Ticks");
        }
        if(compound.contains("Running Ticks")) {
            this.runningTicks = compound.getInt("Running Ticks");
        }
        super.read(compound);
    }

    public String getStringFormatted() {
        return String.valueOf(runningTicks*100/getInitialTicks()) + "%";
    }

}
