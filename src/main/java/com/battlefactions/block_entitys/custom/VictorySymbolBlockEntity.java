package com.battlefactions.block_entitys.custom;

import com.battlefactions.block_entitys.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class VictorySymbolBlockEntity extends BlockEntity {

    public VictorySymbolBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntities.VICTORY_SYMBOL_BLOCK_ENTITY, blockPos, blockState);
    }



}
