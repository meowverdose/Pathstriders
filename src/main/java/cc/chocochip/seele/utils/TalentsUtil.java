package cc.chocochip.seele.utils;

import cc.chocochip.seele.Seele;
import cc.chocochip.seele.data.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class TalentsUtil { // TODO: 6/4/2023 Keep or make new Inventory extension class 

    public static boolean open(Player player) {
        if (player == null) return false;

        PlayerData playerData = Seele.getInstance().getHandler().getPlayerDataManager().get(player.getUniqueId());
        ItemStack[] talents = playerData.getTalents();
        Inventory inventory = Bukkit.createInventory(player, InventoryType.CHEST, "Talents");

        fill(inventory, new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1));
        addTalents(inventory, talents);
        player.openInventory(inventory);
        return true;
    }

    private static void addTalents(Inventory inventory, ItemStack[] talents) {
        // 10, 12, 14, 16
        for (int i = 0; i < talents.length; ++i) {
            if (talents[i] == null) return;

            int slot = 10 + (i * 2);
            inventory.setItem(slot, talents[i]);
        }
    }

    /**
     * Code courtesy of https://github.com/TriumphTeam/triumph-gui GuiFiller.java
     */
    private static void fillBorder(Inventory inventory, ItemStack item) {
        int rows = inventory.getSize() / 9;

        if (rows <= 2) {
            fill(inventory, item);
            return;
        }

        for (int i = 0; i < inventory.getSize(); ++i) {
            if ((i <= 8)
                    || (i >= (rows * 9) - 8) && (i <= (rows * 9) - 2)
                    || i % 9 == 0
                    || i % 9 == 8) {
                inventory.setItem(i, item);
            }
        }
    }

    private static void fill(Inventory inventory, ItemStack item) {
        for (int i = 0; i < inventory.getSize(); ++i) {
            inventory.setItem(i, item);
        }
    }
}
