package com.battlefactions.world.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;

import java.util.ArrayList;

public class Faction {

    private String name;
    private ArrayList<String> members;
    private ArrayList<String> invited;
    private String owner;

    public static final Codec<Faction> CODEC =
            RecordCodecBuilder.create(instance ->
                    instance.group(
                            Codec.STRING.fieldOf("name")
                                    .forGetter(Faction::getName),

                            Codec.list(Codec.STRING)
                                    .fieldOf("members")
                                    .forGetter(Faction::getMembers),

                            Codec.list(Codec.STRING)
                                     .fieldOf("invited")
                                     .forGetter(Faction::getInvited),

                            Codec.STRING.fieldOf("owner")
                                    .forGetter(Faction::getOwner)

                    ).apply(instance, (name, members, invited, owner) -> {
                        Faction f = new Faction();
                        f.name = name;
                        f.invited = new ArrayList<>(invited);
                        f.members = new ArrayList<>(members); // SEM CAST
                        f.owner = owner;
                        return f;
                    })
            );

    public Faction() {
        members = new ArrayList<>();
        invited = new ArrayList<>();
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

    //public String getFlagPos() {return flagPos; }
    //public void setFlagPos(BlockPos pos) {this.flagPos = pos.toString(); }



}

