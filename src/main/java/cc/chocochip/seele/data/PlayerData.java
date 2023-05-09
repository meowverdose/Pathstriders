package cc.chocochip.seele.data;

import cc.chocochip.seele.ability.Ability;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlayerData {

    private final UUID uniqueId;
    private double critRate;
    private final List<Ability> activeAbilities;

    public PlayerData(UUID uniqueId) {
        this.uniqueId = uniqueId;
        this.critRate = 0.25;
        this.activeAbilities = new ArrayList<Ability>();
    }

    public UUID getUniqueId() {
        return this.uniqueId;
    }

    public double getCritRate() {
        return this.critRate;
    }

    public void addAbility(Ability ability) {
        this.activeAbilities.add(ability);
    }
}
