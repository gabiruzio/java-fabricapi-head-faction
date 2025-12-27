package com.battlefactions;

import com.battlefactions.attributes.ModAttributes;
import com.battlefactions.block_entitys.ModBlockEntities;
import com.battlefactions.blocks.ModBlocks;
import com.battlefactions.command.FactionCommand;
import com.battlefactions.creative.CreativeTab;
import com.battlefactions.event.ChatEvent;
import com.battlefactions.event.DeathEvent;
import com.battlefactions.item.ModItems;
import net.fabricmc.api.ModInitializer;

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
		ModItems.inicialize();
		ModBlocks.initialize();
		ModBlockEntities.initialize();
		ModAttributes.initialize();
		CreativeTab.inicialize();

		FactionCommand.register();

		// Events
		ChatEvent.register();
		DeathEvent.register();






	}
}