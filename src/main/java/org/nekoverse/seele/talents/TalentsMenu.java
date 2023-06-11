package org.nekoverse.seele.talents;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.nekoverse.seele.data.PlayerData;
import org.nekoverse.seele.utils.ItemBuilder;

import javax.annotation.Nullable;
import java.util.Map;

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
                    Player player = (Player) event.getWhoClicked();
                    ItemStack talent = event.getClickedInventory().getItem(event.getSlot());
                    ItemStack cursor = event.getCursor();
                    ItemStack[] talents = {
                            event.getClickedInventory().getItem(10),
                            event.getClickedInventory().getItem(13),
                            event.getClickedInventory().getItem(16),
                    };

                    if (talent == null) {                                                   // Talent spot unoccupied
                        if (cursor == null) return;                                         // Removing talent

                        if (!isTalentItem(cursor)) {                                        // Only talents
                            player.sendMessage(ChatColor.RED + " **ERROR** That item is not a talent");
                            event.setCancelled(true);
                            return;
                        }

                        if (talentExists(talents, cursor)) {                                         // No duplicate talents
                            player.sendMessage(ChatColor.RED + " **ERROR** You cannot have duplicate talents equipped");
                            event.setCancelled(true);
                            return;
                        }
                    } else {                                                                // Talent slot occupied
                        if (talent.getMaxStackSize() > 1 && talent.isSimilar(cursor)) {     // No stacking talents
                            event.setCancelled(true);
                            return;
                        }
                    }
                }
            }
            default -> {
                event.setCancelled(true);
            }
        }
    }

    @Override
    public void onInventoryClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();

        removeAttributeModifiers(player);

        for (int i = 0; i < 3; ++i) {
            int slot = (i * 3) + 10;
            ItemStack talent = event.getInventory().getItem(slot);

            playerData.setTalent(i, (talent == null ? null : new ItemStack(talent)));       // Must do copy constructor

            applyAttributeModifiers(talent, player);
        }

        player.sendMessage(
                "",
                ChatColor.GOLD + " " + ChatColor.BOLD + "Talents",
                "",
                ChatColor.YELLOW + "  Slot 1: " + (playerData.hasTalent(0) ? playerData.getTalent(0).getItemMeta().getDisplayName() : ChatColor.GRAY + "Empty"),
                ChatColor.YELLOW + "  Slot 2: " + (playerData.hasTalent(1) ? playerData.getTalent(1).getItemMeta().getDisplayName()  : ChatColor.GRAY + "Empty"),
                ChatColor.YELLOW + "  Slot 3: " + (playerData.hasTalent(2) ? playerData.getTalent(2).getItemMeta().getDisplayName()  : ChatColor.GRAY + "Empty"),
                ""
        );
    }

    @Override
    public void addContent(Inventory inventory) {
        for (int i = 0; i < inventory.getSize(); ++i) {
            switch (i) {
                case 1 -> {
                    inventory.setItem(i, new ItemBuilder(Material.FILLED_MAP, 1)
                            .setName(ChatColor.GOLD + "" + ChatColor.BOLD + "Slot 1")
                            .addEnchant(Enchantment.DURABILITY, 1)
                            .addFlag(ItemFlag.HIDE_ENCHANTS)
                            .toItemStack()
                    );
                }
                case 4 -> {
                    inventory.setItem(i, new ItemBuilder(Material.NETHER_STAR, 1)
                            .setName(ChatColor.BLUE + "" + ChatColor.BOLD + "Slot 2")
                            .addEnchant(Enchantment.DURABILITY, 1)
                            .addFlag(ItemFlag.HIDE_ENCHANTS)
                            .toItemStack()
                    );
                }
                case 7 -> {
                    inventory.setItem(i, new ItemBuilder(Material.EMERALD, 1)
                            .setName(ChatColor.GREEN + "" + ChatColor.BOLD + "Slot 3")
                            .addEnchant(Enchantment.DURABILITY, 1)
                            .addFlag(ItemFlag.HIDE_ENCHANTS)
                            .toItemStack()
                    );
                }
                case 10, 13, 16 -> {
                    int index = (i - 10) / 3;

                    if (this.playerData.hasTalent(index)) {
                        inventory.setItem(i, playerData.getTalent(index));
                    }
                }
                default -> {
                    inventory.setItem(i, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE, 1)
                            .setName(" ")
                            .toItemStack()
                    );
                }
            }
        }
    }

    @Override
    public Inventory getInventory() {
        Inventory inventory = Bukkit.createInventory(this, 27, ChatColor.BLUE + "" + ChatColor.BOLD + "Talents");
        addContent(inventory);
        return inventory;
    }

    /**
     * Pretty
     */
    public Inventory create() {
        return getInventory();
    }

    private boolean isTalentItem(ItemStack item) {
        if (!item.hasItemMeta()) return false;

        for (Items items : Items.values()) {
            if (items.getItem().getItemMeta().getPersistentDataContainer().equals(item.getItemMeta().getPersistentDataContainer())) {
                return true;
            }
        }
        return false;
    }

    private boolean talentExists(ItemStack[] talents, ItemStack item) {
        for (ItemStack talent : talents) {
            if (talent != null) {
                if (talent.getItemMeta().getPersistentDataContainer().equals(item.getItemMeta().getPersistentDataContainer())) {
                    return true;
                }
            }
        }
        return false;
    }

    private void applyAttributeModifiers(@Nullable ItemStack item, Player player) {
        if (item == null) return;
        if (!item.hasItemMeta()) return;
        if (!item.getItemMeta().hasAttributeModifiers()) return;

        for (Map.Entry<Attribute, AttributeModifier> entry : item.getItemMeta().getAttributeModifiers().entries()) {
            player.getAttribute(entry.getKey()).addModifier(entry.getValue());
        }
    }

    private void removeAttributeModifiers(Player player) {
        for (Attribute attribute : Attribute.values()) {
            if (player.getAttribute(attribute) != null) {
                player.getAttribute(attribute).getModifiers().forEach(modifier -> {
                    player.getAttribute(attribute).removeModifier(modifier);
                });
            }
        }
    }
}
