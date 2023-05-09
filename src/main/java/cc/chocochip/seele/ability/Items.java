package cc.chocochip.seele.ability;

import cc.chocochip.seele.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum Items {

    IN_THE_NIGHT(new ItemBuilder(Material.MAP)
            .setName("&9In The Night")
            .setLore(
                    "&7Flowers and Butterflies",
                    "",
                    "&e✫✫✫✫✫",
                    "",
                    "&7When in Talents:",
                    " &2+18% CRIT RATE",
                    " &2+6% ATK per 0.01 SPD. This effect can stack up to 4 time(s)"
            )
            .hideEnchants()
            .toItemStack()
    );

    private ItemStack item;

    Items(ItemStack item) {
        this.item = item;
    }

    public ItemStack getItem() {
        return this.item;
    }
}
