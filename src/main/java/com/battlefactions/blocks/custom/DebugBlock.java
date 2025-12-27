package com.battlefactions.blocks.custom;

import com.battlefactions.BattleFactionsMod;
import com.battlefactions.attachments.FactionAttachment;
import com.battlefactions.block_entitys.custom.FlagBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class DebugBlock extends Block {
    public DebugBlock(Properties properties) {
        super(properties);
    }


    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {
        if (world.isClientSide()) {
            return InteractionResult.FAIL;
        }

        // Debug area
        String s = FactionAttachment.get(player).getCurrentFaction();
        BattleFactionsMod.LOGGER.info(s);


        return InteractionResult.SUCCESS;

    }

}
