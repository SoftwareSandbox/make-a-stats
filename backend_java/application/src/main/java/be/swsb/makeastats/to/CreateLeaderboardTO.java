package be.swsb.makeastats.to;

import java.util.Set;

public class CreateLeaderboardTO {

    private String name;
    private Set<String> playerNames;

    private CreateLeaderboardTO() {
        // necessary for jackson
    }

    public String getName() {
        return name;
    }

    public Set<String> getPlayerNames() {
        return playerNames;
    }
}
