package com.battlefactions.blocks.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.entity.player.Player;


public class FlagBlock extends Block {

    // Define se é a parte de baixo ou de cima
    public static final EnumProperty<DoubleBlockHalf> HALF =
            BlockStateProperties.DOUBLE_BLOCK_HALF;

    public static final EnumProperty<Direction> FACING =
            BlockStateProperties.HORIZONTAL_FACING;


    public FlagBlock(Properties properties) {
        super(properties);// Estado padrão: parte de baixo
        this.registerDefaultState(
                this.stateDefinition.any().setValue(HALF, DoubleBlockHalf.LOWER)
        );
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {

        // Só coloca se o bloco de cima estiver livre
        if (context.getLevel().getBlockState(context.getClickedPos().above())
                .canBeReplaced(context)) {
            return this.defaultBlockState()
                    .setValue(HALF, DoubleBlockHalf.LOWER)
                    .setValue(BlockStateProperties.HORIZONTAL_FACING, context.getHorizontalDirection().getOpposite());
        }
        return null;
    }

    @Override
    public void setPlacedBy(
            Level level,
            BlockPos pos,
            BlockState state,
            net.minecraft.world.entity.LivingEntity placer,
            net.minecraft.world.item.ItemStack stack
    ) { // Coloca a parte de cima automaticamente
        level.setBlock(
                pos.above(),
                state.setValue(HALF, DoubleBlockHalf.UPPER),
                3
        );
    }

    @Override
    public BlockState playerWillDestroy(
            Level level,
            BlockPos pos,
            BlockState state,
            Player player
    ) { // Remove a outra metade ao quebrar
        DoubleBlockHalf half = state.getValue(HALF);
        // Define a posição da outra metade
        BlockPos otherPos = (half == DoubleBlockHalf.LOWER) ? pos.above() : pos.below();

        if (level.getBlockState(otherPos).getBlock() == this) {
            level.removeBlock(otherPos, false);
        }

        return super.playerWillDestroy(level, pos, state, player);
    }


    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(HALF, BlockStateProperties.HORIZONTAL_FACING);
    // Registra as propriedades do bloco
    }
}
