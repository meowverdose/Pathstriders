package com.meowverdose.pathstriders.managers;

import com.meowverdose.pathstriders.Pathstriders;
import com.meowverdose.pathstriders.talents.Talent;
import com.meowverdose.pathstriders.talents.TalentEffect;
import org.bukkit.entity.Player;

import java.util.*;

public class PlayerDataManager {

    private final Pathstriders plugin;
    // Cooldowns for own talent usage
    private final Map<UUID, Map<Talent, Long>> cooldowns = new HashMap<>();
    // Affected by other talents (like disables, debuffs)
    private final Map<UUID, Map<TalentEffect, Long>> activeEffects = new HashMap<>();

    public PlayerDataManager(Pathstriders plugin) {
        this.plugin = plugin;
    }

    // Cooldowns
    public boolean isOnCooldown(Player player, Talent talent) {
        Map<Talent, Long> map = cooldowns.get(player.getUniqueId());
        if (map == null) return false;
        return map.getOrDefault(talent, 0L) > System.currentTimeMillis();
    }

    public void setCooldown(Player player, Talent talent, long durationMillis) {
        cooldowns.computeIfAbsent(player.getUniqueId(), k -> new HashMap<>())
                .put(talent, System.currentTimeMillis() + durationMillis);
    }

    public void setCooldown(Player player, Talent talent) {
        setCooldown(player, talent, talent.getCooldown());
    }

    public void removeCooldown(UUID uuid, Talent talent) {
        Map<Talent, Long> playerCooldowns = cooldowns.get(uuid);
        if (playerCooldowns != null) {
            playerCooldowns.remove(talent);
            if (playerCooldowns.isEmpty()) {
                cooldowns.remove(uuid);
            }

            // Notify if online
            Player player = plugin.getServer().getPlayer(uuid);
            if (player != null && player.isOnline()) {
                player.sendMessage("§aTalents: " + talent.getItem().getItemMeta().getDisplayName() + " ability cooldown has expired!"); // todo talent name
            }
        }
    }

    public void cleanupExpiredCooldowns() {
        long now = System.currentTimeMillis();

        for (UUID uuid : new HashSet<>(cooldowns.keySet())) {
            Map<Talent, Long> talentMap = cooldowns.get(uuid);
            if (talentMap == null) continue;

            for (Talent talent : new HashSet<>(talentMap.keySet())) {
                long endTime = talentMap.getOrDefault(talent, 0L);
                if (endTime <= now) {
                    removeCooldown(uuid, talent);
                }
            }
        }
    }

    public long getCooldownRemaining(Player player, Talent talent) {
        Map<Talent, Long> map = cooldowns.get(player.getUniqueId());
        if (map == null) return 0;

        long end = map.getOrDefault(talent, 0L);
        long now = System.currentTimeMillis();

        return Math.max(0, end - now);
    }

    // Active effects
    public boolean hasEffect(Player player, TalentEffect effect) {
        Map<TalentEffect, Long> map = activeEffects.get(player.getUniqueId());
        if (map == null) return false;
        return map.getOrDefault(effect, 0L) > System.currentTimeMillis();
    }

    public void applyEffect(Player player, TalentEffect effect) {
        activeEffects.computeIfAbsent(
                player.getUniqueId(),
                k -> new HashMap<>()
        ).put(effect, System.currentTimeMillis() + effect.getDurationMillis());
    }

    public void removeEffect(UUID uuid, TalentEffect effect) {
        Map<TalentEffect, Long> effectsMap = activeEffects.get(uuid);
        if (effectsMap != null) {
            effectsMap.remove(effect);
            if (effectsMap.isEmpty()) {
                activeEffects.remove(uuid);
            }

            // Notify if online
            Player player = plugin.getServer().getPlayer(uuid);
            if (player != null && player.isOnline()) {
                player.sendMessage("§eTalents: Your " + effect.name().toLowerCase() + " effect has expired!"); // todo effect name
            }
        }
    }

    public void removeEffect(Player player, TalentEffect effect) {
        removeEffect(player.getUniqueId(), effect);
    }

    public long getEffectRemaining(Player player, TalentEffect effect) {
        long end = activeEffects.getOrDefault(player.getUniqueId(), Collections.emptyMap())
                .getOrDefault(effect, 0L);
        return Math.max(0, end - System.currentTimeMillis());
    }

    public void cleanupExpiredEffects() {
        long now = System.currentTimeMillis();

        for (UUID uuid : new HashSet<>(activeEffects.keySet())) {
            Map<TalentEffect, Long> effectsMap = activeEffects.get(uuid);
            if (effectsMap == null) continue;

            for (TalentEffect effect : new HashSet<>(effectsMap.keySet())) {
                long endTime = effectsMap.getOrDefault(effect, 0L);
                if (endTime <= now) {
                    removeEffect(uuid, effect);
                }
            }
        }
    }

    public Map<TalentEffect, Long> getActiveEffects(Player player) {
        return activeEffects.get(player.getUniqueId());
    }
}
