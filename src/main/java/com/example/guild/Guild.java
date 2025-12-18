package com.example.guild;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;

import java.util.ArrayList;

public class Guild {

    private String name;
    private ArrayList<String> members;
    private ArrayList<String> invited;
    private String owner;

    public static final Codec<Guild> CODEC =
            RecordCodecBuilder.create(instance ->
                    instance.group(
                            Codec.STRING.fieldOf("name")
                                    .forGetter(Guild::getName),

                            Codec.list(Codec.STRING)
                                    .fieldOf("members")
                                    .forGetter(Guild::getMembers),

                            Codec.list(Codec.STRING)
                                     .fieldOf("invited")
                                     .forGetter(Guild::getInvited),

                            Codec.STRING.fieldOf("owner")
                                    .forGetter(Guild::getOwner)
                    ).apply(instance, (name, members, invited, owner) -> {
                        Guild g = new Guild();
                        g.name = name;
                        g.invited = new ArrayList<>(invited);
                        g.members = new ArrayList<>(members); // SEM CAST
                        g.owner = owner;
                        return g;
                    })
            );

    public Guild() {
        members = new ArrayList<>();
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public ArrayList<String> getMembers() {
        return members;
    }

    public void setMembers(ArrayList<String> members) {
        this.members = members;
    }

    public ArrayList<String> getInvited() {
        return invited;
    }

    public void setInvited(ArrayList<String> invited) {
        this.invited = invited;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

