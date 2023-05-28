package cc.chocochip.seele.ability;

import cc.chocochip.seele.Seele;
import cc.chocochip.seele.data.PlayerData;
import org.bukkit.entity.Player;

public abstract class Ability {

    private final AbilityType type;
    private final String name;

    public Ability(AbilityType type, String name) {
        this.type = type;
        this.name = name;
    }

    /**
     * Intended for multi-target use excluding caster
     * @param caster    Player casting ability
     */
    public void activate(Player caster) {
        PlayerData playerData = Seele.getInstance().getHandler().getPlayerDataManager().get(caster.getUniqueId());
    }

    public AbilityType getType() {
        return this.type;
    }

    public String getName() {
        return this.name;
    }

    public enum AbilityType {
        LIGHT_CONE,     // Active in talents; HSR series
        ARTIFACT,       // Active in talents; Genshin series
        ATTRIBUTE       // Single-use, active till death; World series
        // TODO
    }
}
