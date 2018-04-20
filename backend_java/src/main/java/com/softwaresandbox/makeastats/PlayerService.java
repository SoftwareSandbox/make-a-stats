package com.softwaresandbox.makeastats;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.softwaresandbox.makeastats.mapper.MatchMapper;
import com.softwaresandbox.makeastats.mapper.PlayerMapper;
import com.softwaresandbox.makeastats.model.Match;
import com.softwaresandbox.makeastats.model.Player;
import com.softwaresandbox.makeastats.model.PlayerStats;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

import static com.softwaresandbox.makeastats.util.PropertyFileReader.readPubgApiKey;

@Service
public class PlayerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlayerService.class);

    private static final String BASE_URL = "https://api.playbattlegrounds.com/shards/pc-eu";
    private static final String PLAYERS_URL = BASE_URL + "/players?filter[playerNames]=";
    private static final String MATCHES_URL = BASE_URL + "/matches/";

    public PlayerStats getPlayerStats(String playerName) {
        Player player = getPlayer(playerName);
        Set<Match> matches = new HashSet<>();
        player.getMatchIds().forEach(matchId -> matches.add(getMatch(playerName, matchId)));
        return new PlayerStats(player, matches);
    }

    private Player getPlayer(String playerName) {
        sleepForDelay();
        try {
            LOGGER.info("Requesting player info player=" + playerName);
            HttpResponse<String> response = Unirest.get(PLAYERS_URL + playerName)
                    .header("Authorization", "Bearer " + readPubgApiKey())
                    .header("Accept", "application/vnd.api+json")
                    .asString();
            return new PlayerMapper().map(playerName, response.getBody());
        } catch (UnirestException e) {
            throw new RuntimeException(e);
        }
    }

    private Match getMatch(String playerName, String matchId) {
        sleepForDelay();
        try {
            LOGGER.info("Requesting match info player=" + playerName + " match=" + matchId);
            HttpResponse<String> response = Unirest.get(MATCHES_URL + matchId)
                    .header("Authorization", "Bearer " + readPubgApiKey())
                    .header("Accept", "application/vnd.api+json")
                    .asString();
            return new MatchMapper().map(playerName, matchId, response.getBody());
        } catch (UnirestException e) {
            throw new RuntimeException(e);
        }
    }

    private void sleepForDelay() {
        // TODO improve this to have a single thread which sends all the requests and is in charge of adding delays
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
