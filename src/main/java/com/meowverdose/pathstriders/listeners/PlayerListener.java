package com.meowverdose.pathstriders.listeners;

import com.meowverdose.pathstriders.Pathstriders;
import com.meowverdose.pathstriders.talents.Talent;
import com.meowverdose.pathstriders.talents.TalentEffect;
import com.meowverdose.pathstriders.util.TimeUtil;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

public class PlayerListener implements Listener {

    private final Pathstriders plugin;

    public PlayerListener(Pathstriders plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player attacker) || !(event.getEntity() instanceof LivingEntity victim)) return;

        Talent mainHandTalent = Talent.fromItem(attacker.getInventory().getItemInMainHand());
        if (mainHandTalent != null && mainHandTalent.getOnAttackEffect() != null) {
            if (mainHandTalent.getSlot() == EquipmentSlot.HAND) {
                if (!isSilenced(attacker)) {
                    mainHandTalent.getOnAttackEffect().accept(attacker, victim, event);
                }
            }
        }

        Talent offHandTalent = Talent.fromItem(attacker.getInventory().getItemInOffHand());
        if (offHandTalent != null && offHandTalent.getOnAttackEffect() != null) {
            if (offHandTalent.getSlot() == EquipmentSlot.OFF_HAND) {
                if (!isSilenced(attacker)) {
                    offHandTalent.getOnAttackEffect().accept(attacker, victim, event);
                }
            }
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (!(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)) return;
        if (!event.getPlayer().isSneaking()) return;

        Player player = event.getPlayer();

        // Main hand
        Talent mainHandTalent = Talent.fromItem(player.getInventory().getItemInMainHand());
        if (mainHandTalent != null && !plugin.getPlayerDataManager().isOnCooldown(player, mainHandTalent)) {
            if (mainHandTalent.getSlot() == EquipmentSlot.HAND) {
                if (!isSilenced(player) && !hasCooldown(player, mainHandTalent)) {
                    mainHandTalent.getActiveEffect().accept(player, plugin.getPlayerDataManager());
                    plugin.getPlayerDataManager().setCooldown(player, mainHandTalent);
                }
            }
        }

        // Off hand
        Talent offHandTalent = Talent.fromItem(player.getInventory().getItemInOffHand());
        if (offHandTalent != null && !plugin.getPlayerDataManager().isOnCooldown(player, offHandTalent)) {
            if (offHandTalent.getSlot() == EquipmentSlot.OFF_HAND) {
                if (!isSilenced(player) && !hasCooldown(player, offHandTalent)) {
                    offHandTalent.getActiveEffect().accept(player, plugin.getPlayerDataManager());
                    plugin.getPlayerDataManager().setCooldown(player, offHandTalent);
                }
            }
        }
    }

    private boolean hasCooldown(Player player, Talent talent) {
        if (plugin.getPlayerDataManager().isOnCooldown(player, talent)) {
            player.sendMessage("§cTalents: " + talent.getItem().getItemMeta().getDisplayName() + " is on cooldown for " + TimeUtil.formatMillis(plugin.getPlayerDataManager().getCooldownRemaining(player, talent)));
            return true;
        }
        return false;
    }

    private boolean isSilenced(Player player) {
        if (plugin.getPlayerDataManager().hasEffect(player, TalentEffect.THE_FOOLS_WORLD)) {
            player.sendMessage("§cTalents: You are silenced and cannot use talents for " + TimeUtil.formatMillis(plugin.getPlayerDataManager().getEffectRemaining(player, TalentEffect.THE_FOOLS_WORLD))); // show in hud bar
            return true;
        }
        return false;
    }
}
