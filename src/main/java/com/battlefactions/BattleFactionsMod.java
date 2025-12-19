package com.battlefactions;

import com.battlefactions.blocks.ModBlocks;
import com.battlefactions.command.GuildCommand;
import com.battlefactions.creative.tab.CreativeTab;
import com.battlefactions.item.ModItems;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BattleFactionsMod implements ModInitializer {
	public static final String MOD_ID = "battlefactions";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);




	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		// ro
		LOGGER.info("Hello Fabric world!");

		// Don't change order
		ModBlocks.initialize();
		ModItems.inicialize();
		CreativeTab.inicialize();

		GuildCommand.register();

		ServerLivingEntityEvents.AFTER_DEATH.register((entity, source) -> {
			// if entity die for Player
			if (!(entity instanceof ServerPlayer deadPlayer)) return;

			// Onlu if dead case for dead for other player
			//if (source.getEntity() instanceof ServerPlayer killer) {

			// Posição onde o jogador morreu
			BlockPos pos = deadPlayer.getOnPos();

			// Cria o item que você quer dropar (ex: um diamante)
			ItemStack stack = new ItemStack(Items.PLAYER_HEAD); // mude para o item que quiser


			// Dropa a pilha no mundo onde morreu
			ServerLevel world = (ServerLevel) deadPlayer.level() ;
			ItemEntity ie = new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), stack);
			deadPlayer.level().addFreshEntity(ie);


			//}
		});



	}
}