package org.nekoverse.seele.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.nekoverse.seele.Seele;
import org.nekoverse.seele.data.PlayerData;
import org.nekoverse.seele.talents.TalentsMenu;

public class TalentsCommand implements CommandExecutor {

    private final Seele plugin;

    public TalentsCommand(Seele plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String prefix, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + " **ERROR** This command can only be ran by players");
            return false;
        }

        Player player = (Player) sender;
        PlayerData playerData = this.plugin.getHandler().getPlayerDataManager().get(player.getUniqueId());
        player.openInventory(new TalentsMenu(playerData).create());
        return true;
    }
}
