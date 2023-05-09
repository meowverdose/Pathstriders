package cc.chocochip.seele.listeners;

import cc.chocochip.seele.Seele;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

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

        if (living instanceof Player) {                                 // sendMessage()
            Player victim = (Player) living;                            // Victim (Player)
        }
    }
}
