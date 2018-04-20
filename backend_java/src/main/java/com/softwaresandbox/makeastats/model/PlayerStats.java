package com.softwaresandbox.makeastats.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Set;

public class PlayerStats {

    private final Player player;
    private final int totalKills;
    private final int amountOfMatches;
    private final BigDecimal killsPerMatch;

    public PlayerStats(Player player, Set<Match> matches) {
        this.player = player;
        this.totalKills = calculateKills(matches);
        this.amountOfMatches = matches.size();
        this.killsPerMatch = calculateKillsPerMatch(totalKills, amountOfMatches);
    }

    private int calculateKills(Set<Match> matches) {
        return matches.stream()
                .mapToInt(Match::getKills)
                .sum();
    }

    private BigDecimal calculateKillsPerMatch(int totalKills, int amountOfMatches) {
        return new BigDecimal(Integer.toString(totalKills)).divide(new BigDecimal(Integer.toString(amountOfMatches)), 2, RoundingMode.UP);
    }

    public Player getPlayer() {
        return player;
    }

    public int getTotalKills() {
        return totalKills;
    }

    public int getAmountOfMatches() {
        return amountOfMatches;
    }

    public BigDecimal getKillsPerMatch() {
        return killsPerMatch;
    }
}
