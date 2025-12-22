package com.battlefactions.item;

import com.battlefactions.BattleFactionsMod;
import com.battlefactions.item.custom.ReinforcedObsidianArmorMaterial;
import com.battlefactions.item.custom.ReinforcedObsidianIngot;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.equipment.ArmorType;

import java.util.function.Function;

public class ModItems {

    // Exemple for How to add Item
    public static final Item TEST_ITEM = register(
            "test_item",
            Item::new,
            new Item.Properties()
    );


    public static final Item REINFORCED_OBSIDIAN_INGOT = register(
            "reinforced_obsidian_ingot",
            ReinforcedObsidianIngot::new,
            new ReinforcedObsidianIngot.Properties()
    );

    public static final Item REINFORCED_OBSIDIAN_HELMET = register(
            "reinforced_obsidian_helmet",
            Item::new,
            new Item.Properties().humanoidArmor(ReinforcedObsidianArmorMaterial.INSTANCE, ArmorType.HELMET)
                    .durability(ArmorType.HELMET.getDurability(ReinforcedObsidianArmorMaterial.BASE_DURABILITY))
    );
    public static final Item REINFORCED_OBSIDIAN_CHESTPLATE = register(
            "reinforced_obsidian_chestplate",
            Item::new,
            new Item.Properties().humanoidArmor(ReinforcedObsidianArmorMaterial.INSTANCE, ArmorType.CHESTPLATE)
                    .durability(ArmorType.CHESTPLATE.getDurability(ReinforcedObsidianArmorMaterial.BASE_DURABILITY))
    );

    public static final Item REINFORCED_OBSIDIAN_LEGGINGS = register(
            "reinforced_obsidian_leggings",
            Item::new,
            new Item.Properties().humanoidArmor(ReinforcedObsidianArmorMaterial.INSTANCE, ArmorType.LEGGINGS)
                    .durability(ArmorType.LEGGINGS.getDurability(ReinforcedObsidianArmorMaterial.BASE_DURABILITY))
    );

    public static final Item REINFORCED_OBSIDIAN_BOOTS = register(
            "reinforced_obsidian_boots",
            Item::new,
            new Item.Properties().humanoidArmor(ReinforcedObsidianArmorMaterial.INSTANCE, ArmorType.BOOTS)
                    .durability(ArmorType.BOOTS.getDurability(ReinforcedObsidianArmorMaterial.BASE_DURABILITY))
    );

    public static final ToolMaterial REINFORCED_OBSIDIAN_TOOL_MATERIAL = new ToolMaterial(
            BlockTags.ALL_SIGNS,
            455,
            5.0F,
            1.5F,
            22,
            ReinforcedObsidianArmorMaterial.REPAIRS_REINFORCED_OBSIDIAN_ARMOR
    );

    public static final Item REINFORCED_OBSIDIAN_SWORD = register(
            "reinforced_obsidian_sword",
            Item::new,
            new Item.Properties().sword(REINFORCED_OBSIDIAN_TOOL_MATERIAL, 1f, 1f)
    );

    public static final Item REINFORCED_OBSIDIAN_PICKAXE = register(
            "reinforced_obsidian_pickaxe",
            Item::new,
            new Item.Properties().pickaxe(REINFORCED_OBSIDIAN_TOOL_MATERIAL, 1f, 1f)
    );

    public static final Item REINFORCED_OBSIDIAN_AXE = register(
            "reinforced_obsidian_axe",
            Item::new,
            new Item.Properties().axe(REINFORCED_OBSIDIAN_TOOL_MATERIAL, 1f, 1f)
    );

    public static final Item REINFORCED_OBSIDIAN_HOE = register(
            "reinforced_obsidian_hoe",
            Item::new,
            new Item.Properties().hoe(REINFORCED_OBSIDIAN_TOOL_MATERIAL, 1f, 1f)
    );

    public static final Item REINFORCED_OBSIDIAN_SHOVEL = register(
            "reinforced_obsidian_shovel",
            Item::new,
            new Item.Properties().shovel(REINFORCED_OBSIDIAN_TOOL_MATERIAL, 1f, 1f)
    );

    public static final Item REINFORCED_OBSIDIAN_OMNI = register(
            "reinforced_obsidian_omni_tool",
            Item::new,
            new ReinforcedObsidianIngot.Properties().stacksTo(1)
                    .sword(REINFORCED_OBSIDIAN_TOOL_MATERIAL, 1f, 1f)
                    .hoe(REINFORCED_OBSIDIAN_TOOL_MATERIAL, 1f, 1f)
                    .pickaxe(REINFORCED_OBSIDIAN_TOOL_MATERIAL, 1f, 1f)
                    .axe(REINFORCED_OBSIDIAN_TOOL_MATERIAL, 1f, 1f)
                    .shovel(REINFORCED_OBSIDIAN_TOOL_MATERIAL, 1f, 1f)
    );

    public static Item register(String name, Function<Item.Properties, Item> itemFactory, Item.Properties settings) {
        // Create the item key.



        ResourceKey<Item> itemKey = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(BattleFactionsMod.MOD_ID, name));

        // Create the item instance.
        Item item = itemFactory.apply(settings.setId(itemKey));

        // Register the item.
        Registry.register(BuiltInRegistries.ITEM, itemKey, item);

        return item;
    }

    public static void inicialize() {
        // Esse método será chamado no onInitialize do mod
    }

}




