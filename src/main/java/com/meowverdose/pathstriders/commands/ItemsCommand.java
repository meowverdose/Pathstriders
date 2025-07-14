package com.meowverdose.pathstriders.commands;

import com.meowverdose.pathstriders.Pathstriders;
import com.meowverdose.pathstriders.talents.Talent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ItemsCommand implements CommandExecutor, TabCompleter {

    private final Pathstriders plugin;

    public ItemsCommand(Pathstriders plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 2) {
            sender.sendMessage("§cUsage: /items <player> <talent>");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null || !target.isOnline()) {
            sender.sendMessage("§cTalents Player not found or not online!");
            return true;
        }

        Talent talent = null;
        for (Talent talents : Talent.values()) {
            if (talents.name().equalsIgnoreCase(args[1])) {
                talent = talents;
                break;
            }
        }

        if (talent == null) {
            sender.sendMessage("§cTalents: Talent not found!");
            return true;
        }

        target.getInventory().addItem(talent.getItem());
        target.sendMessage("§aTalents: You received the " + talent.name() + " talent!");
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            completions = Bukkit.getOnlinePlayers().stream()
                    .map(Player::getName)
                    .filter(name -> name.toLowerCase().startsWith(args[0].toLowerCase()))
                    .collect(Collectors.toList());
        } else if (args.length == 2) {
            completions = new ArrayList<>();
            for (Talent talent : Talent.values()) {
                String name = talent.name();
                if (name.toLowerCase().startsWith(args[1].toLowerCase())) {
                    completions.add(name);
                }
            }
        }
        return completions;
    }
}
