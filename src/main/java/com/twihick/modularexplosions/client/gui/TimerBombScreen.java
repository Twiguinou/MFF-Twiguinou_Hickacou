package com.twihick.modularexplosions.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.twihick.modularexplosions.StringID;
import com.twihick.modularexplosions.blocks.TimerBombBlock;
import com.twihick.modularexplosions.tileentities.TimerBombTileEntity;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import java.awt.*;

public class TimerBombScreen extends Screen {

    private static final ResourceLocation GUI_BACKGROUND = new ResourceLocation(StringID.ID, "textures/gui/timer_bomb_screen.png");

    private final int xSize = 151;
    private final int ySize = 101;

    private final TimerBombTileEntity bomb;
    private Button buttonMoreMins;
    private Button buttonLessMins;
    private Button buttonMoreSecs;
    private Button buttonLessSecs;
    private Button buttonActivate;

    public TimerBombScreen(TimerBombTileEntity bomb) {
        super(new TranslationTextComponent("gui.modularexplosions.timer_bomb_screen"));
        this.bomb = bomb;
    }

    @Override
    protected void init() {
        int leftCorner = (this.width-this.xSize) / 2;
        int topCorner = (this.height-this.ySize) / 2;
        this.buttonMoreMins = this.addButton(new Button((leftCorner+this.xSize/2)-30, topCorner+10,20,20, I18n.format("gui.button.modularexplosions.more"), button -> {
            bomb.runningTicks+=1200;
        }));
        this.buttonLessMins = this.addButton(new Button((leftCorner+this.xSize/2)-30, topCorner+50,20,20, I18n.format("gui.button.modularexplosions.less"), button -> {
            if (bomb.computeTime()[0]-1 >= 0) {
                bomb.runningTicks-=1200;
            }
        }));
        this.buttonMoreSecs = this.addButton(new Button((leftCorner+this.xSize/2)+10, topCorner+10,20,20, I18n.format("gui.button.modularexplosions.more"), button -> {
                bomb.runningTicks+=20;
        }));
        this.buttonLessSecs = this.addButton(new Button((leftCorner+this.xSize/2)+10, topCorner+50,20,20, I18n.format("gui.button.modularexplosions.less"), button -> {
            if (bomb.runningTicks-20 >= 0) {
                bomb.runningTicks-=20;
            }
        }));
        this.buttonActivate = this.addButton(new Button((leftCorner+this.xSize/2)-40, topCorner+75, 80, 20, I18n.format("gui.button.modularexplosions.activate"), button -> {
            bomb.getWorld().setBlockState(bomb.getPos(), bomb.getBlockState().with(TimerBombBlock.ACTIVATED, Boolean.valueOf(true)));
            this.minecraft.player.closeScreen();
        }));
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        this.renderBackground();
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(GUI_BACKGROUND);
        int leftCorner = (this.width-this.xSize) / 2;
        int topCorner = (this.height-this.ySize) / 2;
        this.blit(leftCorner, topCorner, 0, 0, this.xSize, this.ySize);
        super.render(mouseX, mouseY, partialTicks);
        float minsW = this.font.getStringWidth(new StringTextComponent(bomb.getFormattedMinutes()).getFormattedText());
        float secsW = this.font.getStringWidth(new StringTextComponent(bomb.getFormattedSeconds()).getFormattedText());
        this.font.drawString(new StringTextComponent(bomb.getFormattedMinutes()).getFormattedText(),(leftCorner+this.xSize/2)-30+minsW/2, topCorner+35, Color.WHITE.getRGB());
        this.font.drawString(new StringTextComponent(bomb.getFormattedSeconds()).getFormattedText(),(leftCorner+this.xSize/2)+10+secsW/2, topCorner+35, Color.WHITE.getRGB());

    }

}
