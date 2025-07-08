package com.meowverdose.pathstriders.talents;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public enum TalentType {

    IN_THE_NIGHT(
        "in_the_night",
            "§9In the Night",
            List.of(
                    "§7Flowers and Butterflies",
                    " ",
                    "§e☆☆☆☆☆",
                    " ",
                    "§eHonkai: Star Rail §6§lLight Cone §eseries",
                    " ",
                    "§7When in Off-hand:",
                    "§2+18% CRIT RATE",
                    "§2+6% ATK per 0.01 SPD (max 4 stacks)",
                    " ",
                    "§7A young girl smiles subtly.",
                    "§7§o\"How?\"",
                    "§7§o\"Right here, right now I am alone...\"",
                    "§7§o\"But it feels... very lively...\""
            ),
            Material.PAPER,
            (player) -> {
                player.sendMessage(ChatColor.GREEN + "Talents: In the Night equipped!");
            },
            (player) -> {
                player.sendMessage(ChatColor.RED + "Talents: In the Night unequipped.");
            },
            (player, target) -> {
                // CRIT RATE +18%
                Random random = new Random();
                if (random.nextDouble() < 0.18) {
                    double critBonus = 2.0;
                    target.damage(critBonus, player);
                    player.sendMessage(ChatColor.BLUE + "In The Night: CRIT hit! +" + critBonus + " damage.");
                }

                // SPD DMG SCALE +6% DMG/0.1 SPD
                double base = 0.1;
                double curr = player.getAttribute(Attribute.MOVEMENT_SPEED).getValue();
                double diff = Math.max(0, curr - base);
                int stacks = (int) Math.min(4, (diff * 1000) / 100); // +1 stack/0.1 SPD
                double bonus = stacks * 0.06; // +6% DMG/stack

                // TODO debug this
                if (bonus > 0) {
                    double bonusDmg = 2.0 * bonus;
                    target.damage(bonusDmg, player);
                }
            },
            (player) -> {} // No active ability

    );

    private final String id;
    private final String name;
    private final List<String> lore;
    private final Material material;
    private final Consumer<Player> onEquip;
    private final Consumer<Player> onUnequip;
    private final BiConsumer<Player, LivingEntity> onAttack;
    private final Consumer<Player> onRightClick;

    TalentType(String id, String name, List<String> lore, Material material,
           Consumer<Player> onEquip,
           Consumer<Player> onUnequip,
           BiConsumer<Player, LivingEntity> onAttack,
           Consumer<Player> onRightClick) {
        this.id = id;
        this.name = name;
        this.lore = lore;
        this.material = material;
        this.onEquip = onEquip;
        this.onUnequip = onUnequip;
        this.onAttack = onAttack;
        this.onRightClick = onRightClick;
    }

    public String getId() {
        return id;
    }

    public ItemStack createItem(NamespacedKey key) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(lore);
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, id);
        meta.addEnchant(Enchantment.UNBREAKING, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(meta);
        return item;
    }

    public boolean isTalent(ItemStack item, NamespacedKey key) {
        if (item == null || !item.hasItemMeta()) return false;
        ItemMeta meta = item.getItemMeta();
        return meta.getPersistentDataContainer().has(key, PersistentDataType.STRING)
                && meta.getPersistentDataContainer().get(key, PersistentDataType.STRING).equals(id);
    }

    public void onEquip(Player player) {
        onEquip.accept(player);
    }

    public void onUnequip(Player player) {
        onUnequip.accept(player);
    }

    public void onAttack(Player player, LivingEntity target) {
        onAttack.accept(player, target);
    }

    public void onRightClick(Player player) {
        onRightClick.accept(player);
    }
}
