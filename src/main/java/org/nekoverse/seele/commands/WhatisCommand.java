package org.nekoverse.seele.commands;

import org.nekoverse.seele.Seele;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WhatisCommand implements CommandExecutor {

    private final Seele plugin;

    public WhatisCommand(Seele plugin) {
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

        player.sendMessage(player.getInventory().getItemInMainHand().toString().replaceAll(",", "\n"));
        return true;
    }
}
