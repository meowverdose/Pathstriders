package cc.chocochip.seele.commands;

import cc.chocochip.seele.Seele;
import cc.chocochip.seele.data.PlayerData;
import cc.chocochip.seele.talents.TalentsMenu;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
        player.openInventory(new TalentsMenu(playerData).getInventory());
        return true;
    }
}
