package com.battlefactions.blocks.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class Hearth extends Block {
    public Hearth(Properties properties) { super(properties); }

    @Override
    protected InteractionResult useWithoutItem(BlockState blockState, Level level, BlockPos blockPos, Player player, BlockHitResult blockHitResult) {

        if (level.isClientSide()){ return InteractionResult.FAIL; }

        player.displayClientMessage(Component.nullToEmpty("Hearth Click"), false);
        return super.useWithoutItem(blockState, level, blockPos, player, blockHitResult);
    }
}
