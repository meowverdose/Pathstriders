package cc.chocochip.seele.data;

import java.util.UUID;

public class PlayerData {

    private final UUID uniqueId;

    public PlayerData(UUID uniqueId) {
        this.uniqueId = uniqueId;
    }

    public UUID getUniqueId() {
        return this.uniqueId;
    }
}
