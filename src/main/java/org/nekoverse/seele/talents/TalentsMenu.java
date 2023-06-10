package org.nekoverse.seele.talents;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.nekoverse.seele.data.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.nekoverse.seele.utils.ItemBuilder;

public class TalentsMenu implements Menu {

    private final PlayerData playerData;

    public TalentsMenu(PlayerData playerData) {
        this.playerData = playerData;
    }

    @Override
    public void onInventoryClick(InventoryClickEvent event) {
        switch (event.getSlot()) {
            case 10, 13, 16 -> {
                if (event.getClickedInventory().equals(event.getView().getTopInventory())) {
                    ItemStack talent = event.getClickedInventory().getItem(event.getSlot());
                    ItemStack cursor = event.getCursor();

                    if (talent == null) {                                                   // Talent spot unoccupied
                        if (cursor == null) return;                                         // Removing talent

                        if (!isTalentItem(cursor)) {                                        // Only talents
                            System.out.println("cancel B"); // todo DBUG
                            event.setCancelled(true);
                        }

                        // TODO: 6/10/2023 Add specific slot logic later
                    } else {                                                                // Talent slot occupied
                        if (talent.getMaxStackSize() > 1 && talent.isSimilar(cursor)) {     // No stacking talents
                            System.out.println("cancel A"); // todo DBUG
                            event.setCancelled(true);
                        }
                    }
                }
            }
            default -> {
                System.out.println("cancel C"); // todo DBUG
                event.setCancelled(true);
            }
        }
    }

    @Override
    public void onInventoryClose(InventoryCloseEvent event) {
        for (int i = 0; i < this.playerData.getTalents().length; ++i) {
            int slot = (i * 3) + 10;
            ItemStack talent = event.getInventory().getItem(slot);

            playerData.setTalent(i, (talent == null ? null : new ItemStack(talent)));       // Must do copy constructor
        }

        Player player = Bukkit.getPlayer(playerData.getUniqueId());

        player.sendMessage(
                "",
                ChatColor.GOLD + " " + ChatColor.BOLD + "Talents",
                "",
                ChatColor.YELLOW + "  Light Cone: " + (playerData.getTalents()[0] == null ? ChatColor.GRAY + "Empty" : playerData.getTalents()[0].getItemMeta().getDisplayName()),
                ChatColor.YELLOW + "  Artifact: " + (playerData.getTalents()[1] == null ? ChatColor.GRAY + "Empty" : playerData.getTalents()[1].getItemMeta().getDisplayName()),
                ChatColor.YELLOW + "  Relic: " + (playerData.getTalents()[2] == null ? ChatColor.GRAY + "Empty" : playerData.getTalents()[2].getItemMeta().getDisplayName()),
                ""
        );
    }

    @Override
    public void addContent(Inventory inventory) {
        for (int i = 0; i < inventory.getSize(); ++i) {
            switch (i) {
                case 1 -> {
                    inventory.setItem(i, new ItemBuilder(Material.NETHER_STAR, 1)
                            .setName(ChatColor.GOLD + "" + ChatColor.BOLD + "Light Cone")
                            .addEnchant(Enchantment.DURABILITY, 1)
                            .addFlag(ItemFlag.HIDE_ENCHANTS)
                            .toItemStack()
                    );
                }
                case 4 -> {
                    inventory.setItem(i, new ItemBuilder(Material.NETHER_STAR, 1)
                            .setName(ChatColor.BLUE + "" + ChatColor.BOLD + "Artifact")
                            .addEnchant(Enchantment.DURABILITY, 1)
                            .addFlag(ItemFlag.HIDE_ENCHANTS)
                            .toItemStack()
                    );
                }
                case 7 -> {
                    inventory.setItem(i, new ItemBuilder(Material.NETHER_STAR, 1)
                            .setName(ChatColor.RED + "" + ChatColor.BOLD + "Relic")
                            .addEnchant(Enchantment.DURABILITY, 1)
                            .addFlag(ItemFlag.HIDE_ENCHANTS)
                            .toItemStack()
                    );
                }
                case 10, 13, 16 -> {}
                default -> {
                    inventory.setItem(i, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE, 1)
                            .setName(" ")
                            .toItemStack()
                    );
                }
            }
        }

        for (int i = 0; i < this.playerData.getTalents().length; ++i) {
            if (this.playerData.getTalents()[i] == null) return;

            int slot = (i * 3) + 10;                                                        // 10, 13, 16
            ItemStack talent = this.playerData.getTalents()[i];
            inventory.setItem(slot, talent);
        }
    }

    @Override
    public Inventory getInventory() {
        Inventory inventory = Bukkit.createInventory(this, 27, "Talents");
        addContent(inventory);
        return inventory;
    }

    public Inventory create() {
        return getInventory();
    }

    private boolean isTalentItem(ItemStack item) { // TODO: 6/9/2023 Later will need to change if @item has dynamic durability
        if (!item.hasItemMeta()) return false;

        for (Items items : Items.values()) {
            if (items.getItem().getItemMeta().getPersistentDataContainer().equals(item.getItemMeta().getPersistentDataContainer())) {
                return true;
            }
        }
        return false;
    }
}
