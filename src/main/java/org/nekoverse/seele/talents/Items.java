package org.nekoverse.seele.talents;

import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.nekoverse.seele.utils.ItemBuilder;

import java.util.UUID;

public enum Items {

    IN_THE_NIGHT(new ItemBuilder(Material.PAPER)
            .setName("&9In The Night")
            .setLore(
                    "&7Flowers and Butterflies",
                    "",
                    "&eHonkai: Star Rail &6&lLight Cone &eSeries",
                    "",
                    "&7When in Talents:",
                    " &d+18% ATK SPD",
                    " &d+4% ATK DMG per 0.01 SPD. This effect can stack up to 4 time(s)"
            )
            .addEnchant(Enchantment.DURABILITY, 1)
            .addData("Flowers_And_Butterflies")
            .addFlag(ItemFlag.HIDE_ENCHANTS)
            .addFlag(ItemFlag.HIDE_ATTRIBUTES)
            .addAttributeModifier(
                    Attribute.GENERIC_ATTACK_SPEED,
                    new AttributeModifier(
                            UUID.randomUUID(),
                            "ATK_SPD",
                            0.18,
                            AttributeModifier.Operation.MULTIPLY_SCALAR_1,
                            EquipmentSlot.FEET
                    )
            )
            .toItemStack()
    ),
    MOMENT_OF_VICTORY(new ItemBuilder(Material.PAPER)
            .setName("&eMoment of Victory")
            .setLore(
                    "&7Verdict",
                    "",
                    "&eHonkai: Star Rail &6&lLight Cone &eSeries",
                    "",
                    "&7When in Talents:",
                    " &d+24% DMG RES",
                    " &d-4% SPD"
            )
            .addEnchant(Enchantment.DURABILITY, 1)
            .addData("Verdict")
            .addFlag(ItemFlag.HIDE_ENCHANTS)
            .addFlag(ItemFlag.HIDE_ATTRIBUTES)
            .addAttributeModifier(
                    Attribute.GENERIC_ARMOR_TOUGHNESS,
                    new AttributeModifier(
                            UUID.randomUUID(),
                            "DMG_RES",
                            0.24,
                            AttributeModifier.Operation.MULTIPLY_SCALAR_1,
                            EquipmentSlot.FEET
                    )
            )
            .addAttributeModifier(
                    Attribute.GENERIC_MOVEMENT_SPEED,
                    new AttributeModifier(
                            UUID.randomUUID(),
                            "SPD",
                            -0.04,
                            AttributeModifier.Operation.MULTIPLY_SCALAR_1,
                            EquipmentSlot.FEET
                    )
            )
            .toItemStack()
    ),
    INCESSANT_RAIN(new ItemBuilder(Material.PAPER)
            .setName("&dIncessant Rain")
            .setLore(
                    "&7Mirage of Reality",
                    "",
                    "&eHonkai: Star Rail &6&lLight Cone &eSeries",
                    "",
                    "&7When in Talents:",
                    " &d+15% chance to apply a random debuff",
                    " &don target",
                    " &d+4% ATK DMG"
            )
            .addEnchant(Enchantment.DURABILITY, 1)
            .addData("Mirage_Of_Reality")
            .addFlag(ItemFlag.HIDE_ENCHANTS)
            .addFlag(ItemFlag.HIDE_ATTRIBUTES)
            .addAttributeModifier(
                    Attribute.GENERIC_ATTACK_DAMAGE,
                    new AttributeModifier(
                            UUID.randomUUID(),
                            "ATK_DMG",
                            0.04,
                            AttributeModifier.Operation.MULTIPLY_SCALAR_1,
                            EquipmentSlot.FEET
                    )
            )
            .toItemStack()
    ),
    LOST_PRAYER(new ItemBuilder(Material.ENCHANTED_BOOK)
            .setName("&bLost Prayer to the Sacred Winds")
            .setLore(
                    "&7Boundless Blessing",
                    "",
                    "&dGenshin Impact &5&lCatalyst &dSeries",
                    "",
                    "&7When in Talents:",
                    " &d+10% SPD",
                    " &d+6% ATK DMG"
            )
            .addEnchant(Enchantment.DURABILITY, 1)
            .addData("Boundless_Blessing")
            .addFlag(ItemFlag.HIDE_ENCHANTS)
            .addFlag(ItemFlag.HIDE_ATTRIBUTES)
            .addAttributeModifier(
                    Attribute.GENERIC_MOVEMENT_SPEED,
                    new AttributeModifier(
                            UUID.randomUUID(),
                            "SPD",
                            0.1,
                            AttributeModifier.Operation.MULTIPLY_SCALAR_1,
                            EquipmentSlot.FEET
                    )
            )
            .addAttributeModifier(
                    Attribute.GENERIC_ATTACK_DAMAGE,
                    new AttributeModifier(
                            UUID.randomUUID(),
                            "ATK_DMG",
                            0.06,
                            AttributeModifier.Operation.MULTIPLY_SCALAR_1,
                            EquipmentSlot.FEET
                    )
            )
            .toItemStack()
    ),
    SKYWARD_ATLAS(new ItemBuilder(Material.ENCHANTED_BOOK)
            .setName("&bSkyward Atlas")
            .setLore(
                    "&7Wandering Clouds",
                    "",
                    "&dGenshin Impact &5&lCatalyst &dSeries",
                    "",
                    "&7When in Talents:",
                    " &d+6% ATK DMG",
                    " &d+12% chance to deal 150% ATK DMG"
            )
            .addEnchant(Enchantment.DURABILITY, 1)
            .addData("Wandering_Clouds")
            .addFlag(ItemFlag.HIDE_ENCHANTS)
            .addFlag(ItemFlag.HIDE_ATTRIBUTES)
            .addAttributeModifier(
                    Attribute.GENERIC_ATTACK_DAMAGE,
                    new AttributeModifier(
                            UUID.randomUUID(),
                            "ATK_DMG",
                            0.06,
                            AttributeModifier.Operation.MULTIPLY_SCALAR_1,
                            EquipmentSlot.FEET
                    )
            )
            .toItemStack()
    );

    private final ItemStack item;

    Items(ItemStack item) {
        this.item = item;
    }

    public ItemStack getItem() {
        return new ItemStack(this.item);
    }
}
