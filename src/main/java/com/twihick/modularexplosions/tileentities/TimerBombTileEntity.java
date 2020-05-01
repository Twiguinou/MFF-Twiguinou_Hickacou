package com.twihick.modularexplosions.tileentities;

import com.twihick.modularexplosions.blocks.TimerBombBlock;
import com.twihick.modularexplosions.common.registry.TileEntitiesList;
import com.twihick.modularexplosions.util.world.CustomExplosion;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import org.apache.logging.log4j.LogManager;

import javax.annotation.Nullable;
import java.util.logging.Logger;

public class TimerBombTileEntity extends TileEntity implements ITickableTileEntity {
    public int runningTicks = 500;

    public TimerBombTileEntity() {
        super(TileEntitiesList.TIMER_BOMB);
    }

    @Override
    public void tick() {
        if(this.isBlockActivated()) {
            runningTicks--;
            if(runningTicks%20 == 0) {
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

    public int[] computeTime() {
        int secs = this.runningTicks/20;
        int mins = (int)Math.floor(secs/60);
        secs-=mins*60;
        return new int[]{mins, secs};
    }

    public String getFormattedMinutes() {
        int mins = computeTime()[0];
        return (mins > 9) ? String.format("%s", mins) : String.format("0%s", mins);
    }

    public String getFormattedSeconds() {
        int secs = computeTime()[1];
        return (secs > 9) ? String.format("%s", secs) : String.format("0%s", secs);
    }

    public String getDisplayCountdown() {
        return String.format("%s:%s", this.getFormattedMinutes(), this.getFormattedSeconds());
    }

    public boolean isBlockActivated() {
        return this.getBlockState().get(TimerBombBlock.ACTIVATED).booleanValue();
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        compound.putInt("Running Ticks", this.runningTicks);
        return super.write(compound);
    }

    @Override
    public void read(CompoundNBT compound) {
        super.read(compound);
        if(compound.contains("Running Ticks")) {
            this.runningTicks = compound.getInt("Running Ticks");
        }
    }

    @Override
    public CompoundNBT getUpdateTag() {
        return this.write(new CompoundNBT());
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(this.getPos(), 0, this.getUpdateTag());
    }

    @Override
    public void onDataPacket(NetworkManager network, SUpdateTileEntityPacket packet) {
        this.read(packet.getNbtCompound());
    }

}
