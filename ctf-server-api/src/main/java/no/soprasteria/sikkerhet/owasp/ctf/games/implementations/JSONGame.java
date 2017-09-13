package no.soprasteria.sikkerhet.owasp.ctf.games.implementations;

import no.soprasteria.sikkerhet.owasp.ctf.games.GameConfig;
import no.soprasteria.sikkerhet.owasp.ctf.games.JSONGameConfig;
import no.soprasteria.sikkerhet.owasp.ctf.service.FlagService;

import java.io.IOException;

public class JSONGame extends JSONGameConfig implements GameConfig {

    public JSONGame(FlagService flagService, String resourceFile) throws IOException {
        super(flagService, resourceFile);
    }
}
