package com.meowverdose.pathstriders.talents;

import org.bukkit.entity.Player;

import java.util.function.Consumer;

public enum TalentEffect {

    THE_FOOLS_WORLD(
            player -> { // Effect
                player.sendMessage("§cTalents: You have been silenced and cannot use talents for 30s");
            },
            30_000L
    );

    private final Consumer<Player> effect;
    private final long durationMillis;

    TalentEffect(
            Consumer<Player> effect, long durationMillis
    ) {
        this.effect = effect;
        this.durationMillis = durationMillis;
    }

    public long getDurationMillis() {
        return durationMillis;
    }

    public void apply(Player player) {
        effect.accept(player);
    }
}
