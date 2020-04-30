package com.twihick.modularexplosions.blocks;

import com.twihick.modularexplosions.common.registry.ItemsList;
import com.twihick.modularexplosions.tileentities.RemoteBombTileEntity;
import com.twihick.modularexplosions.util.ShapeUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFaceBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.AttachFace;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import java.util.UUID;

public class RemoteBombBlock extends HorizontalFaceBlock {

    public static final VoxelShape FLOOR_SHAPE = Block.makeCuboidShape(4.25D, 0.0D, 6.1D, 12.5D, 2.0D, 10.0D);
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
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity playerIn, Hand handIn, BlockRayTraceResult rtr) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        if(itemstack.getItem() == ItemsList.REMOTE_CONTROLLER) {
            UUID uuid = playerIn.getUniqueID();
            RemoteBombTileEntity bomb = (RemoteBombTileEntity) worldIn.getTileEntity(pos);
            if(bomb.playerLink == null) {
                bomb.setPlayerLink(uuid);
            }else if(bomb.playerLink != uuid) {
                bomb.setPlayerLink(uuid);
                playerIn.sendMessage(new StringTextComponent("This bomb is now linked !"));
                return ActionResultType.SUCCESS;
            }else {
                playerIn.sendMessage(new StringTextComponent("This bomb is already linked !"));
            }
            return ActionResultType.PASS;
        }
        return ActionResultType.FAIL;
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader worldInf) {
        return new RemoteBombTileEntity();
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(HORIZONTAL_FACING, FACE);
    }

}
