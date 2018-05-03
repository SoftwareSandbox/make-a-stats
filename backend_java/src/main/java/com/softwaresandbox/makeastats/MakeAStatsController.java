package com.softwaresandbox.makeastats;

import com.softwaresandbox.makeastats.model.Leaderboard;
import com.softwaresandbox.makeastats.model.PlayerStats;
import com.softwaresandbox.makeastats.to.CreateLeaderboardTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static java.util.stream.Collectors.toSet;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api")
public class MakeAStatsController {

    private static final String EXPOSE_HEADER = "access-control-expose-headers";

    private final PlayerService playerService;
    private final Map<Integer, CreateLeaderboardTO> leaderboardMap = new HashMap<>();

    @Autowired
    public MakeAStatsController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @PostMapping(value = "/leaderboard")
    public ResponseEntity createLeaderBoard(@RequestBody CreateLeaderboardTO leaderboard,
                                            UriComponentsBuilder uriBuilder) {
        int leaderboardId = leaderboardMap.size();
        leaderboardMap.put(leaderboardId, leaderboard);

        URI location = uriBuilder.path("leaderboard/{id}")
                .buildAndExpand(leaderboardId)
                .toUri();

        return created(location)
                .header(EXPOSE_HEADER, "Location")
                .build();
    }

    @GetMapping(value = "/leaderboard/{id}", produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity getLeaderBoard(@PathVariable("id") Integer leaderBoardId) {
        CreateLeaderboardTO leaderboard = leaderboardMap.get(leaderBoardId);
        Set<PlayerStats> playerStats = leaderboard.getPlayerNames().stream()
                .map(playerService::getPlayerStats)
                .collect(toSet());
        return ok().body(new Leaderboard(leaderBoardId, leaderboard.getName(), playerStats));
    }

    @GetMapping(value = "/player/{name}", produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity getPlayer(@PathVariable("name") String playerName) {
        return ok().body(playerService.getPlayerStats(playerName));
    }
}
