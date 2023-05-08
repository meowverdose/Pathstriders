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
    private File dataFolder;
    private Gson gson;

    @Override
    public void onEnable() {
        instance = this;

        init();

        handler = new ManagerHandler();
    }

    @Override
    public void onDisable() {
        handler.getTalentsManager().save();

        instance = null;
    }

    public void init() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        this.dataFolder = new File(getDataFolder(), "playerdata");

        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }

        registerListeners();
        registerCommands();

        // TODO
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
