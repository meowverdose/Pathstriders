package org.nekoverse.seele.utils;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

public class PlayerUtil {

    public static OfflinePlayer getPlayerByName(String name) { // TODO Test
        if (name == null || name.isBlank() || name.isEmpty()) {
            return null;
        }

        for (OfflinePlayer player : Bukkit.getOfflinePlayers()) {
            if (player != null) {
                if (player.getName().equalsIgnoreCase(name)) {
                    return player;
                }
            }
        }
        return null;
    }
}
