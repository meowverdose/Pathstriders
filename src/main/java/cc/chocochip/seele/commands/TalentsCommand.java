package cc.chocochip.seele.commands;

import cc.chocochip.seele.Seele;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.logging.Level;

public class TalentsCommand implements CommandExecutor {

    private final Seele plugin;

    public TalentsCommand(Seele plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String prefix, String[] args) {
        if (!(sender instanceof Player)) {
            this.plugin.getLogger().log(Level.WARNING, "Player only command");
            return false;
        }

        Player player = (Player) sender;


        if (args.length != 0) {
            player.sendMessage(ChatColor.RED + "Syntax error: /talents");
            return false;
        }

        // TODO: Open talents menu
        return true;
    }
}
