package cc.chocochip.seele.ability;

import com.google.gson.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemMetaAdapter implements JsonSerializer<ItemMeta>, JsonDeserializer<ItemMeta> {
    @Override
    public JsonElement serialize(ItemMeta itemMeta, java.lang.reflect.Type type, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();

        // Serialize itemMeta properties
        // Example: Serialize display name
        if (itemMeta.hasDisplayName()) {
            jsonObject.addProperty("displayName", itemMeta.getDisplayName());
        }

        // You can add more properties as needed

        return jsonObject;
    }

    @Override
    public ItemMeta deserialize(JsonElement json, java.lang.reflect.Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        // Deserialize itemMeta properties
        // Example: Deserialize display name
        String displayName = null;
        if (jsonObject.has("displayName")) {
            displayName = jsonObject.get("displayName").getAsString();
        }

        // Create an instance of ItemMeta (specific implementation) and set properties
        ItemMeta itemMeta = /* Create your specific ItemMeta instance here */;
        if (displayName != null) {
            itemMeta.setDisplayName(displayName);
        }

        // You can set more properties as needed

        return itemMeta;
    }
}