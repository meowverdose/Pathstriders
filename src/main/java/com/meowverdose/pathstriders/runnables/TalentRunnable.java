package com.meowverdose.pathstriders.runnables;

import com.meowverdose.pathstriders.Pathstriders;
import com.meowverdose.pathstriders.talents.Talent;
import com.meowverdose.pathstriders.talents.TalentEffect;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;

public class TalentRunnable extends BukkitRunnable {

    private final Pathstriders plugin;

    public TalentRunnable(Pathstriders plugin) {
        this.plugin = plugin;
        runTaskTimer(plugin, 0, 20); // Runs every second (20 ticks)
    }

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (plugin.getPlayerDataManager().hasEffect(player, TalentEffect.THE_FOOLS_WORLD)) {
                return;
            }

            // Main hand passive
            Talent mainHandTalent = Talent.fromItem(player.getInventory().getItemInMainHand());
            if (mainHandTalent != null && mainHandTalent.getPassiveEffect() != null) {
                mainHandTalent.getPassiveEffect().accept(player);
            }

            // Off hand passive
            Talent offHandTalent = Talent.fromItem(player.getInventory().getItemInOffHand());
            if (offHandTalent != null && offHandTalent.getPassiveEffect() != null) {
                offHandTalent.getPassiveEffect().accept(player);
            }

            // Active effect logic
            if (plugin.getPlayerDataManager().getActiveEffects(player) != null) {
                Map<TalentEffect, Long> activeEffects = plugin.getPlayerDataManager().getActiveEffects(player);

                for (TalentEffect effect : activeEffects.keySet()) {
                    effect.apply(player);
                }
            }
        }
        // Cleanup expired data
        plugin.getPlayerDataManager().cleanupExpiredCooldowns();
        plugin.getPlayerDataManager().cleanupExpiredEffects();
    }
}
