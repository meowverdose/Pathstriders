package com.meowverdose.pathstriders.commands;

import com.meowverdose.pathstriders.Pathstriders;
import com.meowverdose.pathstriders.talents.TalentType;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ItemsCommand implements CommandExecutor, TabCompleter {

    private final Pathstriders plugin;

    public ItemsCommand(Pathstriders plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length < 1) {
            sender.sendMessage("§cUsage: /items <talent-id> <player>");
            return true;
        }

        String talentId = args[0].toLowerCase();
        TalentType talent = plugin.getTalentManager().getTalentById(talentId);

        if (talent == null) {
            sender.sendMessage("§cTalent not found: " + talentId);
            return true;
        }

        Player target;
        if (args.length >= 2) {
            target = Bukkit.getPlayerExact(args[1]);
            if (target == null) {
                sender.sendMessage("§cPlayer not found: " + args[1]);
                return true;
            }
        } else {
            if (!(sender instanceof Player player)) {
                sender.sendMessage("§cConsole must specify a player.");
                return true;
            }
            target = player;
        }

        target.getInventory().addItem(talent.createItem(Pathstriders.TALENT_ID_KEY));
        sender.sendMessage("§aGave " + target.getName() + " the talent: " + talentId);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            for (TalentType talent : plugin.getTalentManager().getAllTalents()) {
                if (talent.getId().startsWith(args[0].toLowerCase())) {
                    completions.add(talent.getId());
                }
            }
        }

        if (args.length == 2) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (player.getName().toLowerCase().startsWith(args[1].toLowerCase())) {
                    completions.add(player.getName());
                }
            }
        }

        return completions;
    }
}
