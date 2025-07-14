package com.meowverdose.pathstriders.talents;

import com.meowverdose.pathstriders.Pathstriders;
import com.meowverdose.pathstriders.managers.PlayerDataManager;
import com.meowverdose.pathstriders.util.PlayerUtil;
import com.meowverdose.pathstriders.util.TriConsumer;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public enum Talent {

    /**
     * Honkai: Star Rail: Light Cone series
     */
    IN_THE_NIGHT(
            EquipmentSlot.OFF_HAND,
            new ItemBuilder(Material.PAPER)
                    .setName("§9In The Night")
                    .addLore(
                            "§7Flowers and Butterflies",
                            " ",
                            "§e☆☆☆☆☆",
                            " ",
                            "§eHonkai: Star Rail §6§lLight Cone §eseries",
                            " ",
                            "§7When in Talents:",
                            "§2+18% CRIT RATE",
                            "§2+6% ATK per 0.01 SPD (max 4 stacks)",
                            " ",
                            "§7A young girl smiles subtly.",
                            "§7§o\"How?\"",
                            "§7§o\"Right here, right now I am alone...\"",
                            "§7§o\"But it feels... very lively...\""
                    )
                    .setTalentKey("in_the_night")
                    .build(),
            player -> { // Passive
            },
            (player, playerDataManager) -> { // Active
            },
            (attacker, victim, event) -> { // onAttack
                Random random = new Random();

                if (random.nextDouble() < 0.18) {
                    event.setDamage(event.getDamage() + 2.0);
                    attacker.sendMessage("§aCRITICAL! +2 ATK DMG");
                }

                // todo fix broken
                double baseSpeed = 0.1;
                double currSpeed = attacker.getAttribute(Attribute.MOVEMENT_SPEED).getValue();
                double diffSpeed = Math.max(0, currSpeed - baseSpeed);
                int stacks = (int) Math.min(4, diffSpeed * 100); // +1 stack/0.1 SPD
                double bonusDamage = stacks * 0.06; // +6% DMG/stack

                if (bonusDamage > 0) {
                    event.setDamage(event.getDamage() + bonusDamage);
                }
            },
            0L
    ),

    /**
     * Rakuaka: Original Magic series
     */
    THE_FOOL(
        EquipmentSlot.OFF_HAND,
            new ItemBuilder(Material.PAPER)
                    .setName("§9The Fool")
                    .addLore(
                            "§7The Fool's World",
                            " ",
                            "§e☆☆☆☆☆",
                            " ",
                            "§bRakuaka: §9§lOriginal Magic §bseries",
                            " ",
                            "§7When activated:",
                            "§2The Fool's World: Within 5 blocks, all talents become disabled for 30 seconds",
                            "§2 - Cooldown: 60s",
                            " ",
                            "§7\"Magic’s overrated. Watch closely—I’ll show you what a real fight looks like.\""
                    )
                    .setTalentKey("the_fool")
                    .build(),
            player -> { // Passive
            },
            (player, playerDataManager) -> { // Active
                for (Player players : PlayerUtil.getPlayersWithinRadius(player, 5)) {
                    playerDataManager.applyEffect(players, TalentEffect.THE_FOOLS_WORLD);
                }
            },
            (attacker, victim, event) -> { // onAttack
            },
            60_000L
    );

    private final EquipmentSlot slot;
    private final ItemStack item;
    private final Consumer<Player> passiveEffect;
    private final BiConsumer<Player, PlayerDataManager> activeEffect; // BiConsumer may be better for future fx where select-targeting is needed
    private final TriConsumer<Player, LivingEntity, EntityDamageByEntityEvent> onAttackEffect;
    private final long cooldown; // ms * 10000

    Talent(
            EquipmentSlot slot,
            ItemStack item,
            Consumer<Player> passiveEffect,
            BiConsumer<Player, PlayerDataManager> activeEffect,
            TriConsumer<Player, LivingEntity, EntityDamageByEntityEvent> onAttackEffect,
            long cooldown
    ) {
        this.slot = slot;
        this.item = item;
        this.passiveEffect = passiveEffect;
        this.activeEffect = activeEffect;
        this.onAttackEffect = onAttackEffect;
        this.cooldown = cooldown;
    }

    public EquipmentSlot getSlot() {
        return slot;
    }

    public ItemStack getItem() {
        return item;
    }

    public Consumer<Player> getPassiveEffect() {
        return passiveEffect;
    }

    public BiConsumer<Player, PlayerDataManager> getActiveEffect() {
        return activeEffect;
    }

    public TriConsumer<Player, LivingEntity, EntityDamageByEntityEvent> getOnAttackEffect() {
        return onAttackEffect;
    }

    public long getCooldown() {
        return cooldown;
    }

    public static Talent fromItem(ItemStack item) {
        if (item == null || !item.hasItemMeta()) return null;

        String id = item.getItemMeta().getPersistentDataContainer()
                .get(Pathstriders.TALENT_ID_KEY, PersistentDataType.STRING);

        if (id == null) return null;

        for (Talent talent : values()) {
            if (talent.name().equalsIgnoreCase(id)) return talent;
        }
        return null;
    }

    // --- Embedded ItemBuilder ---
    private static class ItemBuilder {
        private final ItemStack item;
        private final ItemMeta meta;

        public ItemBuilder(Material material) {
            item = new ItemStack(material);
            meta = item.getItemMeta();
        }

        public ItemBuilder setName(String name) {
            meta.setDisplayName(name);
            return this;
        }

        public ItemBuilder addLore(String... lines) {
            List<String> lore = meta.hasLore() ? meta.getLore() : new ArrayList<>();
            Collections.addAll(lore, lines);
            meta.setLore(lore);
            return this;
        }

        public ItemBuilder setTalentKey(String talentId) {
            meta.getPersistentDataContainer().set(Pathstriders.TALENT_ID_KEY, PersistentDataType.STRING, talentId);
            return this;
        }

        public ItemBuilder hideEnchantments(boolean hide) {
            if (hide) {
                meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }
            return this;
        }

        public ItemStack build() {
            item.setItemMeta(meta);
            return item;
        }
    }
}
