package com.twihick.modularexplosions.packets;

import com.twihick.modularexplosions.blocks.TimerBombBlock;
import com.twihick.modularexplosions.tileentities.TimerBombTileEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class TimerBombPackets {
    
    public static class SetTicks extends AbstractPacket<SetTicks> {
        private BlockPos pos;
        private int ticks;
        public SetTicks() {
            super(new TranslationTextComponent("timer_bomb.set_ticks"));
        }
        public SetTicks(BlockPos pos, int ticks) {
            this();
            this.pos = pos;
            this.ticks = ticks;
        }
        @Override
        public void to(SetTicks packet, PacketBuffer buffer) {
            buffer.writeBlockPos(packet.pos);
            buffer.writeInt(packet.ticks);
        }
        @Override
        public SetTicks from(PacketBuffer buffer) {
            return new SetTicks(buffer.readBlockPos(), buffer.readInt());
        }
        @Override
        public void create(SetTicks packet, Supplier<NetworkEvent.Context> supplier) {
            supplier.get().enqueueWork(() -> {
                ServerPlayerEntity player = supplier.get().getSender();
                if(player != null) {
                    World world = player.getServerWorld();
                    if(world.isAreaLoaded(packet.pos, 0)) {
                        TileEntity te = world.getTileEntity(packet.pos);
                        if(te instanceof TimerBombTileEntity) {
                            ((TimerBombTileEntity)te).runningTicks = packet.ticks;
                        }
                    }
                }
            });
            supplier.get().setPacketHandled(true);
        }
    }

    public static class Activate extends AbstractPacket<Activate> {
        private BlockPos pos;
        public Activate() {
            super(new TranslationTextComponent("timer_bomb.activate"));
        }
        public Activate(BlockPos pos) {
            this();
            this.pos = pos;
        }
        @Override
        public void to(Activate packet, PacketBuffer buffer) {
            buffer.writeBlockPos(packet.pos);
        }
        @Override
        public Activate from(PacketBuffer buffer) {
            return new Activate(buffer.readBlockPos());
        }
        @Override
        public void create(Activate packet, Supplier<NetworkEvent.Context> supplier) {
            supplier.get().enqueueWork(() -> {
                ServerPlayerEntity player = supplier.get().getSender();
                if(player != null) {
                    World world = player.getServerWorld();
                    if(world.isAreaLoaded(packet.pos, 0)) {
                        world.setBlockState(packet.pos, world.getBlockState(packet.pos).with(TimerBombBlock.ACTIVATED, Boolean.valueOf(true)));
                    }
                }
            });
            supplier.get().setPacketHandled(true);
        }
    }
    
}
