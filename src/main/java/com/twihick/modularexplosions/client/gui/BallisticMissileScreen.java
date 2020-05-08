package com.twihick.modularexplosions.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.twihick.modularexplosions.StringID;
import com.twihick.modularexplosions.entities.BallisticMissileEntity;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BallisticMissileScreen extends Screen {

    private static final ResourceLocation GUI_BACKGROUND = new ResourceLocation(StringID.ID, "textures/gui/ballistic_missile_screen.png");

    private final int xSize = 151;
    private final int ySize = 101;

    private final BallisticMissileEntity missile;
    private Button buttonSave;
    private Button buttonLaunch;
    private TextFieldWidget xField;
    private TextFieldWidget yField;
    private TextFieldWidget zField;

    public BallisticMissileScreen(BallisticMissileEntity missile) {
        super(new TranslationTextComponent("gui.modularexplosions.ballistic_missile_screen"));
        this.missile = missile;
    }

    @Override
    protected void init() {
        int leftCorner = (this.width-this.xSize) / 2;
        int topCorner = (this.height-this.ySize) / 2;
        this.buttonSave = this.addButton(new Button(leftCorner+10, topCorner+70, 40, 20, I18n.format("gui.button.modularexplosions.save"), button -> {
            if(this.xField.getText().length() > 0 && this.yField.getText().length() > 0 && this.zField.getText().length() > 0) {
                this.missile.setTargetX(Integer.parseInt(this.xField.getText()));
                this.missile.setTargetY(Integer.parseInt(this.yField.getText())/2);
                this.missile.setTargetZ(Integer.parseInt(this.zField.getText()));
            }
        }));
        this.buttonLaunch = this.addButton(new Button(leftCorner+70, topCorner+70, 40, 20, I18n.format("gui.button.modularexplosions.launch"), button -> {
            if(!this.missile.world.isRemote) {
                this.missile.setLaunched(true);
                this.minecraft.player.closeScreen();
            }
        }));
        this.xField = new TextFieldWidget(this.font, leftCorner+60, topCorner+10, 75, 10, "") {
            @Override
            public void writeText(String text) {
                if(BallisticMissileScreen.this.isTextValid(text)) {
                    super.writeText(text);
                }
            }
        };
        this.yField = new TextFieldWidget(this.font, leftCorner+60, topCorner+25, 75, 10, "") {
            @Override
            public void writeText(String text) {
                if(BallisticMissileScreen.this.isTextValid(text)) {
                    super.writeText(text);
                }
            }
        };
        this.zField = new TextFieldWidget(this.font, leftCorner+60, topCorner+40, 75, 10, "") {
            @Override
            public void writeText(String text) {
                if(BallisticMissileScreen.this.isTextValid(text)) {
                    super.writeText(text);
                }
            }
        };
        this.children.add(this.xField);
        this.children.add(this.yField);
        this.children.add(this.zField);
        this.buttonSave.active = !this.missile.isLaunched();
        this.buttonLaunch.active = !this.missile.isLaunched();
    }

    private boolean isTextValid(String text) {
        for(int i = 0; i < text.length(); i++) {
            char character = text.charAt(i);
            if(!(character == '-' || Character.isDigit(character))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void tick() {
        super.tick();
        this.xField.tick();
        this.yField.tick();
        this.zField.tick();
        this.buttonSave.active = !this.missile.isLaunched();
        this.buttonLaunch.active = !this.missile.isLaunched();
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
        String posX = "X: " + String.valueOf(this.missile.getTargetX());
        String posY = "Y: " + String.valueOf(this.missile.getTargetY()*2);
        String posZ = "Z: " + String.valueOf(this.missile.getTargetZ());
        this.font.drawString(posX, ((leftCorner+this.xSize/2)-25)-40, topCorner+9, 0xFFFFFF);
        this.font.drawString(posY, ((leftCorner+this.xSize/2)-25)-40, topCorner+24, 0xFFFFFF);
        this.font.drawString(posZ, ((leftCorner+this.xSize/2)-25)-40, topCorner+39, 0xFFFFFF);
        this.xField.render(mouseX, mouseY, partialTicks);
        this.yField.render(mouseX, mouseY, partialTicks);
        this.zField.render(mouseX, mouseY, partialTicks);
    }

}
