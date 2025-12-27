package com.battlefactions.block_entitys.custom;

import com.battlefactions.BattleFactionsMod;
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

    private String ownerUuid;


    public void setOwner(UUID playerUuid){
        ownerUuid = playerUuid.toString();
        setChanged();
    }

    public String getOwner() {
        return ownerUuid;
    }


    public InteractionResult interaction(Player player){

        player.displayClientMessage(Component.literal("Player: " + player.getUUID()) , false);

        if(getOwner() == null || getOwner().isEmpty()){
            setOwner(player.getUUID());
            player.displayClientMessage(Component.translatable("new_owner_flag_alert"), false);
            return InteractionResult.SUCCESS;
        }
        else if (getOwner().equals(player.getUUID().toString())){
            player.displayClientMessage(Component.translatable("already_owner_flag_alert"), false);
            player.displayClientMessage(Component.literal(getOwner().toString()) , false);
            return InteractionResult.FAIL;
        }
        else if (!getOwner().equals(player.getUUID().toString()) ){
            player.displayClientMessage(Component.translatable("Outro player e dono"), false);
            player.displayClientMessage(Component.literal(getOwner().toString()) , false);
            return InteractionResult.FAIL;
        }


        return InteractionResult.FAIL;
    }


    @Override
    protected void saveAdditional(ValueOutput writeView) {
        if (ownerUuid != null){
            writeView.putString("ownerUuid", ownerUuid);
        }

        super.saveAdditional(writeView);
    }


    @Override
    protected void loadAdditional(ValueInput readView) {
        super.loadAdditional(readView);

        String u = readView.getStringOr("ownerUuid", "");

        if (!u.isEmpty()) {
            ownerUuid = u;
        } else {
            ownerUuid = null;
        }

    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registryLookup) {
        return saveWithoutMetadata(registryLookup);
    }


}
