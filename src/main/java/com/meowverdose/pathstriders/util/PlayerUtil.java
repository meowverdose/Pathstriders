package com.meowverdose.pathstriders.util;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

public class PlayerUtil {

    public static List<Player> getPlayersWithinRadius(Player player, double radius) {
        World world = player.getWorld();
        Location center = player.getLocation();

        return world.getPlayers().stream()
                // .filter(p -> p != player) // exclude self prn
                .filter(p -> p.getLocation().distanceSquared(center) <= radius * radius)
                .collect(Collectors.toList());
    }
}
