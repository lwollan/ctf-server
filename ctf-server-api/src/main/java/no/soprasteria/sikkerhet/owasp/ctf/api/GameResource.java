package no.soprasteria.sikkerhet.owasp.ctf.api;

import no.soprasteria.sikkerhet.owasp.ctf.ApplicationContext;
import no.soprasteria.sikkerhet.owasp.ctf.filter.Beskyttet;
import no.soprasteria.sikkerhet.owasp.ctf.core.service.BoardService;
import no.soprasteria.sikkerhet.owasp.ctf.core.service.FlagService;
import no.soprasteria.sikkerhet.owasp.ctf.core.service.GameService;
import no.soprasteria.sikkerhet.owasp.ctf.core.service.TeamService;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("game")
public class GameResource {

    enum GameResourceResponseKeys {
        game, flags, gameOn, score
    }

    @Beskyttet
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Map<GameResourceResponseKeys, Object>> list(@Context Application application) {
        GameService gameService = ApplicationContext.get(application, GameService.class);
        FlagService flagService = ApplicationContext.get(application, FlagService.class);
        BoardService boardService = ApplicationContext.get(application, BoardService.class);

        Map<GameResourceResponseKeys, Object> singleGame = new HashMap<>();
        singleGame.put(GameResourceResponseKeys.game, gameService.getName());
        singleGame.put(GameResourceResponseKeys.flags, flagService.listFlag());
        singleGame.put(GameResourceResponseKeys.gameOn, gameService.isGameOn());
        singleGame.put(GameResourceResponseKeys.score, boardService.getScore());

        return Arrays.asList(singleGame);
    }

    @Beskyttet
    @POST
    @Path("start")
    public void startGame(@Context Application application) {
        GameService gameService = ApplicationContext.get(application, GameService.class);
        gameService.startGame();
    }

    @Beskyttet
    @POST
    @Path("stop")
    public void stopGame(@Context Application application) {
        GameService gameService = ApplicationContext.get(application, GameService.class);
        gameService.pauseGame();
    }

    @Beskyttet
    @POST
    @Path("sys64764")
    public void reset(@Context Application application) {
        TeamService teamService = ApplicationContext.get(application, TeamService.class);
        teamService.reset();
    }

}
