package com.battlefactions.command;

import com.battlefactions.world.data.FactionActions;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import com.mojang.brigadier.arguments.StringArgumentType;

public class FactionCommand {

    public final static void register() {

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            /*dispatcher.register(Commands.literal("guild").executes(context -> {
                context.getSource().sendSuccess(() -> Component.literal("Called /guild."), false);
                return 1;
            }));*/

            dispatcher.register(
                    Commands.literal("faction")
                    .then(Commands.literal("create")
                           .then(Commands.argument("name", StringArgumentType.greedyString())
                                   .executes(FactionActions::create))));

            dispatcher.register(
                    Commands.literal("faction")
                    .then(Commands.literal("list").executes(FactionActions::list))
            );

            dispatcher.register(
                Commands.literal("faction")
                        .then(Commands.literal("delete")
                                .then(Commands.argument("name", StringArgumentType.greedyString()).executes(FactionActions::delete))
            ));

            dispatcher.register(
                    Commands.literal("faction")
                            .then(Commands.literal("leave")
                                    .then(Commands.argument("name", StringArgumentType.greedyString()).executes(FactionActions::leave))
            ));

            dispatcher.register(
                    Commands.literal("faction")
                            .then(Commands.literal("join")
                                    .then(Commands.argument("name", StringArgumentType.greedyString()).executes(FactionActions::join))
            ));

            dispatcher.register(
                    Commands.literal("faction")
                            .then(Commands.literal("invite")
                                    .then(Commands.argument("player_name", EntityArgument.player())
                                            .executes(FactionActions::invite))));

        });

    }
}
