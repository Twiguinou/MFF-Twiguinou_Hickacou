package com.twihick.modularexplosions.client.gui;

import com.twihick.modularexplosions.tileentities.ConfigurableBombTileEntity;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.TranslationTextComponent;

public class ConfigurableBombScreen extends Screen {

    private final ConfigurableBombTileEntity bomb;

    public ConfigurableBombScreen(ConfigurableBombTileEntity bomb) {
        super(new TranslationTextComponent("gui.modularexplosions.configurable_bomb_screen"));
        this.bomb = bomb;
    }

    @Override
    public void init() {
    }

}
