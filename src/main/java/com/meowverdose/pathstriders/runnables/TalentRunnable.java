package com.meowverdose.pathstriders.runnables;

import com.meowverdose.pathstriders.Pathstriders;
import com.meowverdose.pathstriders.talents.Talent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Iterator;
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
            // Clear expired cooldowns
            Map<String, Long> cooldowns = plugin.getPlayerDataManager().getCooldowns().get(player.getUniqueId());
            if (cooldowns != null) {
                Iterator<Map.Entry<String, Long>> iterator = cooldowns.entrySet().iterator();

                while (iterator.hasNext()) {
                    Map.Entry<String, Long> entry = iterator.next();
                    String talentId = entry.getKey();
                    long duration = entry.getValue();

                    if (duration <= System.currentTimeMillis()) {
                        // Remove this expired cooldown
                        iterator.remove();

                        Talent talent = plugin.getTalentManager().getTalentById(talentId);
                        String name = (talent != null) ? talent.name() : talentId;

                        player.sendMessage(ChatColor.GREEN + "Talents: " + name + " is ready to be used again");
                    }
                }

                // If all cooldowns for this player are gone, remove their map entirely
                if (cooldowns.isEmpty()) {
                    plugin.getPlayerDataManager().getCooldowns().remove(player.getUniqueId());
                }
            }

            // Skip loop if player's talents are disabled
            if (plugin.getPlayerDataManager().isTalentDisabled(player)) {
                continue;
            }

            ItemStack offHand = player.getInventory().getItemInOffHand();
            Talent matchedTalent = null;

            // Looks up talent
            for (Talent talent : plugin.getTalentManager().getAllTalents()) {
                if (talent.isTalent(offHand, Pathstriders.TALENT_ID_KEY)) {
                    matchedTalent = talent;
                    break;
                }
            }

            // TODO Look up custom debuff effects to proc (i.e. Murasame)

            Talent currentTalent = plugin.getPlayerDataManager().getActiveTalent(player);

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
