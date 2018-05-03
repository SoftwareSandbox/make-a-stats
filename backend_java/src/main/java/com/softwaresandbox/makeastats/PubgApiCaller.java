package com.softwaresandbox.makeastats;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.stereotype.Component;

import static com.softwaresandbox.makeastats.util.PropertyFileReader.readPubgApiKey;

@Component
public class PubgApiCaller {

    private static final String BASE_URL = "https://api.playbattlegrounds.com/shards/pc-eu"; // Actual
    private static final String BASE_URL_STUB = "http://localhost:3333/pubg-stub"; // Stub
    private static final String PLAYERS_RESOURCE = "/players?filter[playerNames]=";
    private static final String MATCHES_RESOURCE = "/matches/";

    // Modify this depending on the requirement to use the stub or the actual api
    private static final boolean USE_STUB = true;

    public HttpResponse<String> getPlayer(String playerName) {
        sleepForDelay();
        try {
            return Unirest.get(getBaseUrl() + PLAYERS_RESOURCE + playerName)
                    .header("Authorization", "Bearer " + readPubgApiKey())
                    .header("Accept", "application/vnd.api+json")
                    .asString();
        } catch (UnirestException e) {
            throw new RuntimeException(e);
        }
    }

    public HttpResponse<String> getMatch(String matchId) {
        sleepForDelay();
        try {
            return Unirest.get(getBaseUrl() + MATCHES_RESOURCE + matchId)
                    .header("Authorization", "Bearer " + readPubgApiKey())
                    .header("Accept", "application/vnd.api+json")
                    .asString();
        } catch (UnirestException e) {
            throw new RuntimeException(e);
        }
    }

    private String getBaseUrl() {
        if (USE_STUB) {
            return BASE_URL_STUB;
        }
        return BASE_URL;
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
