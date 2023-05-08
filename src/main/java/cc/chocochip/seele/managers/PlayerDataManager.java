package cc.chocochip.seele.managers;

import cc.chocochip.seele.Seele;
import cc.chocochip.seele.data.PlayerData;
import cc.chocochip.seele.manager.Manager;
import cc.chocochip.seele.manager.ManagerHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerDataManager extends Manager {

    private Map<UUID, PlayerData> playerDataMap;

    public PlayerDataManager(ManagerHandler handler) {
        super(handler);
        this.playerDataMap = new HashMap<UUID, PlayerData>();
    }

    public void load() {

    }

    public void save() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        for (UUID uniqueIds : playerDataMap.keySet()) {
            String json = gson.toJson(get(uniqueIds));

            try (FileWriter writer = new FileWriter(getPlayerDataFile(uniqueIds))){
                writer.write(json);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void add(Player player) {
        this.playerDataMap.put(player.getUniqueId(), new PlayerData(player.getUniqueId()));
    }

    public PlayerData get(Player player) {
        return playerDataMap.get(player.getUniqueId());
    }

    public PlayerData get(UUID uniqueId) {
        return playerDataMap.get(uniqueId);
    }

    public File getPlayerDataFile(UUID uniqueId) {
        return new File(Seele.getInstance().getDataFolder(), uniqueId.toString() + ".json");
    }
}
