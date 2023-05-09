package cc.chocochip.seele.manager;

import cc.chocochip.seele.managers.PlayerDataManager;

public class ManagerHandler {

    private final PlayerDataManager playerDataManager;

    public ManagerHandler() {
        this.playerDataManager = new PlayerDataManager(this);
    }

    public PlayerDataManager getPlayerDataManager() {
        return this.playerDataManager;
    }
}
