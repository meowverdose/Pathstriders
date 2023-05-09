package cc.chocochip.seele.ability;

import cc.chocochip.seele.Seele;
import cc.chocochip.seele.data.PlayerData;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public abstract class Ability {

    private final AbilityType type;
    private final String name;
    private final long time;

    public Ability(AbilityType type, String name, long time) {
        this.type = type;
        this.name = name;
        this.time = time;
    }

    /**
     * Intended for multi-target use excluding caster
     * @param caster    Player casting ability
     */
    public void activate(Player caster) {
        PlayerData playerData = Seele.getInstance().getHandler().getPlayerDataManager().get(caster.getUniqueId());

        playerData.addAbility(this);
    }

    public AbilityType getType() {
        return this.type;
    }

    public String getName() {
        return this.name;
    }

    public long getTime() {
        return this.time;
    }

    public enum AbilityType {
        LIGHT_CONE,     // Active in talents; HSR series
        ARTIFACT,       // Active in talents; Genshin series
        ATTRIBUTE       // Single-use, active till death; World series
        // TODO
    }
}
