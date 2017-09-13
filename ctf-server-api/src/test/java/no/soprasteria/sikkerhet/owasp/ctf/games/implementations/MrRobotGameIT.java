package no.soprasteria.sikkerhet.owasp.ctf.games.implementations;

import no.soprasteria.sikkerhet.owasp.ctf.service.FlagService;
import org.junit.Test;

import java.io.IOException;

public class MrRobotGameIT {

    @Test
    public void skal_laste_spillet() throws IOException {
        JSONGame game = new JSONGame(new FlagService(), "/games/mrrobot.json");
    }

}