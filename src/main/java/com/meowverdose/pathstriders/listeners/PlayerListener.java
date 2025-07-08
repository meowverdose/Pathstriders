package com.meowverdose.pathstriders.listeners;

import com.meowverdose.pathstriders.Pathstriders;
import com.meowverdose.pathstriders.talents.TalentType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerListener implements Listener {

    private final Pathstriders plugin;

    public PlayerListener(Pathstriders plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player player)) return;

        TalentType talent = plugin.getPlayerDataManager().getActiveTalent(player);

        if (talent == null) return;
        if (!(event.getEntity() instanceof LivingEntity target)) return;

        talent.onAttack(player, target);
        plugin.getLogger().info(player.getName() + "'s " + talent.getId() + " talent proc'd on " + target.getName());
    }

    // TODO debug this
    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_AIR) return;
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;

        Player player = event.getPlayer();
        TalentType talent = plugin.getPlayerDataManager().getActiveTalent(player);

        if (talent == null) return;

        talent.onRightClick(player);
    }
}
