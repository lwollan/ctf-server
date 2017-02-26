package no.soprasteria.sikkerhet.owasp.ctf.games;

import no.soprasteria.sikkerhet.owasp.ctf.service.FlagService;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class OWASPGame {

    public static void setupGame(FlagService flagService) throws IOException {
        List<Map<String, String>> game = GameFactory.les(OWASPGame.class.getResourceAsStream("/apps0404.json"));

        game.stream().forEach(m -> {
            flagService.addFlag(m.get("flag-name"), m.get("svar"), Long.parseLong(m.get("poeng")), m.get("tips"), m.get("beskrivelse"));
        });
    }

}
