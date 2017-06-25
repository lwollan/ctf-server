package no.soprasteria.sikkerhet.owasp.ctf.games.implementations;

import no.soprasteria.sikkerhet.owasp.ctf.games.GameConfig;
import no.soprasteria.sikkerhet.owasp.ctf.games.JSONGameConfig;
import no.soprasteria.sikkerhet.owasp.ctf.service.FlagService;

import java.io.IOException;

public class OWASPGame extends JSONGameConfig implements GameConfig {

    private OWASPGame() {

    }

    public OWASPGame(FlagService flagService) throws IOException {
        setupGame(flagService, "/apps0404.json");
    }

    @Override
    public String getName() {
        return "Applications Fagdag 2017";
    }

}
