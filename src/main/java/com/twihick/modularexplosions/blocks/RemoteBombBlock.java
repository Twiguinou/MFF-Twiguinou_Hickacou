package com.twihick.modularexplosions.blocks;

import com.twihick.modularexplosions.util.ShapeUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFaceBlock;
import net.minecraft.block.material.Material;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.AttachFace;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

public class RemoteBombBlock extends HorizontalFaceBlock {

    public static final VoxelShape FLOOR_SHAPE = Block.makeCuboidShape(4.25D, 0.0D, 6.0D, 12.5D, 2.0D, 10.0D);
    public static final VoxelShape CEILING_SHAPE = Block.makeCuboidShape(3.5D, 14.0D, 5.95D, 11.75D, 16.0D, 10.0D);
    public static final VoxelShape WALL_SHAPE = Block.makeCuboidShape(3.5D, 6.0D, 14.0D, 11.75D, 10.0D, 16.0D);

    public RemoteBombBlock() {
        super(Block.Properties
                .create(Material.IRON));
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return getDefaultShape(state, worldIn, pos);
    }

    @Override
    public VoxelShape getRenderShape(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return getDefaultShape(state, worldIn, pos);
    }

    private VoxelShape getDefaultShape(BlockState state, IBlockReader worldIn, BlockPos pos) {
        Direction direction = state.get(HORIZONTAL_FACING);
        switch((AttachFace)state.get(FACE)) {
            case FLOOR:
                return ShapeUtil.rotateShape(FLOOR_SHAPE, direction);
            case WALL:
                return ShapeUtil.rotateShape(WALL_SHAPE, direction);
            default:
                return ShapeUtil.rotateShape(CEILING_SHAPE, direction);
        }
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(HORIZONTAL_FACING, FACE);
    }

}
