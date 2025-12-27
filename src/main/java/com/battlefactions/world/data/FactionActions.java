package com.battlefactions.world.data;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;

public class FactionActions {

    public static final int create(CommandContext<CommandSourceStack> context) {

        MinecraftServer server = context.getSource().getServer();
        ServerPlayer player = context.getSource().getPlayer();
        FactionWorldState state = FactionWorldState.get(server);

        String guildName = StringArgumentType.getString(context, "name");

        // verifo se a guilda já existe
        if (state.getGuilds().containsKey(guildName)) {
            context.getSource().sendFailure(Component.translatable("faction_already_exist_alert", guildName));
            return 0;
        }

        // verifico se o player já esta em uma guild
        for(Faction g : state.getGuilds().values()) {
            if (g.getMembers().contains(player.getName().getString())) {
                context.getSource().sendFailure(Component.translatable("already_stay_in_faction_alert", guildName));
                return 0;
            }
        }

        Faction faction = new Faction();
        faction.setName(guildName);
        faction.getMembers().add(player.getName().getString());
        faction.setOwner(player.getName().getString());

        state.addGuild(guildName, faction);

        context.getSource().sendSuccess(() -> Component.translatable("faction_create_alert"), false);

        return 1;
    }

    public final static int list(CommandContext<CommandSourceStack> context) {
        MinecraftServer server = context.getSource().getServer();
        ServerPlayer player = context.getSource().getPlayer();
        FactionWorldState state = FactionWorldState.get(server);

        if(state.getGuilds().size()==0) {
            context.getSource().sendFailure(Component.translatable("no_faction_exist_alert"));
            return 0;
        }

        context.getSource().sendSuccess(
                () -> Component.literal("Factions:"),
                false
        );

        for(Faction g : state.getGuilds().values()) {
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
        FactionWorldState state = FactionWorldState.get(server);

        String guildName = StringArgumentType.getString(context, "name");

        for(Faction g : state.getGuilds().values()) {
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
        FactionWorldState state = FactionWorldState.get(server);

        String guildName = StringArgumentType.getString(context, "name");

        for(Faction g : state.getGuilds().values()) {
            if(!g.getName().equals(guildName)) {
                continue;
            }
            if(!g.getMembers().contains(player.getName().getString())) {
                context.getSource().sendFailure(Component.translatable("not_member_faction_alert", guildName));
                return 0;
            }

            state.getGuilds().remove(guildName);
            context.getSource().sendSuccess(
                    () -> Component.translatable("leave_faction_alert").append(Component.literal( ": " + guildName)),
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
        FactionWorldState state = FactionWorldState.get(server);

        String guildName = StringArgumentType.getString(context, "name");

        if(state.getGuilds().containsKey(guildName)) {
            Faction g = state.getGuilds().get(guildName);
            if(g.getInvited().contains(player.getName().getString())) {
                g.getInvited().remove(player.getName().getString());
                g.getMembers().add(player.getName().getString());

                state.setDirty();
                context.getSource().sendSuccess(
                        () -> Component.literal("you join!"), false);

                return 1;
            }
            context.getSource().sendFailure(Component.translatable("invite_not_found_alert").append(Component.literal( ": " + guildName)));
            return 0;
        }
        context.getSource().sendFailure(Component.translatable("faction_not_found_alert").append(Component.literal( ": " + guildName)));

        return 0;
    }

    public final static int invite(CommandContext<CommandSourceStack> context) {
        MinecraftServer server = context.getSource().getServer();
        ServerPlayer player = context.getSource().getPlayer();
        FactionWorldState state = FactionWorldState.get(server);

        Faction faction = null;
        for(Faction g : state.getGuilds().values()) {
            if(g.getOwner().equals(player.getName().getString())) {
                faction = g;
                break;
            }
        }

        if(faction == null) {
            context.getSource().sendFailure(Component.translatable("need_be_factions_owner_alert"));
            return 0;
        }


        try {
            ServerPlayer playerInvited = EntityArgument.getPlayer(context, "player_name");
            String playerInvitedName = playerInvited.getName().getString();

            if(faction.getMembers().contains(playerInvitedName)) {
                context.getSource().sendFailure(Component.literal(playerInvitedName + ": ").append(Component.translatable("already_stay_in_faction_alert")));
                return 0;
            }

            if(faction.getInvited().contains(playerInvitedName)) {
                context.getSource().sendFailure(Component.literal(playerInvited + ": ").append(Component.translatable("already_invited_alert")));
                return 0;
            }

            faction.getInvited().add(playerInvitedName);
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
