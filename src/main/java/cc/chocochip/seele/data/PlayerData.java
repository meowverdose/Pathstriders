package cc.chocochip.seele.data;

import cc.chocochip.seele.ability.Items;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class PlayerData {

    private final UUID uniqueId;
    private ItemStack[] talents;

    public PlayerData(UUID uniqueId) {
        this.uniqueId = uniqueId;
        this.talents = new ItemStack[3];
    }

    public UUID getUniqueId() {
        return this.uniqueId;
    }

    public ItemStack[] getTalents() {
        return talents;
    }


}
