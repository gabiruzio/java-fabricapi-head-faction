package com.battlefactions.creative;

import com.battlefactions.BattleFactionsMod;
import com.battlefactions.blocks.ModBlocks;
import com.battlefactions.item.ModItems;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;


public class CreativeTab {

    public static final ResourceKey<CreativeModeTab> CUSTOM_ITEM_GROUP_KEY = ResourceKey.create(BuiltInRegistries.CREATIVE_MODE_TAB.key(), Identifier.fromNamespaceAndPath(BattleFactionsMod.MOD_ID, "item_group"));

    public static final CreativeModeTab CUSTOM_ITEM_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(Blocks.PLAYER_HEAD))
            //.icon(() -> new ItemStack(ModBlocks.TROPHY2))
            .title(Component.translatable("itemGroup.battlefactions"))
            .build();


    public static void inicialize(){
        // Register the group.
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, CUSTOM_ITEM_GROUP_KEY, CUSTOM_ITEM_GROUP);

        // -----------------------------------------------
        // Register items to the ModTab item group.
        ItemGroupEvents.modifyEntriesEvent(CUSTOM_ITEM_GROUP_KEY).register(itemGroup -> {
            itemGroup.accept(ModItems.TEST_ITEM);
            itemGroup.accept(ModBlocks.TEST_BLOCK);
            itemGroup.accept(ModItems.CUCUMBER);
            itemGroup.accept(ModBlocks.FLAG);


            itemGroup.accept(ModItems.REINFORCED_OBSIDIAN_INGOT);

            itemGroup.accept(ModItems.REINFORCED_OBSIDIAN_HELMET);
            itemGroup.accept(ModItems.REINFORCED_OBSIDIAN_CHESTPLATE);
            itemGroup.accept(ModItems.REINFORCED_OBSIDIAN_LEGGINGS);
            itemGroup.accept(ModItems.REINFORCED_OBSIDIAN_BOOTS);

            itemGroup.accept(ModItems.REINFORCED_OBSIDIAN_SWORD);
            itemGroup.accept(ModItems.REINFORCED_OBSIDIAN_AXE);
            itemGroup.accept(ModItems.REINFORCED_OBSIDIAN_PICKAXE);
            itemGroup.accept(ModItems.REINFORCED_OBSIDIAN_SHOVEL);
            itemGroup.accept(ModItems.REINFORCED_OBSIDIAN_HOE);
            itemGroup.accept(ModItems.REINFORCED_OBSIDIAN_OMNI);

            itemGroup.accept(ModBlocks.TROPHY_BLOCK);
            itemGroup.accept(ModBlocks.VICTORY_SYMBOL_BLOCK);
            itemGroup.accept(ModBlocks.SHAME_SYMBOL_BLOCK);
        });

        // Add items a exist minecraft tab
        //ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.INGREDIENTS)
                //.register((itemGroup) -> itemGroup.accept(ModItems.TEST_ITEM));


    }
}
