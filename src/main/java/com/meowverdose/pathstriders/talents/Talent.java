package com.meowverdose.pathstriders.talents;

import com.meowverdose.pathstriders.Pathstriders;
import com.meowverdose.pathstriders.util.PlayerUtil;
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
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public enum Talent {

    /** TODO
     * Murasame
     * Kiriichimonji
     */

    /**
     * Honkai: Star Rail: Light Cone series
     */
    IN_THE_NIGHT(
        "in_the_night",
            "§9In the Night",
            List.of(
                    "§eFlowers and Butterflies",
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
            ),
            Material.PAPER,
            null,
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
            (player) -> {}
    ),

    INCESSANT_RAIN(
            "incessant_rain",
            "§dIncessant Rain",
            List.of(
                    "§eMirage of Reality",
                    " ",
                    "§e☆☆☆☆☆",
                    " ",
                    "§eHonkai: Star Rail §6§lLight Cone §eseries",
                    " ",
                    "§7When in Talents:",
                    "§2+12% CRIT RATE",
                    "§2+10% to apply Aether Code (random debuff) to targets",
                    "§2 - Targets afflicted with Aether Code receive +6% DMG",
                    " ",
                    "§7\"Gaming and rain totally go together.\""
            ),
            Material.PAPER,
            null,
            (player) -> {
                player.sendMessage(ChatColor.GREEN + "Talents: Incessant Rain equipped!");
            },
            (player) -> {
                player.sendMessage(ChatColor.RED + "Talents: Incessant Rain unequipped.");
            },
            (player, target) -> {
                // CRIT RATE +12%
                Random random = new Random();
                if (random.nextDouble() < 0.12) {
                    double critBonus = 2.0;
                    target.damage(critBonus, player);
                    player.sendMessage(ChatColor.BLUE + "Incessant Rain: CRIT hit! +" + critBonus + " damage.");
                }

                if (random.nextDouble() < 0.10) {
                    PotionEffectType[] debuffs = {
                            PotionEffectType.MINING_FATIGUE,
                            PotionEffectType.BLINDNESS,
                            PotionEffectType.DARKNESS,
                            PotionEffectType.HUNGER,
                            PotionEffectType.LEVITATION,
                            PotionEffectType.NAUSEA,
                            PotionEffectType.POISON,
                            PotionEffectType.SLOWNESS,
                            PotionEffectType.WEAKNESS,
                            PotionEffectType.WITHER
                    };

                    PotionEffect debuff = new PotionEffect(debuffs[random.nextInt(debuffs.length - 1)], 60, 0);
                    target.addPotionEffect(debuff);
                    player.sendMessage(ChatColor.BLUE + "Aether Code: Applied " + debuff.getType().getName() + " to " + target.getName() + ".");
                }
            },
            (player) -> {}
    ),

    /**
     * Akame Ga Kill!: Shingu/Tengu series
     */
    MURASAME(
           "murasame",
            "§cMurasame",
            List.of(
                    "§7One-Cut Killing",
                    " ",
                    "§e☆☆☆☆☆",
                    " ",
                    "§cAkame Ga Kill!: §4§lTengu §cseries",
                    " ",
                    "§7When in main hand:",
                    "§2Attacks apply One-Cut Killing to targets for 5s",
                    " ",
                    "§7When activated:",
                    "§2Little War Horn: XXX", // TODO
                    " ",
                    "§7\"A cursed blade that kills with a single cut. Once its poison enters the bloodstream, death is certain.\""
            ),
            Material.IRON_SWORD,
            Map.of(
                    Enchantment.SHARPNESS, 6
            ),
            (player) -> {
                player.sendMessage(ChatColor.GREEN + "Talents: Murasame equipped!");
            },
            (player) -> {
                player.sendMessage(ChatColor.RED + "Talents: Murasame unequipped.");
            },
            (player, target) -> {
               // one cut killing logic todo
            },
            (player) -> {
               // little war horn logic todo
            }
    ),

    /**
     * Rakuaka: Original Magic series
     */
    THE_FOOL(
            "the_fool",
            "§9The Fool",
            List.of(
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
            ),
            Material.PAPER,
            null,
            (player) -> {
                player.sendMessage(ChatColor.GREEN + "Talents: The Fool equipped!");
            },
            (player) -> {
                player.sendMessage(ChatColor.RED + "Talents: The Fool unequipped.");
            },
            (player, target) -> {},
            (player) -> {
                Pathstriders.getInstance().getPlayerDataManager().setCooldown(player, "the_fool", 60_000);

                List<Player> nearbyPlayers = PlayerUtil.getPlayersWithinRadius(player, 5);
                for (Player players : nearbyPlayers) {

                    if (players.equals(player)) { // Caster
                        players.sendMessage(ChatColor.RED + "Talents: You activated The Fool's World! All players (including you) within 5 blocks are disabled and unable to use their talents.");
                    } else { // Other players
                        players.sendMessage(ChatColor.RED + "Talents: " + player.getName() + " has activated The Fool's World! Your talents have been disabled...");
                    }
                }
            }
    );

    private final String id;
    private final String name;
    private final List<String> lore;
    private final Material material;
    private final Map<Enchantment, Integer> enchants;
    private final Consumer<Player> onEquip;
    private final Consumer<Player> onUnequip;
    private final BiConsumer<Player, LivingEntity> onAttack;
    private final Consumer<Player> onRightClick;

    Talent(String id, String name, List<String> lore, Material material,
           Map<Enchantment, Integer> enchants,
           Consumer<Player> onEquip,
           Consumer<Player> onUnequip,
           BiConsumer<Player, LivingEntity> onAttack,
           Consumer<Player> onRightClick) {
        this.id = id;
        this.name = name;
        this.lore = lore;
        this.material = material;
        this.enchants = enchants;
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
        if (enchants != null && !enchants.isEmpty()) {
            enchants.forEach((enchant, level) -> {
                meta.addEnchant(enchant, level, true);
            });
        }
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
