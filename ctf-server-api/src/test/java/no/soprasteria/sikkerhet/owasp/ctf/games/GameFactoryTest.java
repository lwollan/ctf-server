package no.soprasteria.sikkerhet.owasp.ctf.games;

import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class GameFactoryTest {

    @Test
    public void something() throws IOException {
        List<Map<String, String>> mapList = GameFactory.les(GameFactoryTest.class.getResourceAsStream("/apps0404.json"));

        assertThat(mapList).isNotNull();
    }

}