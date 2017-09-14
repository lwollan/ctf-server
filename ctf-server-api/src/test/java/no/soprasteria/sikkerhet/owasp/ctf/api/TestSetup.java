package no.soprasteria.sikkerhet.owasp.ctf.api;

import no.soprasteria.sikkerhet.owasp.ctf.ApplicationContext;
import no.soprasteria.sikkerhet.owasp.ctf.CtFApplication;
import no.soprasteria.sikkerhet.owasp.ctf.core.games.structure.GameStructure;
import no.soprasteria.sikkerhet.owasp.ctf.core.service.GameService;

import java.io.IOException;
import java.util.UUID;

public class TestSetup {

    static void setupTestGame(CtFApplication application) throws IOException {
        GameService gameService = ApplicationContext.get(application, GameService.class);
        GameStructure mrrobotGame = GameStructure.readJSON(BoardResourceTest.class.getResourceAsStream("/games/mrrobot.json"));
        UUID gameId = gameService.addGame(mrrobotGame);
        ApplicationContext.put(application, gameId);
    }
}
