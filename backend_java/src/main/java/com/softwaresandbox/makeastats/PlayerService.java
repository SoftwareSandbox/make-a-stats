package com.softwaresandbox.makeastats;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.softwaresandbox.makeastats.mapper.PlayerMapper;
import com.softwaresandbox.makeastats.model.Player;
import org.springframework.stereotype.Service;

import static com.softwaresandbox.makeastats.util.PropertyFileReader.readPubgApiKey;

@Service
public class PlayerService {

    private static final String URL = "https://api.playbattlegrounds.com/shards/pc-eu/players?filter[playerNames]=";

    public Player getPlayer(String playerName) {
        try {
            HttpResponse<String> response = Unirest.get(URL + playerName)
                    .header("Authorization", "Bearer " + readPubgApiKey())
                    .header("Accept", "application/vnd.api+json")
                    .asString();
            return new PlayerMapper().map(playerName, response.getBody());
        } catch (UnirestException e) {
            throw new RuntimeException(e);
        }
    }

}
