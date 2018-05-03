package be.swsb.makeastats.model;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;

public class PlayerStatsTest {

    @Test
    public void constructor_when1MatchWith4Kills_thenTotalKills4() {
        HashSet<Match> matches = new HashSet<>();
        matches.add(new Match("a", 4));

        PlayerStats result = new PlayerStats("John", matches);

        assertThat(result.getTotalKills()).isEqualTo(4);
    }

    @Test
    public void constructor_when2MatchesWithRespectively2And3Kills_thenTotalKills5() {
        HashSet<Match> matches = new HashSet<>();
        matches.add(new Match("a", 2));
        matches.add(new Match("b", 3));

        PlayerStats result = new PlayerStats("John", matches);

        assertThat(result.getTotalKills()).isEqualTo(5);
    }

    @Test
    public void constructor_when1MatchWith1Kill_thenKillsPerMatch1() {
        HashSet<Match> matches = new HashSet<>();
        matches.add(new Match("a", 1));

        PlayerStats result = new PlayerStats("John", matches);

        assertThat(result.getKillsPerMatch()).isEqualTo(new BigDecimal("1.00"));
    }

    @Test
    public void constructor_when2MatchesWithATotalOf4Kills_thenKillsPerMatch2() {
        HashSet<Match> matches = new HashSet<>();
        matches.add(new Match("a", 2));
        matches.add(new Match("b", 2));

        PlayerStats result = new PlayerStats("John", matches);

        assertThat(result.getKillsPerMatch()).isEqualTo(new BigDecimal("2.00"));
    }

    @Test
    public void constructor_when3MatchesWithATotalOf10Kills_thenKillsPerMatch3_34() {
        HashSet<Match> matches = new HashSet<>();
        matches.add(new Match("a", 2));
        matches.add(new Match("b", 3));
        matches.add(new Match("c", 5));

        PlayerStats result = new PlayerStats("John", matches);

        assertThat(result.getKillsPerMatch()).isEqualTo(new BigDecimal("3.34"));
    }


}