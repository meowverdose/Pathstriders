package cc.chocochip.seele.data;

import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.UUID;

public class PlayerData {

    private final UUID uniqueId;
    private ItemStack[] talents;

    public PlayerData(UUID uniqueId) {
        this.uniqueId = uniqueId;
        this.talents = new ItemStack[4];
    }

    public UUID getUniqueId() {
        return this.uniqueId;
    }

    public ItemStack[] getTalents() {
        return talents;
    }

    public void setTalent(int index, ItemStack item) {
        this.talents[index] = item;
    }

    @Override
    public String toString() {
        return Arrays.toString(talents);
    }
}
