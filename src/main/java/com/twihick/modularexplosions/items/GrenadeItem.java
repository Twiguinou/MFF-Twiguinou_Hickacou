package com.twihick.modularexplosions.items;

import com.twihick.modularexplosions.Main;
import net.minecraft.item.Item;

public class GrenadeItem extends Item {

    public GrenadeItem() {
        super(new Item.Properties()
                .group(Main.getGroup())
                .maxStackSize(8));
    }

}
