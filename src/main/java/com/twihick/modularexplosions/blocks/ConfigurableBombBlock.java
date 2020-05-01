package com.twihick.modularexplosions.blocks;

import com.twihick.modularexplosions.properties.ExtendedBlockStateProperties;
import com.twihick.modularexplosions.tileentities.ConfigurableBombTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.state.BooleanProperty;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;

public class ConfigurableBombBlock extends AbstractFacingAlignedBlock {

    public static final BooleanProperty ACTIVATED = ExtendedBlockStateProperties.ACTIVATED;

    public ConfigurableBombBlock() {
        super(Block.Properties
                .create(Material.IRON));
        this.setDefaultState(this.getDefaultState().with(ACTIVATED, Boolean.valueOf(false)));
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader worldIn) {
        return new ConfigurableBombTileEntity();
    }

}
