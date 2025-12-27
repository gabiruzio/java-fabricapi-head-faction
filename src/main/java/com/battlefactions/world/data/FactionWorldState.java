package com.battlefactions.world.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;

import java.util.HashMap;
import java.util.Map;

public class FactionWorldState extends SavedData {

    private final Map<String, Faction> guilds = new HashMap<>();

    public static final Codec<FactionWorldState> CODEC =
            RecordCodecBuilder.create(instance ->
                instance.group(
                    Codec.unboundedMap(
                            Codec.STRING,
                            Faction.CODEC
                    )
                    .fieldOf("guild_data")
                    .forGetter(FactionWorldState::getGuilds)
                ).apply(instance, guilds -> {
                    FactionWorldState state = new FactionWorldState();
                    state.guilds.putAll(guilds);
                    return state;
                })
            );

    private static final SavedDataType<FactionWorldState> TYPE = new SavedDataType<>(
            "guild_data",
            FactionWorldState::new,
            CODEC,
            null
    );

    // get
    public Map<String, Faction> getGuilds() {
        return guilds;
    }


    // access
    public static FactionWorldState get(MinecraftServer server) {
        ServerLevel level = server.getLevel(ServerLevel.OVERWORLD);

        if(level == null) {
            return new FactionWorldState();
        }

        return level.getDataStorage().computeIfAbsent(TYPE);
    }

    // mod
    public void addGuild(String name, Faction faction) {
        guilds.put(name, faction);
        setDirty();
    }
}
