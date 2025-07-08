package com.meowverdose.pathstriders.runnables;

import com.meowverdose.pathstriders.Pathstriders;
import com.meowverdose.pathstriders.talents.TalentType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class TalentRunnable extends BukkitRunnable {

    private Pathstriders plugin;

    public TalentRunnable(Pathstriders plugin) {
        this.plugin = plugin;
        runTaskTimer(plugin, 0, 20); // Runs every second (20 ticks)
    }

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            ItemStack offHand = player.getInventory().getItemInOffHand();
            TalentType matchedTalent = null;

            for (TalentType talent : plugin.getTalentManager().getAllTalents()) {
                if (talent.isTalent(offHand, Pathstriders.TALENT_ID_KEY)) {
                    matchedTalent = talent;
                    break;
                }
            }

            TalentType currentTalent = plugin.getPlayerDataManager().getActiveTalent(player);

            if (matchedTalent != null) {
                if (currentTalent != matchedTalent) {
                    if (currentTalent != null) currentTalent.onUnequip(player);
                    matchedTalent.onEquip(player);
                    plugin.getPlayerDataManager().setActiveTalent(player, matchedTalent);
                }
            } else if (currentTalent != null) {
                currentTalent.onUnequip(player);
                plugin.getPlayerDataManager().removeActiveTalent(player);
            }
        }
    }
}
