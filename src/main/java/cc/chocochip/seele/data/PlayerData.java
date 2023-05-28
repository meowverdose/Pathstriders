package cc.chocochip.seele.data;

import cc.chocochip.seele.ability.Items;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class PlayerData {

    private final UUID uniqueId;
    private ItemStack talents;
    //private final Ability[] abilities = new Ability[3];;

    public PlayerData(UUID uniqueId) {
        this.uniqueId = uniqueId;
        //this.talents = new InventoryGUI(Bukkit.createInventory(null, 9, "Talents"));
        //this.talents = new ItemStack[3];
        this.talents = Items.IN_THE_NIGHT.getItem();
        //this.abilities[0] = new InTheNight(Ability.AbilityType.LIGHT_CONE, "In the Night");
    }

    public UUID getUniqueId() {
        return this.uniqueId;
    }

    /*public ItemStack[] getTalents() {
        return this.talents;
    }*/

    public ItemStack getTalents() {
        return talents;
    }
}
