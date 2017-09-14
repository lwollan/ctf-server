package no.soprasteria.sikkerhet.owasp.ctf.core.games.structure;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class GameStructure {

    public final String version = "1.0";
    public String beskrivelse = "";
    public String name = "";

    public List<FlagStructure> flags = new ArrayList<>();

    public static GameStructure readJSON(InputStream inputStream) throws IOException {

        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(inputStream, new TypeReference<GameStructure>() {});
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
