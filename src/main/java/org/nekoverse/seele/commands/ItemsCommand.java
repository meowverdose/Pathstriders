package org.nekoverse.seele.commands;

import org.bukkit.inventory.ItemStack;
import org.nekoverse.seele.Seele;
import org.nekoverse.seele.data.PlayerData;
import org.nekoverse.seele.talents.Items;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.nekoverse.seele.utils.EnumUtil;
import org.yaml.snakeyaml.util.EnumUtils;

import java.util.Arrays;

public class ItemsCommand implements CommandExecutor {

    private final Seele plugin;

    public ItemsCommand(Seele plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String prefix, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + " **ERROR** This command can only be ran by players");
            return false;
        }

        Player player = (Player) sender;

        if (!player.isOp()) {
            player.sendMessage(ChatColor.RED + " **ACCESS DENIED** Invalid permissions");
            return false;
        }

        if (args.length != 1) {
            player.sendMessage(ChatColor.YELLOW + " **ITEMS** " + Arrays.toString(Items.values()));
            return false;
        }

        if (EnumUtil.exists(Items.class, args[0]) == null) {
            player.sendMessage(ChatColor.RED + " **ERROR** No enum constant " + args[0].toUpperCase());
            return false;
        }

        Items item = Items.valueOf(args[0].toUpperCase());

        player.getInventory().addItem(item.getItem());
        return true;
    }
}
