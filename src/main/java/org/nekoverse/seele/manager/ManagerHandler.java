package org.nekoverse.seele.manager;

import org.nekoverse.seele.managers.PlayerDataManager;

public class ManagerHandler {

    private final PlayerDataManager playerDataManager;

    public ManagerHandler() {
        this.playerDataManager = new PlayerDataManager(this);
    }

    public PlayerDataManager getPlayerDataManager() {
        return this.playerDataManager;
    }
}
