package org.nekoverse.seele.commands;

import org.nekoverse.seele.Seele;
import org.nekoverse.seele.data.PlayerData;
import org.nekoverse.seele.utils.PlayerUtil;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class LookupCommand implements CommandExecutor {

    private final Seele plugin;

    public LookupCommand(Seele plugin) {
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
            player.sendMessage(ChatColor.RED + " **SYNTAX** /lookup <player>");
            return false;
        }

        OfflinePlayer target = PlayerUtil.getPlayerByName(args[0]);
        PlayerData playerData = this.plugin.getHandler().getPlayerDataManager().get(target.getUniqueId());

        player.sendMessage(
                "",
                ChatColor.GOLD + " " + ChatColor.BOLD + target.getName(),
                "",
                ChatColor.YELLOW + "  UUID: " + target.getUniqueId(),
                ChatColor.YELLOW + "  Abilities: " + Arrays.toString(playerData.getTalents()),
                ""
        );
        return true;
    }
}
