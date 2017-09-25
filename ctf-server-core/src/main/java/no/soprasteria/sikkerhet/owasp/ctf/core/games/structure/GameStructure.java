package no.soprasteria.sikkerhet.owasp.ctf.core.games.structure;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GameStructure {

    private static Logger logger = LoggerFactory.getLogger(GameStructure.class);

    public final String version = "1.0";
    public String beskrivelse = "";
    public String name = "";

    public List<FlagStructure> flags = new ArrayList<>();

    public static Optional<GameStructure> readJSON(InputStream inputStream) throws IOException {

        try {
            ObjectMapper mapper = new ObjectMapper();
            return Optional.of(mapper.readValue(inputStream, new TypeReference<GameStructure>() {}));
        } catch (IOException e) {
            logger.warn("Failed to load game structure.", e);
            return Optional.empty();
        }
    }
}
