package be.swsb.makeastats;

import com.mashape.unirest.http.HttpResponse;
import be.swsb.makeastats.mapper.MatchMapper;
import be.swsb.makeastats.mapper.PlayerMapper;
import be.swsb.makeastats.model.Match;
import be.swsb.makeastats.model.Player;
import be.swsb.makeastats.model.PlayerStats;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class PlayerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlayerService.class);

    private final PubgApiCaller pubgApiCaller;
    private final PlayerMapper playerMapper;
    private final MatchMapper matchMapper;

    @Autowired
    public PlayerService(PubgApiCaller pubgApiCaller, PlayerMapper playerMapper, MatchMapper matchMapper) {
        this.pubgApiCaller = pubgApiCaller;
        this.playerMapper = playerMapper;
        this.matchMapper = matchMapper;
    }

    public PlayerStats getPlayerStats(String playerName) {
        Player player = getPlayer(playerName);
        Set<Match> matches = new HashSet<>();
        player.getMatchIds().forEach(matchId -> matches.add(getMatch(playerName, matchId)));
        LOGGER.info("Done retrieving stats for player=" + player.getPlayerName());
        return new PlayerStats(player.getPlayerName(), matches);
    }

    private Player getPlayer(String playerName) {
        LOGGER.info("Requesting player info player=" + playerName);
        HttpResponse<String> response = pubgApiCaller.getPlayer(playerName);
        validateSuccessfulResponse(response, "player");
        Player player = playerMapper.map(playerName, response.getBody());
        LOGGER.info("Matches found for player=" + player.getPlayerName() + " -> " + player.getMatchIds().size());
        return player;
    }

    private Match getMatch(String playerName, String matchId) {
        LOGGER.info("Requesting match info player=" + playerName + " match=" + matchId);
        HttpResponse<String> response = pubgApiCaller.getMatch(matchId);
        validateSuccessfulResponse(response, "match");
        return matchMapper.map(playerName, matchId, response.getBody());
    }

    private void validateSuccessfulResponse(HttpResponse<String> response, String subject) {
        if (response.getStatus() != 200) {
            throw new RuntimeException("Something went wrong during the retrieval of the " + subject + " information [statusCode=" + response.getStatus() + "]");
        }
    }

}
