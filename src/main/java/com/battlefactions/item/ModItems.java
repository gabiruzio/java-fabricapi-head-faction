package com.battlefactions.item;

import com.battlefactions.BattleFactionsMod;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;

import java.util.function.Function;

public class ModItems {

    // Exemple for How to add Item
    public static final Item TEST_ITEM = register(
            "test_item",
            Item::new,
            new Item.Properties()
    );
    //Example for how to a food item
    public static final Item CUCUMBER = register(
            "cucumber",
            Item::new,
            new Item.Properties().food(
                    new FoodProperties.Builder()
                            .nutrition(8)              // fome restaurada
                            .saturationModifier(0.6F)  // saturação
                            .build()
            )
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




