package org.nekoverse.seele.listeners;

import org.nekoverse.seele.Seele;
import org.nekoverse.seele.data.PlayerData;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class PlayerListener implements Listener {

    private final Seele plugin;

    public PlayerListener(Seele plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = (Player) event.getPlayer();

        if (!player.hasPlayedBefore()) {
            this.plugin.getHandler().getPlayerDataManager().loadPlayerData(player.getUniqueId());
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = (Player) event.getPlayer();
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = (Player) event.getPlayer();
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player)) return;            // Damager must be Player
        if (!(event.getEntity() instanceof LivingEntity)) return;       // Victim must be living

        Player damager = (Player) event.getDamager();                   // Damager (Player)
        LivingEntity living = (LivingEntity) event.getEntity();         // Victim (LivingEntity)

        // TODO: Apply debuffs, etc.
        PlayerData damagerData = this.plugin.getHandler().getPlayerDataManager().get(damager.getUniqueId());

        if (living instanceof Player) {                                 // sendMessage()
            Player victim = (Player) living;                            // Victim (Player)
        }

        if (damagerData.getTalents()[0] != null) {                      // Light Cone
            ItemStack lightCone = damagerData.getTalents()[0];

            if (lightCone.hasItemMeta()) {
                if (lightCone.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(Seele.getInstance(), "Flowers_And_Butterflies"), PersistentDataType.STRING)) {
                    double currSpd = damager.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getValue();
                    double defaultSpd = damager.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getBaseValue();
                    double dmgMultiplier = 1 + (((currSpd - defaultSpd) * 100) * 0.06);

                    if (dmgMultiplier <= 0) {
                        dmgMultiplier = 1;
                    } else if (dmgMultiplier > 1.24) {
                        dmgMultiplier = 1.24;
                    }
                    event.setDamage(event.getDamage() * dmgMultiplier);
                }
            }
        }
    }
}
