package cc.chocochip.seele.utils;

import cc.chocochip.seele.Seele;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
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

    public ItemBuilder addFlag(ItemFlag flag) {
        ItemMeta meta = item.getItemMeta();
        meta.addItemFlags(flag);
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder addData(String data) {
        ItemMeta meta = item.getItemMeta();
        meta.getPersistentDataContainer().set(new NamespacedKey(Seele.getInstance(), data), PersistentDataType.STRING, data);
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder addEnchant(Enchantment ench, int lvl) {
        ItemMeta meta = item.getItemMeta();
        meta.addEnchant(ench, lvl, true);
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder addAttributeModifier(Attribute attribute, AttributeModifier modifier) {
        ItemMeta meta = item.getItemMeta();
        //meta.getAttributeModifiers(EquipmentSlot.OFF_HAND).put(attribute, modifier);
        meta.addAttributeModifier(attribute, modifier);
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
