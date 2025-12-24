package com.battlefactions;

import com.battlefactions.guild.Guild;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.EntityArgument;
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
            context.getSource().sendFailure(Component.translatable("faction_already_exist_alert", guildName));
            return 0;
        }

        // verifico se o player já esta em uma guild
        for(Guild g : state.getGuilds().values()) {
            if (g.getMembers().contains(player.getName().getString())) {
                context.getSource().sendFailure(Component.translatable("already_stay_in_faction_alert", guildName));
                return 0;
            }
        }

        Guild guild = new Guild();
        guild.setName(guildName);
        guild.getMembers().add(player.getName().getString());
        guild.setOwner(player.getName().getString());

        state.addGuild(guildName, guild);

        context.getSource().sendSuccess(() -> Component.literal("faction_create_alert"), false);
        context.getSource().sendSuccess(() -> Component.literal("guild successfully created"), false);

        return 1;
    }

    public final static int list(CommandContext<CommandSourceStack> context) {
        MinecraftServer server = context.getSource().getServer();
        ServerPlayer player = context.getSource().getPlayer();
        GuildWorldState state = GuildWorldState.get(server);

        if(state.getGuilds().size()==0) {
            context.getSource().sendFailure(Component.translatable("no_faction_exist_alert"));
            return 0;
        }

        context.getSource().sendSuccess(
                () -> Component.literal("Factions:"),
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

    public final static int delete(CommandContext<CommandSourceStack> context) {
        MinecraftServer server = context.getSource().getServer();
        ServerPlayer player = context.getSource().getPlayer();
        GuildWorldState state = GuildWorldState.get(server);

        String guildName = StringArgumentType.getString(context, "name");

        for(Guild g : state.getGuilds().values()) {
            if(!g.getName().equals(guildName)) {
                continue;
            }
            if(g.getOwner().equals(player.getName().getString())) {
                context.getSource().sendFailure(Component.translatable("factions_delete_alert", guildName));
                state.getGuilds().remove(guildName);
                state.setDirty();
                return 1;
            }
            context.getSource().sendFailure(Component.translatable("need_be_factions_owner_alert", guildName));
            return 0;
        }
        context.getSource().sendFailure(Component.translatable("faction_not_found_alert", guildName));
        return 1;
    }

    public final static int leave(CommandContext<CommandSourceStack> context) {
        MinecraftServer server = context.getSource().getServer();
        ServerPlayer player = context.getSource().getPlayer();
        GuildWorldState state = GuildWorldState.get(server);

        String guildName = StringArgumentType.getString(context, "name");

        for(Guild g : state.getGuilds().values()) {
            if(!g.getName().equals(guildName)) {
                continue;
            }
            if(!g.getMembers().contains(player.getName().getString())) {
                context.getSource().sendFailure(Component.translatable("not_member_faction_alert", guildName));
                return 0;
            }

            state.getGuilds().remove(guildName);
            context.getSource().sendSuccess(
                    () -> Component.literal("you leave the guild " + guildName + "!"),
                    false
            );
            state.setDirty();
            return 1;
        }
        context.getSource().sendFailure(Component.translatable("faction_not_found_alert", guildName));
        return 0;
    }

    public final static int join(CommandContext<CommandSourceStack> context) {
        MinecraftServer server = context.getSource().getServer();
        ServerPlayer player = context.getSource().getPlayer();
        GuildWorldState state = GuildWorldState.get(server);

        String guildName = StringArgumentType.getString(context, "name");

        if(state.getGuilds().containsKey(guildName)) {
            Guild g = state.getGuilds().get(guildName);
            if(g.getInvited().contains(player.getName().getString())) {
                g.getInvited().remove(player.getName().getString());
                g.getMembers().add(player.getName().getString());

                state.setDirty();
                context.getSource().sendSuccess(
                        () -> Component.literal("you join!"), false);

                return 1;
            }
            context.getSource().sendFailure(Component.translatable("invite_not_found_alert", guildName));
            return 0;
        }
        context.getSource().sendFailure(Component.translatable("faction_not_found_alert", guildName));
        return 0;
    }

    public final static int invite(CommandContext<CommandSourceStack> context) {
        MinecraftServer server = context.getSource().getServer();
        ServerPlayer player = context.getSource().getPlayer();
        GuildWorldState state = GuildWorldState.get(server);

        Guild guild = null;
        for(Guild g : state.getGuilds().values()) {
            if(g.getOwner().equals(player.getName().getString())) {
                guild = g;
                break;
            }
        }

        if(guild == null) {
            context.getSource().sendFailure(Component.translatable("need_be_factions_owner_alert"));
            return 0;
        }


        try {
            ServerPlayer playerInvited = EntityArgument.getPlayer(context, "player_name");
            String playerInvitedName = playerInvited.getName().getString();

            if(guild.getMembers().contains(playerInvitedName)) {
                context.getSource().sendFailure(Component.translatable(playerInvitedName + " already are member of this guild!"));
                return 0;
            }

            if(guild.getInvited().contains(playerInvitedName)) {
                context.getSource().sendFailure(Component.translatable(playerInvitedName + "already invited"));
                return 0;
            }

            guild.getInvited().add(playerInvitedName);
            state.setDirty();

            context.getSource().sendSuccess(
                    () -> Component.literal(playerInvitedName + " invited with successfully!"), false);

            return 1;
        } catch (CommandSyntaxException e) {
            context.getSource().sendFailure(Component.translatable(e.getMessage()));
        }
        context.getSource().sendFailure(Component.translatable("player_not_found_alert"));
        return 0;
    }
}
