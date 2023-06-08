package cc.chocochip.seele.listeners;

import cc.chocochip.seele.Seele;
import cc.chocochip.seele.talents.Menu;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class MenuListener implements Listener {

    private final Seele plugin;

    public MenuListener(Seele plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getClickedInventory() == null) return;
        if (event.getInventory().getHolder() == null) return;
        if (!(event.getInventory().getHolder() instanceof Menu)) return;

        if (event.getClickedInventory() == event.getInventory()) {              // Check if clicked inventory is Talents menu
            Menu menu = (Menu) event.getInventory().getHolder();
            menu.onInventoryClick(event);
        }
        //event.setCancelled(true);
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (event.getInventory().getHolder() == null) return;
        if (!(event.getInventory().getHolder() instanceof Menu)) return;

        Menu menu = (Menu) event.getInventory().getHolder();
        menu.onInventoryClose(event);
    }
}
