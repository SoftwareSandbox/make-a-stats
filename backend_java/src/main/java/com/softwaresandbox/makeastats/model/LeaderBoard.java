package com.softwaresandbox.makeastats.model;

import java.util.Set;

public class LeaderBoard {

    private String id;
    private String name;
    private Set<PlayerStats> playerStats;

    public LeaderBoard(String id, String name, Set<PlayerStats> playerStats) {
        this.id = id;
        this.name = name;
        this.playerStats = playerStats;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<PlayerStats> getPlayerStats() {
        return playerStats;
    }
}
