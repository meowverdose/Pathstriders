package org.nekoverse.seele.managers;

import org.nekoverse.seele.Seele;
import org.nekoverse.seele.data.PlayerData;
import org.nekoverse.seele.manager.Manager;
import org.nekoverse.seele.manager.ManagerHandler;
import org.nekoverse.seele.talents.ItemStackAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.FileReader;
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
        load();
    }

    public void load() {
        File folder = new File(Seele.getInstance().getDataFolder(), "playerdata");

        if (!folder.exists()) {
            folder.mkdirs();
        }

        for (OfflinePlayer offlinePlayer : Bukkit.getOfflinePlayers()) {
            loadPlayerData(offlinePlayer.getUniqueId());
        }
    }

    public void loadPlayerData(UUID uniqueId) {
        File dataFile = new File(Seele.getInstance().getDataFolder() + "/playerdata/" + uniqueId.toString() + ".json");

        if (!dataFile.exists()) {
            playerDataMap.put(uniqueId, new PlayerData(uniqueId));
            return;
        }

        try {
            FileReader reader = new FileReader(dataFile);
            Gson gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .registerTypeAdapter(ItemStack.class, new ItemStackAdapter())
                    .create();

            PlayerData playerData = gson.fromJson(reader, PlayerData.class);
            playerDataMap.put(uniqueId, playerData);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void save() {
        for (UUID uniqueId : playerDataMap.keySet()) {
            savePlayerData(uniqueId);
        }
    }

    public void savePlayerData(UUID uniqueId) {
        File dataFile = new File(Seele.getInstance().getDataFolder() + "/playerdata/" + uniqueId.toString() + ".json");

        try {
            FileWriter writer = new FileWriter(dataFile);
            Gson gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .registerTypeAdapter(ItemStack.class, new ItemStackAdapter())
                    .create();

            gson.toJson(playerDataMap.get(uniqueId), writer);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public PlayerData get(UUID uniqueId) {
        return playerDataMap.get(uniqueId);
    }

    public boolean contains(UUID uniqueId) {
        return get(uniqueId) != null;
    }
}
