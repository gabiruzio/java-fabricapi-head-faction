package com.battlefactions.item.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class OmniTool extends Item {

    private final ToolMaterial material;

    public OmniTool(ToolMaterial material, Item.Properties properties) {
        super(properties);

        this.material = material;
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        if (
                state.is(BlockTags.MINEABLE_WITH_PICKAXE) ||
                        state.is(BlockTags.MINEABLE_WITH_AXE) ||
                        state.is(BlockTags.MINEABLE_WITH_SHOVEL) ||
                        state.is(BlockTags.MINEABLE_WITH_HOE)
        ) {
            return material.speed();
        }
        return 1.0F;
    }

    @Override
    public boolean isCorrectToolForDrops(ItemStack stack, BlockState state) {
        return state.is(BlockTags.MINEABLE_WITH_PICKAXE)
                || state.is(BlockTags.MINEABLE_WITH_AXE)
                || state.is(BlockTags.MINEABLE_WITH_SHOVEL)
                || state.is(BlockTags.MINEABLE_WITH_HOE);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState state = level.getBlockState(pos);

        if (!level.isClientSide() &&
                (state.is(Blocks.DIRT) || state.is(Blocks.GRASS_BLOCK))) {

            level.setBlock(pos, Blocks.FARMLAND.defaultBlockState(), 3);

            context.getItemInHand().hurtAndBreak(
                    1,
                    context.getPlayer(),
                    context.getHand()
            );

            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }
}