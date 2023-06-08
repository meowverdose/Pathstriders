package cc.chocochip.seele.commands;

import cc.chocochip.seele.Seele;
import cc.chocochip.seele.data.PlayerData;
import cc.chocochip.seele.talents.Items;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
            player.sendMessage(ChatColor.RED + Items.values().toString());
            return false;
        }

        Items item = Items.valueOf(args[0].toUpperCase()); // TODO: 6/7/2023 Add proper checks later 
        PlayerData playerData = this.plugin.getHandler().getPlayerDataManager().get(player.getUniqueId());

        playerData.setTalent(0, item.getItem());

        player.getInventory().addItem(item.getItem());
        return true;
    }
}
