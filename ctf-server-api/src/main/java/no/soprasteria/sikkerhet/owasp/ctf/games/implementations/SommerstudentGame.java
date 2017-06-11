package no.soprasteria.sikkerhet.owasp.ctf.games.implementations;

import no.soprasteria.sikkerhet.owasp.ctf.games.GameConfig;
import no.soprasteria.sikkerhet.owasp.ctf.games.JSONGameConfig;
import no.soprasteria.sikkerhet.owasp.ctf.service.FlagService;

import java.io.IOException;

public class SommerstudentGame extends JSONGameConfig implements GameConfig {

    public SommerstudentGame(FlagService flagService) throws IOException {
        setupGame(flagService, "/sommerstudenter2017.json");
    }

    @Override
    public String getName() {
        return "Sommer Studenter 2017";
    }
}
