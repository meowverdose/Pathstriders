package com.meowverdose.pathstriders.managers;

import com.meowverdose.pathstriders.Pathstriders;
import com.meowverdose.pathstriders.talents.Talent;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public class TalentManager {

    private final Pathstriders plugin;
    private final EnumSet<Talent> allTalents = EnumSet.noneOf(Talent.class);
    private final Map<String, Talent> talentById = new HashMap<>();

    public TalentManager(Pathstriders plugin) {
        this.plugin = plugin;
        registerAll();
    }

    private void registerAll() {
        for (Talent talent : Talent.values()) {
            registerTalent(talent);
            plugin.getLogger().info("Registering talent " + talent.getId());
        }
    }

    private void registerTalent(Talent talent) {
        allTalents.add(talent);
        talentById.put(talent.getId(), talent);
    }

    public EnumSet<Talent> getAllTalents() {
        return EnumSet.copyOf(allTalents);
    }

    public Talent getTalentById(String id) {
        return talentById.get(id);
    }
}
