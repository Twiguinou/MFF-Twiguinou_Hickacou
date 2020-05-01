package com.twihick.modularexplosions.blocks;

import com.twihick.modularexplosions.client.gui.ConfigurableBombScreen;
import com.twihick.modularexplosions.tileentities.ConfigurableBombTileEntity;
import com.twihick.modularexplosions.util.world.CustomExplosion;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class ConfigurableBombBlock extends Block {

    public ConfigurableBombBlock() {
        super(Block.Properties
                .create(Material.IRON));
    }

    @Override
    public void catchFire(BlockState state, World world, BlockPos pos, @Nullable net.minecraft.util.Direction face, @Nullable LivingEntity igniter) {
        explode(world, pos);
    }

    @Override
    public void onBlockAdded(BlockState state, World worldIn, BlockPos pos, BlockState oldState, boolean isMoving) {
        if(oldState.getBlock() != state.getBlock()) {
            if(worldIn.isBlockPowered(pos)) {
                catchFire(state, worldIn, pos, null, null);
                worldIn.removeBlock(pos, false);
            }
        }
    }

    @Override
    public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
        if(worldIn.isBlockPowered(pos)) {
            catchFire(state, worldIn, pos, null, null);
            worldIn.removeBlock(pos, false);
        }
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult p_225533_6_) {
        ItemStack itemstack = player.getHeldItem(handIn);
        Item item = itemstack.getItem();
        if(item != Items.FLINT_AND_STEEL && item != Items.FIRE_CHARGE) {
            Minecraft.getInstance().displayGuiScreen(new ConfigurableBombScreen((ConfigurableBombTileEntity)worldIn.getTileEntity(pos)));
            return super.onBlockActivated(state, worldIn, pos, player, handIn, p_225533_6_);
        }else {
            catchFire(state, worldIn, pos, p_225533_6_.getFace(), player);
            worldIn.setBlockState(pos, Blocks.AIR.getDefaultState(), 11);
            if(!player.isCreative()) {
                if(item == Items.FLINT_AND_STEEL) {
                    itemstack.damageItem(1, player, (p_220287_1_) -> {
                        p_220287_1_.sendBreakAnimation(handIn);
                    });
                }else {
                    itemstack.shrink(1);
                }
            }
            return ActionResultType.SUCCESS;
        }
    }

    private static void explode(World worldIn, BlockPos pos) {
        if(!worldIn.isRemote) {
            ConfigurableBombTileEntity bomb = (ConfigurableBombTileEntity) worldIn.getTileEntity(pos);
            CustomExplosion explosion = new CustomExplosion(worldIn, pos, bomb.radius_one/2, 0);
            explosion.explodeExcluding(null, bomb.radius_two/2);
        }
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

    @Override
    public void onProjectileCollision(World worldIn, BlockState state, BlockRayTraceResult rtr, Entity projectile) {
        if(!worldIn.isRemote && projectile instanceof AbstractArrowEntity) {
            AbstractArrowEntity abstractarrowentity = (AbstractArrowEntity) projectile;
            Entity entity = abstractarrowentity.getShooter();
            if(abstractarrowentity.isBurning()) {
                BlockPos blockpos = rtr.getPos();
                catchFire(state, worldIn, blockpos, null, entity instanceof LivingEntity ? (LivingEntity)entity : null);
                worldIn.removeBlock(blockpos, false);
            }
        }
    }

}
