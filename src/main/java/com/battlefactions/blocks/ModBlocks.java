package com.battlefactions.blocks;

import com.battlefactions.BattleFactionsMod;
import com.battlefactions.blocks.custom.ShameSymbolBlock;
import com.battlefactions.blocks.custom.TrophyBlock;
import com.battlefactions.blocks.custom.VictorySymbolBlock;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Function;


public class ModBlocks {

    // Exemple for How to Add Block!!!
    public static final Block TEST_BLOCK = register(
            "test_block",
            Block::new,
            BlockBehaviour.Properties.of()
                    //.noOcclusion() -> para modelo custom (não quadrado)
                    .strength(6.0f, 100.0f)
                    .sound(SoundType.GRASS),
            true
    );


    public static final Block TROPHY_BLOCK = register(
            "trophy_block",
            TrophyBlock::new,
            BlockBehaviour.Properties.of()
                    .noOcclusion()
                    .strength(6.0f, 100.0f)
                    .sound(SoundType.METAL),
            true
    );

    public static final Block VICTORY_SYMBOL_BLOCK = register(
            "victory_symbol_block",
            VictorySymbolBlock::new,
            BlockBehaviour.Properties.of()
                    .noOcclusion()
                    .strength(1.0f, 100.0f)
                    .sound(SoundType.BONE_BLOCK),
            true
    );

    public static final Block SHAME_SYMBOL_BLOCK = register(
            "shame_symbol_block",
            ShameSymbolBlock::new,
            BlockBehaviour.Properties.of()
                    .noOcclusion()
                    .strength(1.0f, 100.0f)
                    .sound(SoundType.BONE_BLOCK),
            true
    );




    private static Block register(String name, Function<BlockBehaviour.Properties, Block> blockFactory, BlockBehaviour.Properties settings, boolean shouldRegisterItem) {
        // Create a registry key for the block
        ResourceKey<Block> blockKey = keyOfBlock(name);
        // Create the block instance
        Block block = blockFactory.apply(settings.setId(blockKey));

        // Sometimes, you may not want to register an item for the block.
        // Eg: if it's a technical block like `minecraft:moving_piston` or `minecraft:end_gateway`
        if (shouldRegisterItem) {
            // Items need to be registered with a different type of registry key, but the ID
            // can be the same.
            ResourceKey<Item> itemKey = keyOfItem(name);

            BlockItem blockItem = new BlockItem(block, new Item.Properties().setId(itemKey).useBlockDescriptionPrefix());
            Registry.register(BuiltInRegistries.ITEM, itemKey, blockItem);
        }

        return Registry.register(BuiltInRegistries.BLOCK, blockKey, block);
    }
    
    private static ResourceKey<Block> keyOfBlock(String name) {

        return ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(BattleFactionsMod.MOD_ID, name));
    }

    private static ResourceKey<Item> keyOfItem(String name) {
        return ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(BattleFactionsMod.MOD_ID, name));
    }

    private static Block registerBlock(String name, Block block) {
        return Registry.register(BuiltInRegistries.BLOCK, Identifier.fromNamespaceAndPath("example", name), block);
    }

    public static void initialize() {

    }

}
