package com.battlefactions.event;

import com.battlefactions.blocks.ModBlocks;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;

public class DeathEvent {

    public static void register(){
        ServerLivingEntityEvents.AFTER_DEATH.register((entity, source) -> {
            // if entity die for Player
            if (!(entity instanceof ServerPlayer deadPlayer)) return;

            // Onlu if dead case for dead for other player
            //if (source.getEntity() instanceof ServerPlayer killer) {


            // Posição onde o jogador morreu
            BlockPos pos = deadPlayer.getOnPos();

            // Cria o item que você quer dropar (ex: um diamante)
            ItemStack stack = new ItemStack(ModBlocks.VICTORY_SYMBOL_BLOCK); // mude para o item que quiser


            // Dropa a pilha no mundo onde morreu
            ServerLevel world = (ServerLevel) deadPlayer.level() ;
            ItemEntity ie = new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), stack);
            deadPlayer.level().addFreshEntity(ie);


            //}
        });
    }

}
