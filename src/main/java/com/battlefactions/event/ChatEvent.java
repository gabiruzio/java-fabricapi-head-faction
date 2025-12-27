package com.battlefactions.event;

import com.battlefactions.BattleFactionsMod;
import net.fabricmc.fabric.api.message.v1.ServerMessageEvents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;


public class ChatEvent {

    public static void register() {

        ServerMessageEvents.ALLOW_CHAT_MESSAGE.register((message, player, b) -> {

            // Ignore commands
            if (message.signedContent().toString().startsWith("/")) {
                return true;
            }

            // message
            Component text = Component.literal(message.signedContent().toString());

            // send
            for (ServerPlayer p : player.level().getServer().getPlayerList().getPlayers()) {
                BattleFactionsMod.LOGGER.info("chat abaixo");
                p.sendSystemMessage(text, false);
            }


            return false;
        });
    }

}

