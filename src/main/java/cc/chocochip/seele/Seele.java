package cc.chocochip.seele;

import cc.chocochip.seele.commands.TalentsCommand;
import cc.chocochip.seele.listeners.PlayerListener;
import cc.chocochip.seele.manager.ManagerHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class Seele extends JavaPlugin {

    private static Seele instance;
    private static ManagerHandler handler;

    @Override
    public void onEnable() {
        instance = this;

        init();
    }

    @Override
    public void onDisable() {
        handler.getTalentsManager().save();

        instance = null;
    }

    private void init() {
        handler = new ManagerHandler();

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
        getCommand("talents").setExecutor(new TalentsCommand(this));
    }

    public static Seele getInstance() {
        return instance;
    }

    public static ManagerHandler getHandler() {
        return handler;
    }
}
