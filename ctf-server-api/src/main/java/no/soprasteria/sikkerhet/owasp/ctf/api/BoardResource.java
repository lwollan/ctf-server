package no.soprasteria.sikkerhet.owasp.ctf.api;

import no.soprasteria.sikkerhet.owasp.ctf.ApplicationContext;
import no.soprasteria.sikkerhet.owasp.ctf.core.service.BoardService;
import no.soprasteria.sikkerhet.owasp.ctf.core.service.GameService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static javax.ws.rs.core.Response.ok;
import static javax.ws.rs.core.Response.serverError;

@Path("public/board")
public class BoardResource {

    private static Logger logger = LoggerFactory.getLogger(BoardResource.class);

    public enum BoardResponseKeys {
        score, title, gameOn, start, end
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBoard(@Context Application application) {
        GameService gameService = ApplicationContext.get(application, GameService.class);
        BoardService boardService = ApplicationContext.get(application, BoardService.class);

        if (gameService.isGameAvailable()) {
            List<Map<String, String>> score = boardService.getScore();
            Map<BoardResponseKeys, Object> response = new HashMap<>();
            response.put(BoardResponseKeys.score, score);
            response.put(BoardResponseKeys.title, gameService.getName());
            response.put(BoardResponseKeys.gameOn, gameService.isGameOn());
            response.put(BoardResponseKeys.start, LocalDateTime.now().toString());
            response.put(BoardResponseKeys.end, LocalDateTime.now().plusHours(2).toString());

            return ok(response).build();
        } else {
            logger.info("Ingen spill satt opp.");
            return serverError().build();
        }
    }

}
