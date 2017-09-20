package no.soprasteria.sikkerhet.owasp.ctf.api;

import no.soprasteria.sikkerhet.owasp.ctf.ApplicationContext;
import no.soprasteria.sikkerhet.owasp.ctf.core.games.structure.GameStructure;
import no.soprasteria.sikkerhet.owasp.ctf.filter.Beskyttet;
import no.soprasteria.sikkerhet.owasp.ctf.core.service.BoardService;
import no.soprasteria.sikkerhet.owasp.ctf.core.service.FlagService;
import no.soprasteria.sikkerhet.owasp.ctf.core.service.GameService;
import no.soprasteria.sikkerhet.owasp.ctf.core.service.TeamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Path("game")
public class GameResource {

    private static Logger logger = LoggerFactory.getLogger(GameResource.class);

    enum GameResourceResponseKeys {
        game, flags, gameOn, score
    }

    /**
    @Beskyttet
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Map<GameResourceResponseKeys, Object>> list(@Context Application application) {
        GameService gameService = ApplicationContext.get(application, GameService.class);
        FlagService flagService = ApplicationContext.get(application, FlagService.class);
        BoardService boardService = ApplicationContext.get(application, BoardService.class);

        if (gameService.isGameAvailable()) {
            Map<GameResourceResponseKeys, Object> singleGame = new HashMap<>();
            singleGame.put(GameResourceResponseKeys.game, gameService.getName());
            singleGame.put(GameResourceResponseKeys.flags, flagService.listFlag());
            singleGame.put(GameResourceResponseKeys.gameOn, gameService.isGameOn());
            singleGame.put(GameResourceResponseKeys.score, boardService.getScore());

            return Arrays.asList(singleGame);
        } else {
            logger.info("Ingen spill aktiv.");
            return Collections.emptyList();
        }
    }
     */

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

    @Beskyttet
    @POST
    @Path("load")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response load(@Context Application application, @Context ContainerRequestContext requestContext) throws IOException {

        InputStream entityStream = requestContext.getEntityStream();
        GameService gameService = ApplicationContext.get(application, GameService.class);

        GameStructure gameToLoad = GameStructure.readJSON(entityStream);
        gameService.setGame(gameToLoad);

        gameService.startGame();
        return Response.ok().build();
    }
}
