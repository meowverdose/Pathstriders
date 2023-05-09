package cc.chocochip.seele.utils;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemBuilder {

    private ItemStack item;

    public ItemBuilder(Material type, int amount, byte durability) {
        this.item = new ItemStack(type, amount, durability);
    }

    public ItemBuilder(Material type, int amount) {
        this.item = new ItemStack(type, amount);
    }

    public ItemBuilder(Material type) {
        this(type, 1);
    }

    public ItemBuilder(ItemStack item) {
        this.item = new ItemStack(item);
    }

    public ItemBuilder clone() {
        return new ItemBuilder(item);
    }

    public ItemBuilder setDurability(short durability) {
        item.setDurability(durability);
        return this;
    }

    public ItemBuilder setUnbreakable() {
        ItemMeta meta = item.getItemMeta();
        meta.setUnbreakable(true);
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder hideEnchants() {
        ItemMeta meta = item.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setName(String name) {
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setLore(String... lore) {
        List<String> coloredLore = new ArrayList<String>();
        for (String line : lore) {
             coloredLore.add(ChatColor.translateAlternateColorCodes('&', line));
        }
        ItemMeta meta = item.getItemMeta();
        meta.setLore(coloredLore);
        item.setItemMeta(meta);
        return this;
    }

    public ItemStack toItemStack() {
        return this.item;
    }

}
