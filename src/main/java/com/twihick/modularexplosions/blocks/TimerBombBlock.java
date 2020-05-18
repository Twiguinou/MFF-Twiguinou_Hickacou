package com.twihick.modularexplosions.blocks;

import com.twihick.modularexplosions.ops.ClientOperationSide;
import com.twihick.modularexplosions.properties.ExtendedBlockStateProperties;
import com.twihick.modularexplosions.tileentities.TimerBombTileEntity;
import com.twihick.modularexplosions.util.ShapeUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class TimerBombBlock extends AbstractFacingAlignedBlock {

    public static final BooleanProperty ACTIVATED = ExtendedBlockStateProperties.ACTIVATED;
    public static final VoxelShape SHAPE = Block.makeCuboidShape(0.25D, 0.0D, 0.25D, 15.75D, 15.0D, 15.75D);

    public TimerBombBlock() {
        super(Block.Properties
                .create(Material.IRON)
                .hardnessAndResistance(2.5F));
        this.setDefaultState(this.getStateContainer().getBaseState().with(ACTIVATED, Boolean.valueOf(false)));
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return ShapeUtil.rotateShape(SHAPE, state.get(DIRECTION));
    }

    @Override
    public VoxelShape getRenderShape(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return ShapeUtil.rotateShape(SHAPE, state.get(DIRECTION));
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult rtr) {
        if(!state.get(this.ACTIVATED).booleanValue()) {
            ClientOperationSide.Helpers.openTimerBombScreen(worldIn, pos);
        }
        return ActionResultType.SUCCESS;
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader worldIn) {
        return new TimerBombTileEntity();
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        super.fillStateContainer(builder);
        builder.add(ACTIVATED);
    }

}
