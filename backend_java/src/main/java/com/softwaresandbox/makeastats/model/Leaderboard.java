package com.softwaresandbox.makeastats.model;

import java.util.Set;

public class Leaderboard {

    private Integer id;
    private String name;
    private Set<PlayerStats> playerStats;

    public Leaderboard(Integer id, String name, Set<PlayerStats> playerStats) {
        this.id = id;
        this.name = name;
        this.playerStats = playerStats;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<PlayerStats> getPlayerStats() {
        return playerStats;
    }
}
