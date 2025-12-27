package com.battlefactions.block_entitys.custom;

import com.battlefactions.BattleFactionsMod;
import com.battlefactions.attachments.FactionAttachment;
import com.battlefactions.block_entitys.ModBlockEntities;
import com.mojang.util.UUIDTypeAdapter;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.UUIDUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.BlockHitResult;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

import static com.mojang.util.UndashedUuid.fromString;


public class FlagBlockEntity extends BlockEntity {

    public FlagBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntities.FLAG_BLOCK_ENTITY, blockPos, blockState);
    }

    private String factionOwner;


    public void setOwner(String guildName){
        factionOwner = guildName;
        setChanged();
    }

    public String getOwner() {
        return factionOwner;
    }


    public InteractionResult interaction(Player player){

        String current_faction = FactionAttachment.get(player).getCurrentFaction();

        //player.displayClientMessage(Component.literal(current_faction), false);

        if (!current_faction.isEmpty()){
            if(getOwner().isEmpty()){
                setOwner(current_faction);
                player.displayClientMessage(Component.translatable("new_owner_flag_alert"), false);
                return InteractionResult.SUCCESS;
            }
            else{
                if (getOwner().equals(current_faction)){
                    player.displayClientMessage(Component.translatable("already_owner_flag_alert"), false);
                    return InteractionResult.FAIL;
                }
                else{
                    player.displayClientMessage(Component.translatable("other_faction_owner_alert"), false);
                    return InteractionResult.FAIL;
                }
            }
        }
        else {
            player.displayClientMessage(Component.translatable("not_in_faction_alert"), false);
            return InteractionResult.FAIL;
        }



    }


    @Override
    protected void saveAdditional(ValueOutput writeView) {
        if (factionOwner != null){
            writeView.putString("factionOwner", factionOwner);
        }

        super.saveAdditional(writeView);
    }


    @Override
    protected void loadAdditional(ValueInput readView) {
        super.loadAdditional(readView);

        String u = readView.getStringOr("factionOwner", "");

        if (!u.isEmpty()) {
            factionOwner = u;
        } else {
            factionOwner = "";
        }

    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registryLookup) {
        return saveWithoutMetadata(registryLookup);
    }


}
