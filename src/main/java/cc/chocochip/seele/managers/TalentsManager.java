package cc.chocochip.seele.managers;

import cc.chocochip.seele.manager.Manager;
import cc.chocochip.seele.manager.ManagerHandler;
import cc.chocochip.seele.menu.TalentsMenu;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TalentsManager extends Manager {

    private Map<UUID, TalentsMenu> talentsMap;

    public TalentsManager(ManagerHandler handler) {
        super(handler);
        this.talentsMap = new HashMap<UUID, TalentsMenu>();
        load();
    }

    private void load() {

    }

    public void save() {

    }

    public TalentsMenu get(UUID uuid) {
        return this.talentsMap.getOrDefault(uuid, null);
    }

    public boolean contains(UUID uuid) {
        return this.talentsMap.containsKey(uuid);
    }

    public void put(UUID uuid, TalentsMenu talents) {
        this.talentsMap.put(uuid, talents);
    }
}
