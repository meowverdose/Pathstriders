package com.meowverdose.pathstriders.managers;

import com.meowverdose.pathstriders.Pathstriders;
import com.meowverdose.pathstriders.talents.TalentType;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public class TalentManager {

    private final Pathstriders plugin;
    private final EnumSet<TalentType> allTalents = EnumSet.noneOf(TalentType.class);
    private final Map<String, TalentType> talentById = new HashMap<>();

    public TalentManager(Pathstriders plugin) {
        this.plugin = plugin;
        registerAll();
    }

    private void registerAll() {
        for (TalentType talent : TalentType.values()) {
            registerTalent(talent);
            plugin.getLogger().info("Registering talent " + talent.getId());
        }
    }

    private void registerTalent(TalentType talent) {
        allTalents.add(talent);
        talentById.put(talent.getId(), talent);
    }

    public EnumSet<TalentType> getAllTalents() {
        return EnumSet.copyOf(allTalents);
    }

    public TalentType getTalentById(String id) {
        return talentById.get(id);
    }
}
