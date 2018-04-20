package com.softwaresandbox.makeastats.mapper;

import com.jayway.jsonpath.JsonPath;
import com.softwaresandbox.makeastats.model.Player;

import java.util.List;

public class PlayerMapper {

    public Player map(String playerName, String json) {
        List<String> matchIds = JsonPath.read(json, "$.data[0].relationships.matches.data[*].id");
        return new Player(playerName, matchIds);
    }

}
