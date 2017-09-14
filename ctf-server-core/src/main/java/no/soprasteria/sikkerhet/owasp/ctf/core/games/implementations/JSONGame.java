package no.soprasteria.sikkerhet.owasp.ctf.core.games.implementations;

import no.soprasteria.sikkerhet.owasp.ctf.core.games.GameConfig;
import no.soprasteria.sikkerhet.owasp.ctf.core.games.JSONGameConfig;
import no.soprasteria.sikkerhet.owasp.ctf.core.service.FlagService;

import java.io.IOException;

public class JSONGame extends JSONGameConfig implements GameConfig {

    public JSONGame(FlagService flagService, String resourceFile) throws IOException {
        super(flagService, resourceFile);
    }
}
