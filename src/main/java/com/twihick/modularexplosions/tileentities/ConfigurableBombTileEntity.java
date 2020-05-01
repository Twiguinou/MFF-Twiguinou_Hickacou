package com.twihick.modularexplosions.tileentities;

import com.twihick.modularexplosions.common.registry.TileEntitiesList;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;

import javax.annotation.Nullable;

public class ConfigurableBombTileEntity extends TileEntity {

    public int radius_one = 10;
    public int radius_two = 10;

    public ConfigurableBombTileEntity() {
        super(TileEntitiesList.CONFIGURABLE_BOMB);
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        compound.putInt("Radius One", this.radius_one);
        compound.putInt("Radius Two", this.radius_two);
        return super.write(compound);
    }

    public void setRadiusOne(int radius) {
        this.radius_one = radius;
    }

    public void setRadiusTwo(int radius) {
        this.radius_two = radius;
    }

    @Override
    public void read(CompoundNBT compound) {
        super.read(compound);
        if(compound.contains("Radius One")) {
            this.radius_one = compound.getInt("Radius One");
        }
        if(compound.contains("Radius Two")) {
            this.radius_two = compound.getInt("Radius Two");
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
