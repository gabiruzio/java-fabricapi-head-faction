package com.battlefactions;

import net.fabricmc.fabric.api.message.v1.ServerMessageEvents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ambient.Bat;


public class ChatListener {

    public static void register() {

        ServerMessageEvents.ALLOW_CHAT_MESSAGE.register((message, player, b) -> {

            // Ignora comandos
            if (message.signedContent().toString().startsWith("/")) {
                return true; // permite comandos normais
            }

            // Monta o texto sem o nome do player
            Component text = Component.literal(message.signedContent().toString());

            // Envia para cada jogador
            for (ServerPlayer p : player.level().getServer().getPlayerList().getPlayers()) {
                BattleFactionsMod.LOGGER.info("chat abaixo");
                p.sendSystemMessage(text, false);
            }

            // Retorna false para impedir o broadcast padrão
            return false;
        });
    }

}

