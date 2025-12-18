package com.example;

import com.example.guild.Guild;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;

public class GuildActions {

    public static final int create(CommandContext<CommandSourceStack> context) {

        MinecraftServer server = context.getSource().getServer();
        ServerPlayer player = context.getSource().getPlayer();

        GuildWorldState state = GuildWorldState.get(server);

        String guildName = StringArgumentType.getString(context, "name");

        // verifo se a guilda já existe
        if (state.getGuilds().containsKey(guildName)) {
            context.getSource().sendFailure(Component.translatable("guild already exists!", guildName));
            return 0;
        }

        // verifico se o player já esta em uma guild
        for(Guild g : state.getGuilds().values()) {
            if (g.getMembers().contains(player.getName().getString())) {
                context.getSource().sendFailure(Component.translatable("you already are in a guild!", guildName));
                return 0;
            }
        }

        Guild guild = new Guild();
        guild.setName(guildName);
        guild.getMembers().add(player.getName().getString());
        guild.setOwner(player.getName().getString());

        state.addGuild(guildName, guild);

        context.getSource().sendSuccess(() -> Component.literal("guild successfuly created"), false);

        return 1;
    }

    public final static int list(CommandContext<CommandSourceStack> context) {
        MinecraftServer server = context.getSource().getServer();
        ServerPlayer player = context.getSource().getPlayer();
        GuildWorldState state = GuildWorldState.get(server);

        if(state.getGuilds().size()==0) {
            context.getSource().sendFailure(Component.translatable("no one guild exists!"));
            return 0;
        }

        context.getSource().sendSuccess(
                () -> Component.literal("guilds:"),
                false
        );

        for(Guild g : state.getGuilds().values()) {
            context.getSource().sendSuccess(
                    () -> Component.literal(g.getName()),
                    false
            );
        }

        return 1;
    }
}
