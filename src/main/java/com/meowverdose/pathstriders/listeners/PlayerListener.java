package com.meowverdose.pathstriders.listeners;

import com.meowverdose.pathstriders.Pathstriders;
import com.meowverdose.pathstriders.talents.Talent;
import org.bukkit.ChatColor;
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
        if (!(event.getDamager() instanceof Player player)) return; // Damager is player check

        Talent talent = plugin.getPlayerDataManager().getActiveTalent(player);

        if (talent == null) return; // Null check
        if (!(event.getEntity() instanceof LivingEntity target)) return; // Target is player check
        if (plugin.getPlayerDataManager().isTalentDisabled(player)) return; // Talent disabled check

        talent.onAttack(player, target);
        plugin.getLogger().info(player.getName() + "'s " + talent.getId() + " talent proc'd on " + target.getName());
    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {
        Action action = event.getAction();

        // Not a right-click
        if (action != Action.RIGHT_CLICK_AIR && action != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        // Ignore main-hand actions
        if (event.getHand() != EquipmentSlot.OFF_HAND) {
            return;
        }

        Player player = event.getPlayer();
        Talent talent = plugin.getPlayerDataManager().getActiveTalent(player);

        if (talent == null) return; // Null check
        if (plugin.getPlayerDataManager().isOnCooldown(player, talent.getId())) { // Talent cooldown check
            System.out.println("DEBUG : " + Pathstriders.getInstance().getPlayerDataManager().isOnCooldown(player, talent.getId()));
            long remaining = Pathstriders.getInstance().getPlayerDataManager().getCooldownRemaining(player, talent.getId()) / 1000;

            player.sendMessage(ChatColor.RED + "Talents: " + talent.name() + " is on cooldown! " + remaining + "s remaining.");
            return;
        }
        if (plugin.getPlayerDataManager().isTalentDisabled(player)) return; // Talent disabled check

        talent.onRightClick(player);
    }
}
