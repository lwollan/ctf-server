package no.soprasteria.sikkerhet.owasp.ctf.api;

import no.soprasteria.sikkerhet.owasp.ctf.ApplicationContext;
import no.soprasteria.sikkerhet.owasp.ctf.core.games.structure.GameStructure;
import no.soprasteria.sikkerhet.owasp.ctf.core.service.AnswerService;
import no.soprasteria.sikkerhet.owasp.ctf.core.service.FlagService;
import no.soprasteria.sikkerhet.owasp.ctf.core.service.GameService;
import no.soprasteria.sikkerhet.owasp.ctf.core.service.TeamService;

import javax.ws.rs.core.Application;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class TestSetup {

    static void setupTestGame(Application application) throws IOException {
        GameService gameService = ApplicationContext.get(application, GameService.class);
        GameStructure mrrobotGame = GameStructure.readJSON(BoardResourceTest.class.getResourceAsStream("/games/mrrobot.json"));
        gameService.setGame(mrrobotGame);
    }

    public static String setupTestTeamWithAnswers(Application application, String teamName) {
        TeamService teamService = ApplicationContext.get(application, TeamService.class);
        AnswerService answerService = ApplicationContext.get(application, AnswerService.class);
        FlagService flagService = ApplicationContext.get(application, FlagService.class);
        List<Map<String, String>> flags = flagService.listFlag();

        String flagId = flags.get(0).get(FlagService.Keys.flagId.toString());
        String riktigSvar = flags.get(0).get(FlagService.Keys.flag.toString());
        String feilSvar = riktigSvar + "feil";

        Optional<String> teamKey = teamService.addNewTeam(teamName);
        answerService.giveAnswer(teamKey.get(), flagId, feilSvar);
        answerService.giveAnswer(teamKey.get(), flagId, riktigSvar);

        return teamKey.get();
    }
}
