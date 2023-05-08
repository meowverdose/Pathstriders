package cc.chocochip.seele.manager;

import cc.chocochip.seele.managers.PlayerDataManager;
import cc.chocochip.seele.managers.TalentsManager;

public class ManagerHandler {

    private final PlayerDataManager playerDataManager;
    private final TalentsManager talentsManager;

    public ManagerHandler() {
        this.playerDataManager = new PlayerDataManager(this);
        this.talentsManager = new TalentsManager(this);
    }

    public PlayerDataManager getPlayerDataManager() {
        return this.playerDataManager;
    }

    public TalentsManager getTalentsManager() {
        return this.talentsManager;
    }
}
