package com.softwaresandbox.makeastats;

import com.softwaresandbox.makeastats.model.LeaderBoard;
import com.softwaresandbox.makeastats.model.PlayerStats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

import static java.util.stream.Collectors.toSet;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api")
public class MakeAStatsController {

    private final PlayerService playerService;

    @Autowired
    public MakeAStatsController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping(value = "/leaderboard/{id}", produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity getLeaderBoard(@PathVariable("id") String leaderBoardId) {
        Set<PlayerStats> playerStats = getPlayersForLeaderBoard(leaderBoardId).stream()
                .map(playerService::getPlayerStats)
                .collect(toSet());
        return ok().body(new LeaderBoard(leaderBoardId, "Team th3 suck", playerStats));
    }

    @GetMapping(value = "/player/{name}", produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity getPlayer(@PathVariable("name") String playerName) {
        return ok().body(playerService.getPlayerStats(playerName));
    }

    private Set<String> getPlayersForLeaderBoard(@PathVariable("id") String leaderBoardId) {
        // Hardcoded for now
        if (!"1".equals(leaderBoardId)) {
            throw new IllegalArgumentException("No leaderboard found for id=" + leaderBoardId);
        }
        Set<String> playerNames = new HashSet<>();
        playerNames.add("Jooones");
        playerNames.add("Sch3lp");
        playerNames.add("Daxude");
        playerNames.add("hahawin");
        return playerNames;
    }

}
