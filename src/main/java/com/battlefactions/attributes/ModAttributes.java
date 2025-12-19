package com.battlefactions.attributes;

import com.battlefactions.BattleFactionsMod;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;

public class ModAttributes {

    // Exemple of custom attribute creation
    public static final Holder<Attribute> TEST_ATTRIBUTE = register(
            "test_attribute",
            8.0,
            0.0,
            Double.MAX_VALUE, // Max value of Double type
            false // No sync with client.
    );



    private static Holder<Attribute> register(
            String name, double defaultValue, double minValue, double maxValue, boolean syncedWithClient
            // name -> Name of attribute.
            // DefaultValue -> Default Value :).
            // minValue -> minimum value.
            // maxValue -> maximum value.
            // synceWithClient -> If attribute will be synchronized with client.
    ) {
        Identifier identifier = Identifier.fromNamespaceAndPath(BattleFactionsMod.MOD_ID, name);
        Attribute entityAttribute = new RangedAttribute(
                identifier.toLanguageKey(),
                defaultValue,
                minValue,
                maxValue
        ).setSyncable(syncedWithClient);

        return Registry.registerForHolder(BuiltInRegistries.ATTRIBUTE, identifier, entityAttribute);
    }

    public static void initialize() {

    }

}
