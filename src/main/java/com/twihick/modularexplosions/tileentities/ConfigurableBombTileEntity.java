package com.twihick.modularexplosions.tileentities;

import com.twihick.modularexplosions.common.registry.TileEntitiesList;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;

import javax.annotation.Nullable;

public class ConfigurableBombTileEntity extends TileEntity {

    public int width = 10;
    public int height = 10;

    public ConfigurableBombTileEntity() {
        super(TileEntitiesList.CONFIGURABLE_BOMB);
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        compound.putInt("width", this.width);
        compound.putInt("height", this.height);
        return super.write(compound);
    }


    @Override
    public void read(CompoundNBT compound) {
        super.read(compound);
        if(compound.contains("width")) {
            this.width = compound.getInt("width");
        }
        if(compound.contains("height")) {
            this.height = compound.getInt("height");
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
