package com.meowverdose.pathstriders;

import com.meowverdose.pathstriders.commands.ItemsCommand;
import com.meowverdose.pathstriders.listeners.PlayerListener;
import com.meowverdose.pathstriders.managers.PlayerDataManager;
import com.meowverdose.pathstriders.managers.TalentManager;
import com.meowverdose.pathstriders.runnables.TalentRunnable;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

public class Pathstriders extends JavaPlugin {

    public static NamespacedKey TALENT_ID_KEY = null;
    private TalentManager talentManager;
    private PlayerDataManager playerDataManager;

    @Override
    public void onEnable() {
        init();
    }

    @Override
    public void onDisable() {

    }

    private void init() {
        TALENT_ID_KEY = new NamespacedKey(this, "pathstriders_talent_key");

        registerManagers();
        registerListeners();
        registerRunnables();
        registerCommands();
    }

    private void registerManagers() {
        talentManager = new TalentManager(this);
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

    public TalentManager getTalentManager() {
        return talentManager;
    }

    public PlayerDataManager getPlayerDataManager() {
        return playerDataManager;
    }
}
