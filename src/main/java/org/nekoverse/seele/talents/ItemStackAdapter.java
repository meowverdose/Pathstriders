package org.nekoverse.seele.talents;

import org.nekoverse.seele.Seele;
import com.google.gson.*;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Custom type adapter for ItemStack.java
 * Utilized by Gson for itemstack json serializing and deserializing
 */
public class ItemStackAdapter implements JsonSerializer<ItemStack>, JsonDeserializer<ItemStack> {

    @Override
    public JsonElement serialize(ItemStack item, Type type, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();

        // Serialize basic ItemStack properties
        jsonObject.addProperty("type", item.getType().ordinal());
        jsonObject.addProperty("amount", item.getAmount());
        jsonObject.addProperty("durability", item.getDurability());

        // Serialize ItemMeta
        if (item.hasItemMeta()) {
            ItemMeta meta = item.getItemMeta();
            JsonObject metaObject = new JsonObject();

            assert meta != null;

            // Serialize displayname
            metaObject.addProperty("displayName", meta.getDisplayName());

            // Serialize enchantments
            if (meta.hasEnchants()) {
                JsonObject enchantmentsObject = new JsonObject();
                for (Map.Entry<Enchantment, Integer> entry : meta.getEnchants().entrySet()) {
                    enchantmentsObject.addProperty(entry.getKey().getName(), entry.getValue());
                }
                metaObject.add("enchantments", enchantmentsObject);
            }

            // Serialize item flags
            if (meta.getItemFlags().size() != 0) {
                JsonArray flagsArray = new JsonArray();
                for (ItemFlag flag : meta.getItemFlags()) {
                    flagsArray.add(flag.ordinal());
                }
                metaObject.add("itemFlags", flagsArray);
            }

            // Serialize PDC
            if (!meta.getPersistentDataContainer().isEmpty()) {
                JsonArray pdcArray = new JsonArray();
                for (NamespacedKey key : meta.getPersistentDataContainer().getKeys()) {
                    pdcArray.add(meta.getPersistentDataContainer().get(key, PersistentDataType.STRING));
                }
                metaObject.add("pdc", pdcArray);
            }

            // Serialize attribute mods
            if (meta.hasAttributeModifiers()) {
                JsonObject attributesObject = new JsonObject();
                for (Map.Entry<Attribute, AttributeModifier> entry : meta.getAttributeModifiers().entries()) {
                    JsonObject modifierObject = new JsonObject();
                    /*for (Map.Entry<String, Object> modifierEntry : entry.getValue().serialize().entrySet()) {
                        modifierObject.addProperty(modifierEntry.getKey(), modifierEntry.getValue().toString());
                    }*/                                                 // For each loop; saves as strings not primitives
                    modifierObject.addProperty("uuid", entry.getValue().getUniqueId().toString());
                    modifierObject.addProperty("name", entry.getValue().getName());
                    modifierObject.addProperty("amount", entry.getValue().getAmount());
                    modifierObject.addProperty("operation", entry.getValue().getOperation().ordinal());
                    modifierObject.addProperty("slot", entry.getValue().getSlot().ordinal());
                    attributesObject.add(entry.getKey().name(), modifierObject);
                }
                metaObject.add("attributeModifiers", attributesObject);
            }

            // Serialize lore
            if (meta.hasLore()) {
                JsonArray loreArray = new JsonArray();
                for (String loreLine : meta.getLore()) {
                    loreArray.add(loreLine);
                }
                metaObject.add("lore", loreArray);
            }
            jsonObject.add("meta", metaObject);
        }
        return jsonObject;
    }

    @Override
    public ItemStack deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        // Deserialize basic ItemStack properties
        ItemStack item = new ItemStack(Material.values()[jsonObject.get("type").getAsInt()]);
        item.setAmount(jsonObject.get("amount").getAsInt());
        item.setDurability(jsonObject.get("durability").getAsShort());


        // Deserialize ItemMeta
        if (jsonObject.has("meta")) {
            JsonObject metaObject = jsonObject.getAsJsonObject("meta");
            ItemMeta meta = item.getItemMeta();

            assert meta != null;

            // Deserialize displayname
            if (metaObject.has("displayName")) {

                meta.setDisplayName(metaObject.get("displayName").getAsString());
            }

            // Deserialize enchantments
            if (metaObject.has("enchantments")) {
                JsonObject enchantmentsObject = metaObject.getAsJsonObject("enchantments");
                for (Map.Entry<String, JsonElement> entry : enchantmentsObject.entrySet()) {
                    Enchantment enchantment = Enchantment.getByName(entry.getKey());
                    if (enchantment != null) {
                        meta.addEnchant(enchantment, entry.getValue().getAsInt(), true);
                    }
                }
            }

            // Serialize PDC
            if (metaObject.has("pdc")) {
                JsonArray pdcArray = metaObject.getAsJsonArray("pdc");
                for (JsonElement pdcElement : pdcArray) {
                    meta.getPersistentDataContainer().set(
                            new NamespacedKey(Seele.getInstance(), pdcElement.getAsString()),
                            PersistentDataType.STRING,
                            pdcElement.getAsString()
                    );
                }
            }

            // Deserialize flags
            if (metaObject.has("itemFlags")) {
                JsonArray jsonArray = metaObject.getAsJsonArray("itemFlags");
                for (JsonElement flagElement : jsonArray) {
                    ItemFlag flag = ItemFlag.values()[flagElement.getAsInt()];
                    meta.addItemFlags(flag);
                }
            }

            // Deserialize mods
            if (metaObject.has("attributeModifiers")) {
                JsonObject attributesObject = metaObject.getAsJsonObject("attributeModifiers");
                for (Map.Entry<String, JsonElement> entry : attributesObject.entrySet()) {
                    if (entry != null) {
                        JsonObject modifierObject = entry.getValue().getAsJsonObject();
                        AttributeModifier modifier = new AttributeModifier(
                                UUID.fromString(modifierObject.get("uuid").getAsString()),
                                modifierObject.get("name").getAsString(),
                                modifierObject.get("amount").getAsDouble(),
                                AttributeModifier.Operation.values()[modifierObject.get("operation").getAsInt()],
                                EquipmentSlot.values()[modifierObject.get("slot").getAsInt()]
                        );
                        meta.addAttributeModifier(Attribute.valueOf(entry.getKey()), modifier);
                    }
                }
            }

            // Deserialize lore
            if (metaObject.has("lore")) {
                JsonArray loreArray = metaObject.getAsJsonArray("lore");

                List<String> lore = new ArrayList<>();
                for (JsonElement loreElement : loreArray) {
                    lore.add(loreElement.getAsString());
                }
                meta.setLore(lore);
            }
            item.setItemMeta(meta);
        }
        return item;
    }
}