package cc.chocochip.seele.manager;

import cc.chocochip.seele.managers.TalentsManager;

public class ManagerHandler {
    private final TalentsManager talentsManager;

    public ManagerHandler() {
        this.talentsManager = new TalentsManager(this);
    }

    public TalentsManager getTalentsManager() {
        return this.talentsManager;
    }
}
