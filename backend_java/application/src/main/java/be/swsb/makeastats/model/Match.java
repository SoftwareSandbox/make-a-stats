package be.swsb.makeastats.model;

public class Match {

    private final String matchId;
    private final int kills;

    public Match(String matchId, int kills) {
        this.matchId = matchId;
        this.kills = kills;
    }

    public String getMatchId() {
        return matchId;
    }

    public int getKills() {
        return kills;
    }
}
