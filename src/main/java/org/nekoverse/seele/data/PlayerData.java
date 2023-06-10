package org.nekoverse.seele.data;

import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.UUID;

public class PlayerData {

    private final UUID uniqueId;
    private final ItemStack[] talents;

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

    public void setTalent(int index, @Nullable ItemStack item) {
        this.talents[index] = (item == null ? null : item.clone());
    }



    @Override
    public String toString() {
        return uniqueId.toString();
    }
}
