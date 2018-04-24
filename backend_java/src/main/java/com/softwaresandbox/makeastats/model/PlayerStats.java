package com.softwaresandbox.makeastats.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Set;

public class PlayerStats {

    private final String player;
    private final int totalKills;
    private final int amountOfMatchesPlayed;
    private final BigDecimal killsPerMatch;

    public PlayerStats(String player, Set<Match> matches) {
        this.player = player;
        this.totalKills = calculateKills(matches);
        this.amountOfMatchesPlayed = matches.size();
        this.killsPerMatch = calculateKillsPerMatch(totalKills, amountOfMatchesPlayed);
    }

    private int calculateKills(Set<Match> matches) {
        return matches.stream()
                .mapToInt(Match::getKills)
                .sum();
    }

    private BigDecimal calculateKillsPerMatch(int totalKills, int amountOfMatches) {
        return new BigDecimal(Integer.toString(totalKills)).divide(new BigDecimal(Integer.toString(amountOfMatches)), 2, RoundingMode.UP);
    }

    public String getPlayer() {
        return player;
    }

    public int getTotalKills() {
        return totalKills;
    }

    public int getAmountOfMatchesPlayed() {
        return amountOfMatchesPlayed;
    }

    public BigDecimal getKillsPerMatch() {
        return killsPerMatch;
    }
}
