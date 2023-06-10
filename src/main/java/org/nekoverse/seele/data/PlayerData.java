package org.nekoverse.seele.data;

import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;
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

    public ItemStack getLightCone() {
        return this.talents[0];
    }

    public ItemStack getArtifact() {
        return this.talents[1];
    }

    public ItemStack getRelic() {
        return this.talents[2];
    }

    public boolean hasLightCone() {
        return this.talents[0] != null;
    }

    public boolean hasArtifact() {
        return this.talents[1] != null;
    }

    public boolean hasRelic() {
        return this.talents[2] != null;
    }

    public ItemStack getTalent(int index) {
        return this.talents[index];
    }

    public boolean hasTalent(int index) {
        return this.talents[index] != null;
    }

    public void setTalent(int index, @Nullable ItemStack item) {
        this.talents[index] = (item == null ? null : item.clone());
    }

    @Override
    public String toString() {
        return this.uniqueId.toString();
    }
}
