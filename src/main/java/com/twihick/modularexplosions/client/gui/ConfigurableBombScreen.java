package com.twihick.modularexplosions.client.gui;

import com.twihick.modularexplosions.StringID;
import com.twihick.modularexplosions.tileentities.ConfigurableBombTileEntity;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;

public class ConfigurableBombScreen extends Screen {

    private static final ResourceLocation GUI_BACKGROUND = new ResourceLocation(StringID.ID, "textures/gui/configurable_bomb_screen.png");

    private final int xSize = 220;
    private final int ySize = 220;

    private final ConfigurableBombTileEntity bomb;
    private Button radiusOnePlus;
    private Button radiusOneMinus;
    private Button radiusTwoPlus;
    private Button radiusTwoMinus;

    public ConfigurableBombScreen(ConfigurableBombTileEntity bomb) {
        super(new TranslationTextComponent("gui.modularexplosions.configurable_bomb_screen"));
        this.bomb = bomb;
    }

    @Override
    public void init() {
        int leftCorner = (this.width-this.xSize) / 2;
        int topCorner = (this.height-this.ySize) / 2;
        this.radiusOnePlus = this.addButton(new Button(leftCorner+10, topCorner+170, 40, 20, I18n.format("gui.button.modularexplosions.radiusOnePlus"), button -> {
            this.bomb.setRadiusOne(this.bomb.radius_one+1);
        }));
        this.radiusOneMinus = this.addButton(new Button(leftCorner+10, topCorner+170, 40, 20, I18n.format("gui.button.modularexplosions.radiusOneMinus"), button -> {
            this.bomb.setRadiusOne(this.bomb.radius_one-1);
        }));
        this.radiusTwoPlus = this.addButton(new Button(leftCorner+10, topCorner+170, 40, 20, I18n.format("gui.button.modularexplosions.radiusOnePlus"), button -> {
            this.bomb.setRadiusTwo(this.bomb.radius_two+1);
        }));
        this.radiusTwoMinus = this.addButton(new Button(leftCorner+10, topCorner+170, 40, 20, I18n.format("gui.button.modularexplosions.radiusOnePlus"), button -> {
            this.bomb.setRadiusTwo(this.bomb.radius_two-1);
        }));
        this.radiusOnePlus.active = false;
        this.radiusOneMinus.active = false;
        this.radiusTwoPlus.active = false;
        this.radiusTwoMinus.active = false;
    }

}
