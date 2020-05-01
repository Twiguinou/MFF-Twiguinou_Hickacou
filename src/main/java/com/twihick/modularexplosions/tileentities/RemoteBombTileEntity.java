package com.twihick.modularexplosions.tileentities;

import com.twihick.modularexplosions.common.registry.TileEntitiesList;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;

import javax.annotation.Nullable;
import java.util.UUID;


public class RemoteBombTileEntity extends TileEntity  {

    public UUID playerLink = null;

    public RemoteBombTileEntity() {
        super(TileEntitiesList.REMOTE_BOMB);
    }

    public void setPlayerLink(UUID newUUID) {
        this.playerLink = newUUID;
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        if(this.playerLink != null) {
            compound.putUniqueId("Player UUID", this.playerLink);
        }
        return super.write(compound);
    }

    @Override
    public void read(CompoundNBT compound) {
        super.read(compound);
        if(compound.hasUniqueId("Player UUID")) {
            this.playerLink = compound.getUniqueId("Player UUID");
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
