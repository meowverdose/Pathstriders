package com.meowverdose.pathstriders;

import org.bukkit.plugin.java.JavaPlugin;

public class Pathstriders extends JavaPlugin {

    @Override
    public void onEnable() {
        init();
    }

    @Override
    public void onDisable() {

    }

    private void init() {
        registerManagers();
        registerListeners();
        registerCommands();
    }

    private void registerManagers() {

    }

    private void registerListeners() {

    }

    private void registerCommands() {

    }
}
