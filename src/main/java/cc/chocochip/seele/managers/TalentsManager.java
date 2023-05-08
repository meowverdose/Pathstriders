package cc.chocochip.seele.managers;

import cc.chocochip.seele.Seele;
import cc.chocochip.seele.manager.Manager;
import cc.chocochip.seele.manager.ManagerHandler;
import cc.chocochip.seele.menu.TalentsMenu;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TalentsManager extends Manager {

    private Map<UUID, TalentsMenu> talentsMap;
    private Gson gson;

    public TalentsManager(ManagerHandler handler) {
        super(handler);
        this.talentsMap = new HashMap<UUID, TalentsMenu>();
        load();
    }

    private void load() {
        gson = new GsonBuilder().setPrettyPrinting().create();

        try {
            FileReader reader = new FileReader(Seele.getInstance().getDataFile());

            this.talentsMap = gson.fromJson(reader, new HashMap<UUID, TalentsMenu>().getClass());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void save() {
        String json = gson.toJson(talentsMap);
        File data = Seele.getInstance().getDataFile();

        try {
            Files.write(data.toPath(), json.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.WRITE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
