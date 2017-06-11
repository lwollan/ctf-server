package no.soprasteria.sikkerhet.owasp.ctf.games;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import no.soprasteria.sikkerhet.owasp.ctf.service.FlagService;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import static no.soprasteria.sikkerhet.owasp.ctf.games.JSONGameConfig.GameKeys.*;

public abstract class JSONGameConfig {

    enum GameKeys {
        flagName, svar, poeng, tips, beskrivelse
    }

    static List<Map<GameKeys, String>> readJSON(InputStream inputStream) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        return mapper.readValue(inputStream, new TypeReference<List<Map<GameKeys, String>>>() {});
    }

    public void setupGame(FlagService flagService, String resourceFile) throws IOException {
        List<Map<GameKeys, String>> gameFile = readJSON(this.getClass().getResourceAsStream(resourceFile));

        gameFile.stream().forEach(m -> {
            flagService.addFlag(m.get(flagName),
                    m.get(svar),
                    Long.parseLong(m.get(poeng)),
                    m.get(tips),
                    m.get(beskrivelse));
        });
    }

}
