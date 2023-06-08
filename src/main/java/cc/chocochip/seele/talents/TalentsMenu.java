package cc.chocochip.seele.talents;

import cc.chocochip.seele.data.PlayerData;
import cc.chocochip.seele.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

public class TalentsMenu implements Menu {

    private final PlayerData playerData;

    public TalentsMenu(PlayerData playerData) {
        this.playerData = playerData;
    }

    @Override
    public void onInventoryClick(InventoryClickEvent event) {
        switch (event.getSlot()) {
            case 10, 12, 14, 16 -> {
                System.out.println(event.getSlot());
            }
            default -> {
                event.setCancelled(true);
            }
        }
    }

    @Override
    public void onInventoryClose(InventoryCloseEvent event) {
        for (int i = 0; i < this.playerData.getTalents().length; ++i) {
            int slot = (i * 2) + 10;

            if (event.getInventory().getItem(slot) == null) return;

            ItemStack talent = event.getInventory().getItem(slot);

            System.out.println("DEBUG (TALENTSMENU): Updating talent slot" + i + " with menu slot" + slot + " -which is- " + talent); // TODO: 6/7/2023 DBUG

            playerData.setTalent(i, talent);
        }
    }

    @Override
    public void addContent(Inventory inventory) {
        /*for (int i = 0; i < inventory.getSize(); ++i) {
            inventory.setItem(i, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE, 1)
                            .setName(" ")
                            .addEnchant(Enchantment.DURABILITY, 1)
                            .addFlag(ItemFlag.HIDE_ENCHANTS)
                            .toItemStack()
            );
        }*/

        for (int i = 0; i < this.playerData.getTalents().length; ++i) {
            if (this.playerData.getTalents()[i] == null) return;

            int slot = (i * 2) + 10; // 10, 12, 14, 16
            ItemStack talent = this.playerData.getTalents()[i];
            inventory.setItem(slot, talent);
        }
    }

    @Override
    public Inventory getInventory() {
        Inventory inventory = Bukkit.createInventory(this, 27, "Talents");
        addContent(inventory);
        return inventory;
    }
}
