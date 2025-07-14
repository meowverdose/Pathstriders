package com.meowverdose.pathstriders;

import com.meowverdose.pathstriders.commands.ItemsCommand;
import com.meowverdose.pathstriders.listeners.PlayerListener;
import com.meowverdose.pathstriders.managers.PlayerDataManager;
import com.meowverdose.pathstriders.runnables.TalentRunnable;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

public class Pathstriders extends JavaPlugin {

    private static Pathstriders instance;
    public static NamespacedKey TALENT_ID_KEY = null;
    private PlayerDataManager playerDataManager;

    @Override
    public void onEnable() {
        instance = this;
        init();
    }

    @Override
    public void onDisable() {
        instance = null;
    }

    private void init() {
        TALENT_ID_KEY = new NamespacedKey(this, "pathstriders_talent_key");

        registerManagers();
        registerListeners();
        registerRunnables();
        registerCommands();
    }

    private void registerManagers() {
        playerDataManager = new PlayerDataManager(this);
    }

    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
    }

    private void registerRunnables() {
        new TalentRunnable(this);
    }

    private void registerCommands() {
        getCommand("items").setExecutor(new ItemsCommand(this));
    }

    public static Pathstriders getInstance() {
        return instance;
    }

    public PlayerDataManager getPlayerDataManager() {
        return playerDataManager;
    }
}
