package org.nekoverse.seele.talents;

import org.nekoverse.seele.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public enum Items {

    IN_THE_NIGHT(new ItemBuilder(Material.PAPER)
            .setName("&9In The Night")
            .setLore(
                    "&7Flowers and Butterflies",
                    "",
                    "&e✫✫✫✫✫",
                    "",
                    "&eHonkai: Star Rail &6&lLight Cone &eSeries",
                    "",
                    "&7When in Talents:",
                    " &2+18% ATK SPD",
                    " &2+6% ATK per 0.01 SPD. This effect can stack up to 4 time(s)"
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
                        EquipmentSlot.OFF_HAND
                    ) // TODO: 6/10/2023 fix off hand for talent slot
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
