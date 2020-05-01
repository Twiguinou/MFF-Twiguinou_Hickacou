package com.twihick.modularexplosions.common.registry;

import com.twihick.modularexplosions.Main;
import com.twihick.modularexplosions.StringID;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.world.World;
import net.minecraftforge.event.RegistryEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class CommonRegistryMethods {

    private static final Logger regLog = LogManager.getLogger();

    private static final List<Item> ITEMS = new ArrayList<>();
    private static final List<Block> BLOCKS = new ArrayList<>();
    private static final List<EntityType<?>> ENTITIES = new ArrayList<>();
    private static final List<TileEntityType<?>> TILE_ENTITIES = new ArrayList<>();

    public static <T extends Item> Item buildItem(Class<T> clazz, String label) {
        Item item = null;
        try {
            item = clazz.newInstance().setRegistryName(createName(label));
            ITEMS.add(item);
        }catch(InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return item;
    }

    public static <T extends Item> Item replaceItem(Class<T> clazz, String label) {
        Item item = null;
        try {
            item = clazz.newInstance().setRegistryName("minecraft:"+label);
            ITEMS.add(item);
        }catch(InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return item;
    }

    /*
    With auto-generated Block-Item.
     */
    public static <T extends Block> Block buildBlock(Class<T> clazz, String label, @Nullable ItemGroup group) {
        Block block = null;
        try {
            block = clazz.newInstance().setRegistryName(createName(label));
            BLOCKS.add(block);
            ITEMS.add(new BlockItem(block, new Item.Properties().group(group != null ? group : Main.getGroup())).setRegistryName(block.getRegistryName()));
        }catch(InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return block;
    }

    public static <T extends Block> Block buildBlockOnly(Class<T> clazz, String label) {
        Block block = null;
        try {
            block = clazz.newInstance().setRegistryName(createName(label));
            BLOCKS.add(block);
        }catch(InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return block;
    }

    public static <T extends Entity> EntityType<T> buildEntity(Class<T> clazz, EntityClassification category, float width, float height, String label) {
        final String finalId = createName(label);
        EntityType<T> entity = null;
        entity = EntityType.Builder.<T>create((type, world) -> {
            Entity tempEntity = null;
            try {
                tempEntity = clazz.getConstructor(World.class).newInstance(world);
            }catch(InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
            return (T) tempEntity;
        }, category).size(width, height).setCustomClientFactory((spawnEntity, world) -> {
            Entity tempEntity = null;
            try {
                tempEntity = clazz.getConstructor(World.class).newInstance(world);
            }catch(InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
            return (T) tempEntity;
        }).build(finalId);
        entity.setRegistryName(finalId);
        ENTITIES.add(entity);
        return entity;
    }

    public static <T extends TileEntity> TileEntityType<T> buildTileEntity(Class<T> clazz, String label, Block... blocks) {
        TileEntityType<? extends TileEntity> te = TileEntityType.Builder.create(() -> {
            TileEntity tempTe = null;
            try {
                tempTe = clazz.newInstance();
            }catch(InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
            return tempTe;
        }, blocks).build(null);
        te.setRegistryName(createName(label));
        TILE_ENTITIES.add(te);
        return (TileEntityType<T>) te;
    }

    public static void registerItems(RegistryEvent.Register<Item> event) {
        if(!ITEMS.isEmpty()) {
            for(Item item : ITEMS) {
                event.getRegistry().register(item);
                if(item instanceof BlockItem) {
                    regLog.info("Block Item with id:" + item.getRegistryName() + " has been registered.");
                }else {
                    regLog.info("Item with id:" + item.getRegistryName() + " has been registered.");
                }
            }
            ITEMS.clear();
        }
    }

    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        if(!BLOCKS.isEmpty()) {
            for(Block block : BLOCKS) {
                event.getRegistry().register(block);
                regLog.info("Block with id:" + block.getRegistryName() + " has been registered.");
            }
            BLOCKS.clear();
        }
    }

    public static void registerEntities(RegistryEvent.Register<EntityType<?>> event) {
        if(!ENTITIES.isEmpty()) {
            for(EntityType<?> entity : ENTITIES) {
                event.getRegistry().register(entity);
                regLog.info("Entity with id:" + entity.getRegistryName() + " has been registered.");
            }
            ENTITIES.clear();
        }
    }

    public static void registerTileEntities(RegistryEvent.Register<TileEntityType<?>> event) {
        if(!TILE_ENTITIES.isEmpty()) {
            for(TileEntityType<?> te : TILE_ENTITIES) {
                event.getRegistry().register(te);
                regLog.info("Tile Entity with id:" + te.getRegistryName() + " has been registered.");
            }
            TILE_ENTITIES.clear();
        }
    }

    private static String createName(String label) {
        return StringID.ID + ":" + label;
    }

}
