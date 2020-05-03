package com.twihick.modularexplosions.items;

import com.twihick.modularexplosions.Main;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class GrenadeItem extends Item {

    public GrenadeItem() {
        super(new Item.Properties()
                .group(Main.getGroup())
                .maxStackSize(8));
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        StringTextComponent textComponent = new StringTextComponent("Ammo for the grenade launcher");
        tooltip.add(textComponent);
    }

}
