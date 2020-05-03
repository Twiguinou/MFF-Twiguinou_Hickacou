package com.twihick.modularexplosions.tileentities;

import com.twihick.modularexplosions.blocks.RemoteBombBlock;
import com.twihick.modularexplosions.common.registry.SoundsList;
import com.twihick.modularexplosions.common.registry.TileEntitiesList;
import com.twihick.modularexplosions.util.world.CustomExplosion;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nullable;
import java.util.UUID;


public class RemoteBombTileEntity extends TileEntity implements ITickableTileEntity {

    public UUID playerLink = null;

    public RemoteBombTileEntity() {
        super(TileEntitiesList.REMOTE_BOMB);
    }

    public void setActivated() {
        this.world.setBlockState(this.getPos(), this.getBlockState().with(RemoteBombBlock.ACTIVATED, Boolean.valueOf(true)));
    }

    public void setPlayerLink(UUID newUUID) {
        this.playerLink = newUUID;
    }

    @Override
    public void tick() {
        sync();
        if(this.getBlockState().get(RemoteBombBlock.ACTIVATED).booleanValue()) {
            this.world.setBlockState(this.getPos(), Blocks.AIR.getDefaultState());
            CustomExplosion explosion = new CustomExplosion(this.world, this.getPos(), 6, 0.764F);
            explosion.explodeExcluding(null, SoundsList.BOMB_GENERIC);
            this.remove();
        }
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        if(this.playerLink != null) {
            compound.putUniqueId("LinkedUUID", this.playerLink);
        }
        return super.write(compound);
    }

    public void sync() {
        this.markDirty();
        SUpdateTileEntityPacket packet = this.getUpdatePacket();
        if(packet != null) {
            if(this.world instanceof ServerWorld) {
                ServerWorld serverWorld = (ServerWorld) world;
                serverWorld.getChunkProvider().chunkManager.getTrackingPlayers(new ChunkPos(this.getPos()), false).forEach(player -> player.connection.sendPacket(packet));
            }
        }
    }

    @Override
    public void read(CompoundNBT compound) {
        super.read(compound);
        if(compound.contains("LinkedUUID", Constants.NBT.TAG_STRING)) {
            this.playerLink = compound.getUniqueId("LinkedUUID");
        }
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(this.pos, 0, this.getUpdateTag());
    }

    @Override
    public CompoundNBT getUpdateTag() {
        return this.write(new CompoundNBT());
    }

    @Override
    public void onDataPacket(NetworkManager network, SUpdateTileEntityPacket packet) {
        this.read(packet.getNbtCompound());
    }

}
