package com.battlefactions.blocks.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.Nullable;

import java.util.Map;


public class VictorySymbolBlock extends Block {
    public static final EnumProperty<Direction> FACING;
    private static final VoxelShape SHAPE;

    public static final BooleanProperty XRAY = BooleanProperty.create("xray");


    public VictorySymbolBlock(Properties properties) {
        super(properties);
        this.registerDefaultState((BlockState)((BlockState)this.stateDefinition.any()).setValue(FACING, Direction.NORTH));
        this.registerDefaultState((BlockState) ((BlockState)this.stateDefinition.any()).setValue(XRAY, false));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
        return (BlockState)this.defaultBlockState().setValue(FACING, blockPlaceContext.getHorizontalDirection().getClockWise());
    }

    @Override
    protected VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return SHAPE;
    }

    @Override
    protected BlockState rotate(BlockState blockState, Rotation rotation) {
        return (BlockState)blockState.setValue(FACING, rotation.rotate((Direction)blockState.getValue(FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{FACING});
        builder.add(new Property[]{XRAY});
    }

    static {
        FACING = HorizontalDirectionalBlock.FACING;
        SHAPE = Block.column((double)8.0F, (double)0.0F, (double)8.0F);
    }


    public void setPlacedBy(Level world, BlockPos pos, BlockState blockState, @Nullable LivingEntity placer, ItemStack itemStack) {

        // Bloco abaixo da posição onde seu bloco foi colocado
        BlockPos below = pos.relative(Direction.DOWN);

        // Se o bloco abaixo não for um baú, remove este bloco
        if (!world.getBlockState(below).is(Blocks.DIRT)) {
            world.removeBlock(pos, false);

            // Devolve o item ao jogador, se houver quem colocou
            if (placer != null) {
                placer.setItemInHand(InteractionHand.MAIN_HAND, itemStack.copy());
            }
        }
    }











}

