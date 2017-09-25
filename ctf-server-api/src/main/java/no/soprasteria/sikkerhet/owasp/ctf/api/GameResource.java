package no.soprasteria.sikkerhet.owasp.ctf.api;

import no.soprasteria.sikkerhet.owasp.ctf.ApplicationContext;
import no.soprasteria.sikkerhet.owasp.ctf.core.games.structure.GameStructure;
import no.soprasteria.sikkerhet.owasp.ctf.core.service.AnswerService;
import no.soprasteria.sikkerhet.owasp.ctf.filter.Beskyttet;
import no.soprasteria.sikkerhet.owasp.ctf.core.service.GameService;
import no.soprasteria.sikkerhet.owasp.ctf.core.service.TeamService;

import javax.ws.rs.*;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.*;

import static javax.ws.rs.core.Response.accepted;
import static javax.ws.rs.core.Response.serverError;

@Path("game")
public class GameResource {

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

        GameService gameService = ApplicationContext.get(application, GameService.class);
        gameService.pauseGame();

        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(requestContext.getEntityStream())) {
            Optional<GameStructure> gameToLoad = GameStructure.readJSON(bufferedInputStream);

            if (gameToLoad.isPresent()) {
                setAndStartGame(application, gameToLoad.get());
                return accepted("Game loaded").build();
            } else {
                return serverError().status(Response.Status.BAD_REQUEST).build();
            }
        } catch (Exception e) {
            return serverError().status(Response.Status.BAD_REQUEST).build();
        }
    }

    private static void setAndStartGame(Application application, GameStructure gameStructure) {
        GameService gameService = ApplicationContext.get(application, GameService.class);
        AnswerService answerService = ApplicationContext.get(application, AnswerService.class);

        gameService.setGame(gameStructure);
        answerService.clear();

        gameService.startGame();
    }
}
