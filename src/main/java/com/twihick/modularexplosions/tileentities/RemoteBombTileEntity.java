package com.twihick.modularexplosions.tileentities;

import com.twihick.modularexplosions.common.registry.TileEntitiesList;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;

import java.util.UUID;


public class RemoteBombTileEntity extends TileEntity {

    public UUID playerLink;

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
        if(compound.hasUniqueId("Player UUID")) {
            this.playerLink = compound.getUniqueId("Player UUID");
        }
        super.read(compound);
    }

}
