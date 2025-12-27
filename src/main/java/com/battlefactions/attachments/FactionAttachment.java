package com.battlefactions.attachments;

import com.mojang.serialization.Codec;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentSyncPredicate;
import net.fabricmc.fabric.api.attachment.v1.AttachmentTarget;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.resources.Identifier;

public class FactionAttachment {
    private static final AttachmentType<String> CURRENT_FACTION = AttachmentRegistry.create(
            Identifier.fromNamespaceAndPath("battlefactions", "current_faction"),
            builder -> builder
                    .syncWith(ByteBufCodecs.STRING_UTF8, AttachmentSyncPredicate.all())
                    .initializer(() -> "")
                    .persistent(Codec.STRING)
                    .copyOnDeath()
    );

    public static FactionData get(AttachmentTarget target){
        return new FactionData(target);
    }

    public record FactionData(AttachmentTarget target){
        public String getCurrentFaction(){
            return target.getAttachedOrElse(CURRENT_FACTION, "");
        }

        public void setCurrentFaction(String msg){
            target.setAttached(CURRENT_FACTION, msg);
        }
    }

}
