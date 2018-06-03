package be.swsb.makeastats.model;

import java.util.List;

public class Player {

    private final String playerName;
    private final List<String> matchIds;

    public Player(String playerName, List<String> matchIds) {
        this.playerName = playerName;
        this.matchIds = matchIds;
    }

    public String getPlayerName() {
        return playerName;
    }

    public List<String> getMatchIds() {
        return matchIds;
    }
}
