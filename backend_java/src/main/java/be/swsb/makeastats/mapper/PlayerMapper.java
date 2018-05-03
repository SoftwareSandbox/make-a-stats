package be.swsb.makeastats.mapper;

import be.swsb.makeastats.model.Player;
import com.jayway.jsonpath.JsonPath;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PlayerMapper {

    public Player map(String playerName, String json) {
        List<String> matchIds = JsonPath.read(json, "$.data[0].relationships.matches.data[*].id");
        return new Player(playerName, matchIds);
    }

}
