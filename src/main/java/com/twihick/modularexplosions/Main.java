package com.twihick.modularexplosions;

import com.twihick.modularexplosions.common.registry.ItemsList;
import com.twihick.modularexplosions.configs.CommonConfig;
import com.twihick.modularexplosions.inventory.PlayerContainerOverride;
import com.twihick.modularexplosions.ops.ClientOperationSide;
import com.twihick.modularexplosions.ops.ServerOperationSide;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.screen.inventory.CreativeScreen;
import net.minecraft.client.gui.screen.inventory.InventoryScreen;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.GuiContainerEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

@Mod(StringID.ID)
public class Main {

    private static Logger logger = LogManager.getLogger();
    private static final ServerOperationSide ops = DistExecutor.runForDist(() -> ClientOperationSide::new, () -> ServerOperationSide::new);
    private static final ItemGroup group = new ItemGroup(StringID.ID) {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(ItemsList.THROWABLE_DYNAMITE);
        }
    };

    private static Field xPosField;
    private static Field yPosField;
    private static Field inventoryField;
    private static Field containerField;

    public Main() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, CommonConfig.commonConfigSpec);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, CommonConfig.clientConfigSpec);
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, CommonConfig.serverConfigSpec);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::common);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::client);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::server);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void common(final FMLCommonSetupEvent event) {
        ops.common(event);
    }

    private void client(final FMLClientSetupEvent event) {
        ops.client(event);
    }

    private void server(final FMLServerStartingEvent event) {
        ops.server(event);
    }

    public static Logger getLogger() {
        return logger;
    }

    public static ItemGroup getGroup() {
        return group;
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public void onPlayerRenderScreen(GuiContainerEvent.DrawBackground event) {
        ContainerScreen screen = event.getGuiContainer();
        if(screen instanceof InventoryScreen) {
            InventoryScreen inventoryScreen = (InventoryScreen) screen;
            int left = inventoryScreen.getGuiLeft();
            int top = inventoryScreen.getGuiTop();
            inventoryScreen.getMinecraft().getTextureManager().bindTexture(ContainerScreen.INVENTORY_BACKGROUND);
            Screen.blit(left+76, top+7, 18, 18, 76, 61, 18, 18, 256, 256);
        }else if(screen instanceof CreativeScreen) {
            CreativeScreen creativeScreen = (CreativeScreen) screen;
            if(creativeScreen.getSelectedTabIndex() == ItemGroup.INVENTORY.getIndex()) {
                int left = creativeScreen.getGuiLeft();
                int top = creativeScreen.getGuiTop();
                creativeScreen.getMinecraft().getTextureManager().bindTexture(ContainerScreen.INVENTORY_BACKGROUND);
                Screen.blit(left+126, top+19, 18, 18, 76, 61, 18, 18, 256, 256);
            }
        }
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public void onTextureStitch(TextureStitchEvent.Pre event) {
        if(event.getMap().getTextureLocation().equals(AtlasTexture.LOCATION_BLOCKS_TEXTURE)) {
            event.addSprite(PlayerContainerOverride.EMPTY_BELT_SLOT);
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static void patchCreativeSlots(CreativeScreen.CreativeContainer creativeContainer) {
        creativeContainer.inventorySlots.stream().filter(slot -> slot.inventory instanceof PlayerInventory && slot.getSlotIndex() == 41).findFirst().ifPresent(slot -> {
            Main.setSlotPosition(slot, 127, 20);
        });
    }

    public static void onPlayerInit(PlayerEntity playerIn) {
        if(inventoryField == null) {
            inventoryField = getFieldAndSetAccessible(PlayerEntity.class, "field_71071_by");
        }
        if(containerField == null) {
            containerField = getFieldAndSetAccessible(PlayerEntity.class, "field_71069_bz");
        }
        try {
            PlayerInventory inventory = new PlayerInventory(playerIn);
            inventoryField.set(playerIn, inventory);
            PlayerContainerOverride container = new PlayerContainerOverride(inventory, !playerIn.world.isRemote, playerIn);
            containerField.set(playerIn, container);
            playerIn.openContainer = container;
        }catch(IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private static Field getFieldAndSetAccessible(Class clazz, String fieldName) {
        Field field = ObfuscationReflectionHelper.findField(clazz, fieldName);
        field.setAccessible(true);
        try {
            Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
        }catch(IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
        return field;
    }

    public static int getCreativeSlotMax(ServerPlayerEntity player) {
        return 46;
    }

    private static void setSlotPosition(Slot slot, int x, int y) {
        try {
            if(xPosField == null) {
                Field xPos = ObfuscationReflectionHelper.findField(Slot.class, "field_75223_e");
                xPos.setAccessible(true);
                Field xPosModifiers = Field.class.getDeclaredField("modifiers");
                xPosModifiers.setAccessible(true);
                xPosModifiers.setInt(xPos, xPos.getModifiers() & ~Modifier.FINAL);
                xPosField = xPos;
            }
            if(yPosField == null) {
                Field yPos = ObfuscationReflectionHelper.findField(Slot.class, "field_75221_f");
                yPos.setAccessible(true);
                Field yPosModifiers = Field.class.getDeclaredField("modifiers");
                yPosModifiers.setAccessible(true);
                yPosModifiers.setInt(yPos, yPos.getModifiers() & ~Modifier.FINAL);
                yPosField = yPos;
            }
            xPosField.set(slot, x);
            yPosField.set(slot, y);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

}
