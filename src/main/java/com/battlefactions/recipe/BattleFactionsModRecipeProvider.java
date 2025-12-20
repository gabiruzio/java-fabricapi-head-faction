package com.battlefactions.recipe;

import java.util.concurrent.CompletableFuture;

import com.battlefactions.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

public class BattleFactionsModRecipeProvider extends FabricRecipeProvider {

    public BattleFactionsModRecipeProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected RecipeProvider createRecipeProvider(HolderLookup.Provider registryLookup, RecipeOutput exporter) {
        return new RecipeProvider(registryLookup, exporter) {
            @Override
            public void buildRecipes() {
                HolderLookup.RegistryLookup<Item> itemLookup = registries.lookupOrThrow(Registries.ITEM);

                shaped(RecipeCategory.MISC, ModItems.REINFORCED_OBSIDIAN_INGOT, 1)
                        .pattern("li")
                        .pattern("il")
                        .define('l', Items.DIAMOND)
                        .define('i', Items.OBSIDIAN)
                        .group("multi_bench")
                        .unlockedBy(getHasName(Items.DIAMOND_AXE), has(Items.DIAMOND_AXE))
                        .save(output);
                /*
                shaped(RecipeCategory.MISC, Items.CRAFTING_TABLE, 4)
                        .pattern("ll")
                        .pattern("ll")
                        .define('l', ItemTags.LOGS) // 'l' means "any log"
                        .group("multi_bench") // Put it in a group called "multi_bench" - groups are shown in one slot in the recipe book
                        .unlockedBy(getHasName(Items.CRAFTING_TABLE), has(Items.CRAFTING_TABLE))
                        .save(output);

                shaped(RecipeCategory.MISC, Items.LOOM, 4)
                        .pattern("ww")
                        .pattern("ll")
                        .define('w', ItemTags.WOOL) // 'w' means "any wool"
                        .define('l', ItemTags.LOGS)
                        .group("multi_bench")
                        .unlockedBy(getHasName(Items.LOOM), has(Items.LOOM))
                        .save(output);

                doorBuilder(Items.OAK_DOOR, Ingredient.of(Items.OAK_BUTTON)) // Using a helper method!
                        .unlockedBy(getHasName(Items.OAK_BUTTON), has(Items.OAK_BUTTON))
                        .save(output);*/

            }
        };
    }



    @Override
    public String getName() {
        return "BattleFactionsModRecipeProvider";
    }


}
