package com.battlefactions.command;

import com.battlefactions.world.data.GuildActions;
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
                                   .executes(GuildActions::create))));

            dispatcher.register(
                    Commands.literal("faction")
                    .then(Commands.literal("list").executes(GuildActions::list))
            );

            dispatcher.register(
                Commands.literal("faction")
                        .then(Commands.literal("delete")
                                .then(Commands.argument("name", StringArgumentType.greedyString()).executes(GuildActions::delete))
            ));

            dispatcher.register(
                    Commands.literal("faction")
                            .then(Commands.literal("leave")
                                    .then(Commands.argument("name", StringArgumentType.greedyString()).executes(GuildActions::leave))
            ));

            dispatcher.register(
                    Commands.literal("faction")
                            .then(Commands.literal("join")
                                    .then(Commands.argument("name", StringArgumentType.greedyString()).executes(GuildActions::join))
            ));

            dispatcher.register(
                    Commands.literal("faction")
                            .then(Commands.literal("invite")
                                    .then(Commands.argument("player_name", EntityArgument.player())
                                            .executes(GuildActions::invite))));

        });

    }
}
