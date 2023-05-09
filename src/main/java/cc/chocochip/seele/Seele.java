package cc.chocochip.seele;

import cc.chocochip.seele.commands.ItemsCommand;
import cc.chocochip.seele.commands.LookupCommand;
import cc.chocochip.seele.listeners.PlayerListener;
import cc.chocochip.seele.manager.ManagerHandler;
import org.bukkit.plugin.java.JavaPlugin;

public class Seele extends JavaPlugin {

    private static Seele instance;
    private ManagerHandler handler;

    @Override
    public void onEnable() {
        instance = this;

        init();
    }

    @Override
    public void onDisable() {
        getHandler().getPlayerDataManager().save();

        instance = null;
    }

    private void init() {
        this.handler = new ManagerHandler();

        registerListeners();
        registerCommands();

        // TODO
    }

    private void save() {
        getHandler().getPlayerDataManager().save();
    }

    public void registerListeners() {
        getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
    }

    public void registerCommands() {
        getCommand("lookup").setExecutor(new LookupCommand(this));
        getCommand("items").setExecutor(new ItemsCommand(this));
    }

    public static Seele getInstance() {
        return instance;
    }

    public ManagerHandler getHandler() {
        return this.handler;
    }
}
