package org.nekoverse.seele.talents;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public interface Menu extends InventoryHolder {

    void onInventoryClick(InventoryClickEvent event);
    void onInventoryClose(InventoryCloseEvent event);
    void addContent(Inventory inventory);
}
