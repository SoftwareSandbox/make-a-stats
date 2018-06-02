package be.swsb.makeastats.mapper;

import com.jayway.jsonpath.JsonPath;
import be.swsb.makeastats.model.Match;
import net.minidev.json.JSONArray;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class MatchMapperTest {

    @Test
    @SuppressWarnings("ConstantConditions")
    public void map_extractsKillsFromJson() throws IOException {
        String json = ((JSONArray) JsonPath.read(new File(this.getClass().getClassLoader().getResource("example-match-response.json").getFile()), "*")).toJSONString();

        Match result = new MatchMapper().map("Jooones", "a", json);

        assertThat(result.getKills()).isEqualTo(1);
    }

}