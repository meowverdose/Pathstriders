package cc.chocochip.seele.ability;

import com.google.gson.*;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.lang.reflect.Type;

public class ItemStackAdapter implements JsonSerializer<ItemStack>, JsonDeserializer<ItemStack> {
    @Override
    public JsonElement serialize(ItemStack itemStack, Type type, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("type", itemStack.getType().name());
        jsonObject.addProperty("amount", itemStack.getAmount());
        jsonObject.addProperty("durability", itemStack.getDurability());
        // You can add more properties as needed

        // Serialize ItemMeta
        if (itemStack.hasItemMeta()) {
            ItemMeta itemMeta = itemStack.getItemMeta();
            jsonObject.add("meta", context.serialize(itemMeta));
        }

        return jsonObject;
    }

    @Override
    public ItemStack deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        String typeName = jsonObject.get("type").getAsString();
        int amount = jsonObject.get("amount").getAsInt();
        short durability = jsonObject.get("durability").getAsShort();
        // You can retrieve more properties as needed

        // Deserialize ItemMeta
        ItemStack itemStack = new ItemStack(Material.getMaterial(typeName), amount, durability);
        if (jsonObject.has("meta")) {
            JsonElement metaElement = jsonObject.get("meta");
            ItemMeta itemMeta = context.deserialize(metaElement, ItemMetaAdapter.class);
            itemStack.setItemMeta(itemMeta);
        }

        return itemStack;
    }
}