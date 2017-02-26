package no.soprasteria.sikkerhet.owasp.ctf.games;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class GameFactory {

    private GameFactory() {

    }

    public static List<Map<String, String>> les(InputStream inputStream) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        return mapper.readValue(inputStream, List.class);
    }
}
