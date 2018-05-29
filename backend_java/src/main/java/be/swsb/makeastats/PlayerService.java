package be.swsb.makeastats;

import com.mashape.unirest.http.HttpResponse;
import be.swsb.makeastats.mapper.MatchMapper;
import be.swsb.makeastats.mapper.PlayerMapper;
import be.swsb.makeastats.model.Match;
import be.swsb.makeastats.model.Player;
import be.swsb.makeastats.model.PlayerStats;
import com.softwaresandbox.pubgclient.PubgApiClient;
import com.softwaresandbox.pubgclient.PubgApiClientException;
import com.softwaresandbox.pubgclient.model.match.MatchResponse;
import com.softwaresandbox.pubgclient.model.match.participant.Participant;
import com.softwaresandbox.pubgclient.model.player.PlayerResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PlayerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlayerService.class);

    private final PubgApiCaller pubgApiCaller;
    private final PubgApiClient pubgApiClient;
    private final PlayerMapper playerMapper;
    private final MatchMapper matchMapper;

    private static final boolean USE_STUB = true;

    @Autowired
    public PlayerService(PubgApiCaller pubgApiCaller, PubgApiClient pubgApiClient, PlayerMapper playerMapper, MatchMapper matchMapper) {
        this.pubgApiCaller = pubgApiCaller;
        this.pubgApiClient = pubgApiClient;
        this.playerMapper = playerMapper;
        this.matchMapper = matchMapper;
    }

    //TODO return empty optional when player not found
    public PlayerStats getPlayerStats(String playerName) {
        try {
            String region = "pc-eu";
            PlayerResponse playerResponse = pubgApiClient.getPlayerByName(playerName, region)
                    .orElseThrow(() -> new RuntimeException("Player with name \"" + playerName + "\" not found"));
            com.softwaresandbox.pubgclient.model.player.Player player = playerResponse
                    .getPlayer();
            String playerId = player.getId();
            Set<Match> matches = player.getPlayerRelationships()
                    .getMatchIds()
                    .getData()
                    .stream()
                    .map(matchId -> {
                        sleepForDelay();
                        try {
                            return pubgApiClient.getMatch(matchId.getId(), region).orElse(null);
                        } catch (PubgApiClientException e) {
                            return null;
                        }
                    }).filter(Objects::nonNull)
                    .map(matchResponse -> {
                        Participant matchedParticipant = matchResponse.getParticipants().stream()
                                .filter(participant -> playerId.equals(participant.getId()))
                                .findFirst().orElse(null); //TODO make this cleaner
                        return new Match(matchResponse.getMatch().getId(), matchedParticipant.getParticipantAttributes().getParticipantStats().getKills());
                    }).collect(Collectors.toSet());
            LOGGER.info("Done retrieving stats for player=" + playerName);
            return new PlayerStats(playerName, matches);
        } catch (PubgApiClientException e) {
            throw new RuntimeException(e);
        }



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
            throw new RuntimeException("Something went wrong during the retrieval of the " + subject + " information [statusCode=" + response.getStatus() + "]\n"+response.getStatusText()+"\n"+response.getBody());
        }
    }

    private void sleepForDelay() {
        // TODO improve this to have a single thread which sends all the requests and is in charge of adding delays
        if (!USE_STUB) {
            try {
                Thread.sleep(6000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
