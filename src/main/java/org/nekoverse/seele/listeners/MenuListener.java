package org.nekoverse.seele.listeners;

import org.nekoverse.seele.Seele;
import org.nekoverse.seele.talents.Menu;
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
        if (event.getClickedInventory() == null) return;                        // Check click out of screen
        if (event.getInventory().getHolder() == null) return;                   // Will never happen
        if (!(event.getInventory().getHolder() instanceof Menu)) return;        // Check if inventory holder is a Menu

        if (event.getClickedInventory() == event.getInventory()) {              // Check if clicked inventory is Talents menu
            Menu menu = (Menu) event.getInventory().getHolder();
            menu.onInventoryClick(event);
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (event.getInventory().getHolder() == null) return;                   // Will never happen
        if (!(event.getInventory().getHolder() instanceof Menu)) return;        // Check if inventory holder is a Menu

        Menu menu = (Menu) event.getInventory().getHolder();
        menu.onInventoryClose(event);
    }
}
