package com.example.command;

import com.example.GuildActions;
import com.mojang.brigadier.CommandDispatcher;
import com.sun.jdi.connect.Connector;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import com.mojang.brigadier.arguments.StringArgumentType;

public class GuildCommand {

    public final static void register() {

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            /*dispatcher.register(Commands.literal("guild").executes(context -> {
                context.getSource().sendSuccess(() -> Component.literal("Called /guild."), false);
                return 1;
            }));*/

            dispatcher.register(
                    Commands.literal("guild")
                    .then(Commands.literal("create")
                           .then(Commands.argument("name", StringArgumentType.greedyString())
                                   .executes(GuildActions::create))));

            dispatcher.register(
                    Commands.literal("guild")
                    .then(Commands.literal("list").executes(GuildActions::list))
            );

            dispatcher.register(
                Commands.literal("guild")
                        .then(Commands.literal("delete")
                                .then(Commands.argument("name", StringArgumentType.greedyString()).executes(GuildActions::delete))
            ));

            dispatcher.register(
                    Commands.literal("guild")
                            .then(Commands.literal("leave")
                                    .then(Commands.argument("name", StringArgumentType.greedyString()).executes(GuildActions::leave))
            ));

            dispatcher.register(
                    Commands.literal("guild")
                            .then(Commands.literal("join")
                                    .then(Commands.argument("name", StringArgumentType.greedyString()).executes(GuildActions::join))
            ));

            dispatcher.register(
                    Commands.literal("guild")
                            .then(Commands.literal("invite")
                                    .then(Commands.argument("player_name", EntityArgument.player())
                                            .executes(GuildActions::invite))));

        });

    }
}
