package com.meowverdose.pathstriders.managers;

import com.meowverdose.pathstriders.Pathstriders;
import com.meowverdose.pathstriders.talents.TalentType;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerDataManager {

    private final Pathstriders plugin;
    private final Map<UUID, TalentType> activeTalent = new HashMap<>();

    public PlayerDataManager(Pathstriders plugin) {
        this.plugin = plugin;
    }

    public TalentType getActiveTalent(Player player) {
        return activeTalent.get(player.getUniqueId());
    }

    public void setActiveTalent(Player player, TalentType talent) {
        activeTalent.put(player.getUniqueId(), talent);
    }

    public void removeActiveTalent(Player player) {
        activeTalent.remove(player.getUniqueId());
    }
}
