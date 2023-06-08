package cc.chocochip.seele.talents;

import cc.chocochip.seele.data.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;

public class TalentsMenu implements Menu {

    private PlayerData playerData;
    private ItemStack[] talents;

    public TalentsMenu(PlayerData playerData) {
        this.playerData = playerData;
        this.talents = playerData.getTalents().clone();
    }

    @Override
    public void onInventoryClick(InventoryClickEvent event) {
        int[] slots = {10, 12, 14, 16};

        if (!Arrays.asList(slots).contains(event.getSlot())) {
            event.setCancelled(true);
        }
    }

    @Override
    public void onInventoryClose(InventoryCloseEvent event) {
        for (int i = 0; i < this.talents.length; ++i) {
            int slot = (i * 2) + 10;

            if (event.getInventory().getItem(slot) == null) return;

            ItemStack talent = event.getInventory().getItem(slot);

            playerData.setTalent(i, talent);
        }
    }

    @Override
    public void addContent(Inventory inventory) {
        for (int i = 0; i < this.talents.length; ++i) {
            if (this.talents[i] == null) return;

            int slot = (i * 2) + 10; // 10, 12, 14, 16
            ItemStack talent = this.talents[i];
            inventory.setItem(slot, talent);
        }
    }

    @Override
    public Inventory getInventory() {
        Inventory inventory = Bukkit.createInventory(this, 27, "Talents");
        addContent(inventory);
        return inventory;
    }
}
