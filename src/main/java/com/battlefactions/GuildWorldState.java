package com.battlefactions;

import com.battlefactions.guild.Guild;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;

import java.util.HashMap;
import java.util.Map;

public class GuildWorldState extends SavedData {

    private final Map<String, Guild> guilds = new HashMap<>();

    public static final Codec<GuildWorldState> CODEC =
            RecordCodecBuilder.create(instance ->
                instance.group(
                    Codec.unboundedMap(
                            Codec.STRING,
                            Guild.CODEC
                    )
                    .fieldOf("guild_data")
                    .forGetter(GuildWorldState::getGuilds)
                ).apply(instance, guilds -> {
                    GuildWorldState state = new GuildWorldState();
                    state.guilds.putAll(guilds);
                    return state;
                })
            );

    private static final SavedDataType<GuildWorldState> TYPE = new SavedDataType<>(
            "guild_data",
            GuildWorldState::new,
            CODEC,
            null
    );

    // get
    public Map<String, Guild> getGuilds() {
        return guilds;
    }

    // access
    public static GuildWorldState get(MinecraftServer server) {
        ServerLevel level = server.getLevel(ServerLevel.OVERWORLD);

        if(level == null) {
            return new GuildWorldState();
        }

        return level.getDataStorage().computeIfAbsent(TYPE);
    }

    // mod
    public void addGuild(String name, Guild guild) {
        guilds.put(name, guild);
        setDirty();
    }
}
