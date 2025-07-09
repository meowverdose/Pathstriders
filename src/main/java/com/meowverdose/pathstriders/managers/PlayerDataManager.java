package com.meowverdose.pathstriders.managers;

import com.meowverdose.pathstriders.Pathstriders;
import com.meowverdose.pathstriders.talents.Talent;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerDataManager {

    private final Pathstriders plugin;
    private final Map<UUID, Talent> activeTalent = new HashMap<>();
    private final Map<UUID, Long> disabledDuration = new HashMap<>();
    private final Map<UUID, Map<String, Long>> cooldowns = new HashMap<>(); // Multiple cooldowns

    public PlayerDataManager(Pathstriders plugin) {
        this.plugin = plugin;
    }

    public Talent getActiveTalent(Player player) {
        return activeTalent.get(player.getUniqueId());
    }

    public long getCooldownRemaining(Player player, String talentId) {
        Map<String, Long> playerCooldowns = cooldowns.get(player.getUniqueId());
        if (playerCooldowns == null) return 0;

        Long duration = playerCooldowns.get(talentId);
        if (duration == null) return 0;

        long remaining = duration - System.currentTimeMillis();
        return Math.max(remaining, 0);
    }

    public Map<UUID, Map<String, Long>> getCooldowns() {
        return cooldowns;
    }

    public void setActiveTalent(Player player, Talent talent) {
        activeTalent.put(player.getUniqueId(), talent);
    }

    public void removeActiveTalent(Player player) {
        activeTalent.remove(player.getUniqueId());
    }

    public void setCooldown(Player player, String talentId, long duration) {
        cooldowns.computeIfAbsent(player.getUniqueId(), k -> new HashMap<>())
                .put(talentId, System.currentTimeMillis() + duration);
    }

    public void clearCooldown(Player player, String talentId) {
        Map<String, Long> playerCooldowns = cooldowns.get(player.getUniqueId());
        if (playerCooldowns != null) {
            playerCooldowns.remove(talentId);
        }
    }

    public void setTalentDisabled(Player player, long duration) {
        disabledDuration.put(player.getUniqueId(), System.currentTimeMillis() + duration);
    }

    public void removeTalentDisabled(Player player) {
        disabledDuration.remove(player.getUniqueId());
    }

    public boolean isTalentDisabled(Player player) {
        Long duration = disabledDuration.get(player.getUniqueId());
        return duration != null && duration > System.currentTimeMillis();
    }

    public boolean isOnCooldown(Player player, String talentId) {
        Map<String, Long> playerCooldowns = cooldowns.get(player.getUniqueId());
        if (playerCooldowns == null) return false;

        Long duration = playerCooldowns.get(talentId);
        return duration != null && duration > System.currentTimeMillis();
    }
}
