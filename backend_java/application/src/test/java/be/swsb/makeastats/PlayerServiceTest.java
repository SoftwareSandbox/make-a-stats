package be.swsb.makeastats;

import com.mashape.unirest.http.HttpResponse;
import be.swsb.makeastats.mapper.MatchMapper;
import be.swsb.makeastats.mapper.PlayerMapper;
import be.swsb.makeastats.model.Match;
import be.swsb.makeastats.model.Player;
import be.swsb.makeastats.model.PlayerStats;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class PlayerServiceTest extends AbstractUnitTest {

    private static final String PLAYER_RESPONSE = "{PLAYER_RESPONSE}";
    private static final String MATCH_RESPONSE = "{MATCH_RESPONSE}";
    private static final String PLAYER_NAME = "John";
    private static final String MATCH_ID = "1";
    private static final int KILLS = 4;

    @Mock
    private PubgApiCaller pubgApiCaller;
    @Mock
    private PlayerMapper playerMapper;
    @Mock
    private MatchMapper matchMapper;
    @Mock
    private HttpResponse<String> playerHttpResponse;
    @Mock
    private HttpResponse<String> matchHttpResponse;

    private Player player = new Player(PLAYER_NAME, singletonList(MATCH_ID));
    private Match match = new Match(MATCH_ID, KILLS);

    @InjectMocks
    private PlayerService playerService;

    @Test
    public void getPlayerStats_ExecutesPlayerAndMatchCallsAndReturnsStats() {
        when(pubgApiCaller.getPlayer(PLAYER_NAME)).thenReturn(playerHttpResponse);
        when(playerHttpResponse.getStatus()).thenReturn(200);
        when(playerHttpResponse.getBody()).thenReturn(PLAYER_RESPONSE);
        when(playerMapper.map(PLAYER_NAME, PLAYER_RESPONSE)).thenReturn(player);
        when(pubgApiCaller.getMatch(MATCH_ID)).thenReturn(matchHttpResponse);
        when(matchHttpResponse.getBody()).thenReturn(MATCH_RESPONSE);
        when(matchHttpResponse.getStatus()).thenReturn(200);
        when(matchMapper.map(PLAYER_NAME, MATCH_ID, MATCH_RESPONSE)).thenReturn(match);

        PlayerStats result = playerService.getPlayerStats(PLAYER_NAME);

        assertThat(result.getPlayer()).isEqualTo(PLAYER_NAME);
        assertThat(result.getTotalKills()).isEqualTo(KILLS);
    }

    @Test
    public void getPlayerStats_WhenPlayerResponseNot200_ThenThrowException() {
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("Something went wrong during the retrieval of the player information [statusCode=404]");

        when(pubgApiCaller.getPlayer(PLAYER_NAME)).thenReturn(playerHttpResponse);
        when(playerHttpResponse.getStatus()).thenReturn(404);

        playerService.getPlayerStats(PLAYER_NAME);
    }

    @Test
    public void getPlayerStats_WhenMatchResponseNot200_ThenThrowException() {
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("Something went wrong during the retrieval of the match information [statusCode=404]");

        when(pubgApiCaller.getPlayer(PLAYER_NAME)).thenReturn(playerHttpResponse);
        when(playerHttpResponse.getStatus()).thenReturn(200);
        when(playerHttpResponse.getBody()).thenReturn(PLAYER_RESPONSE);
        when(playerMapper.map(PLAYER_NAME, PLAYER_RESPONSE)).thenReturn(player);
        when(pubgApiCaller.getMatch(MATCH_ID)).thenReturn(matchHttpResponse);
        when(matchHttpResponse.getStatus()).thenReturn(404);

        playerService.getPlayerStats(PLAYER_NAME);
    }
}