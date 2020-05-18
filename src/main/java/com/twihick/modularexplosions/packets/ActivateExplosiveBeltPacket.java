package com.twihick.modularexplosions.packets;

import com.twihick.modularexplosions.items.ExplosiveBeltItem;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class ActivateExplosiveBeltPacket extends AbstractPacket<ActivateExplosiveBeltPacket> {

    public ActivateExplosiveBeltPacket() {
        super(new TranslationTextComponent("explosive_belt.activate"));
    }

    @Override
    public void to(ActivateExplosiveBeltPacket packet, PacketBuffer buffer) {
    }

    @Override
    public ActivateExplosiveBeltPacket from(PacketBuffer buffer) {
        return new ActivateExplosiveBeltPacket();
    }

    @Override
    public void create(ActivateExplosiveBeltPacket packet, Supplier<NetworkEvent.Context> supplier) {
        supplier.get().enqueueWork(() -> {
            ServerPlayerEntity player = supplier.get().getSender();
            if(player != null) {
                ItemStack stack = player.getItemStackFromSlot(EquipmentSlotType.CHEST);
                if(stack.getItem() instanceof ExplosiveBeltItem) {
                    ExplosiveBeltItem.setActivated(stack, true);
                }
            }
        });
        supplier.get().setPacketHandled(true);
    }

}
