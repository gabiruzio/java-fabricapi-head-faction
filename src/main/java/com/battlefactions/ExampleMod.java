package com.battlefactions;

import com.battlefactions.blocks.ModBlocks;
import com.battlefactions.command.GuildCommand;
import com.battlefactions.creative.tab.CreativeTab;
import com.battlefactions.items.ModItems;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExampleMod implements ModInitializer {
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

	}
}