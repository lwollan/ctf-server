package no.soprasteria.sikkerhet.owasp.ctf.api;

import no.soprasteria.sikkerhet.owasp.ctf.ApplicationContext;
import no.soprasteria.sikkerhet.owasp.ctf.core.games.structure.GameStructure;
import no.soprasteria.sikkerhet.owasp.ctf.core.service.GameService;

import javax.ws.rs.core.Application;
import java.io.IOException;

public class TestSetup {

    static void setupTestGame(Application application) throws IOException {
        GameService gameService = ApplicationContext.get(application, GameService.class);
        GameStructure mrrobotGame = GameStructure.readJSON(BoardResourceTest.class.getResourceAsStream("/games/mrrobot.json"));
        gameService.setGame(mrrobotGame);
    }
}
