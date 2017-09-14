package no.soprasteria.sikkerhet.owasp.ctf.core.games;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import no.soprasteria.sikkerhet.owasp.ctf.core.games.structure.GameStructure;
import no.soprasteria.sikkerhet.owasp.ctf.core.service.FlagService;

import java.io.IOException;
import java.io.InputStream;

public abstract class JSONGameConfig {

    protected JSONGameConfig(FlagService flagService, String resourceFile) throws IOException {
        GameStructure gameStructure = readJSON(this.getClass().getResourceAsStream(resourceFile));

        gameStructure.flags.stream().forEach(m -> {
            flagService.addFlag(m.flagName,
                    m.flagName,
                    Long.parseLong(m.poeng),
                    m.tips,
                    m.beskrivelse);
        });
    }

    static GameStructure readJSON(InputStream inputStream) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(inputStream, new TypeReference<GameStructure>() {});
    }

}
