package org.nekoverse.seele.commands;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.nekoverse.seele.Seele;
import org.nekoverse.seele.data.PlayerData;
import org.nekoverse.seele.utils.PlayerUtil;

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

        if (target == null) {
            sender.sendMessage(ChatColor.RED + " **ERROR** Invalid player");
            return false;
        }

        PlayerData playerData = this.plugin.getHandler().getPlayerDataManager().get(target.getUniqueId());

        player.sendMessage(
                "",
                ChatColor.GOLD + " " + ChatColor.BOLD + target.getName(),
                "",
                ChatColor.YELLOW + "  UUID: " + target.getUniqueId(),
                ChatColor.YELLOW + "  Talents: ",
                ChatColor.YELLOW + "   Slot 1: " + (playerData.hasTalent(0) ? playerData.getTalent(0).getItemMeta().getDisplayName() : ChatColor.GRAY + "Empty"),
                ChatColor.YELLOW + "   Slot 2: " + (playerData.hasTalent(1) ? playerData.getTalent(1).getItemMeta().getDisplayName()  : ChatColor.GRAY + "Empty"),
                ChatColor.YELLOW + "   Slot 3: " + (playerData.hasTalent(2) ? playerData.getTalent(2).getItemMeta().getDisplayName()  : ChatColor.GRAY + "Empty"),
                ""
        );
        return true;
    }
}
