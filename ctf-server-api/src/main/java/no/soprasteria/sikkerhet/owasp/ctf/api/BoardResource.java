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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static javax.ws.rs.core.Response.ok;

@Path("public/board")
public class BoardResource {

    private static Logger logger = LoggerFactory.getLogger(BoardResource.class);

    public enum BoardResponseKeys {
        score, title, gameOn, start, end, beskrivelse
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBoard(@Context Application application) {
        GameService gameService = ApplicationContext.get(application, GameService.class);
        BoardService boardService = ApplicationContext.get(application, BoardService.class);

        Map<BoardResponseKeys, Object> response = new HashMap<>();
        if (gameService.isGameAvailable()) {
            List<Map<String, String>> score = boardService.getScore();
            response.put(BoardResponseKeys.score, score);

            response.put(BoardResponseKeys.title, gameService.getName());
            response.put(BoardResponseKeys.beskrivelse, gameService.getGameDescription());
            response.put(BoardResponseKeys.start, gameService.getStartTime());
            response.put(BoardResponseKeys.end, gameService.getEndTime());
        } else {
            response.put(BoardResponseKeys.title, "No Game Configured");
            response.put(BoardResponseKeys.beskrivelse, "Upload game via admin page.");

        }
        response.put(BoardResponseKeys.gameOn, gameService.isGameOn());

        return ok(response).build();
    }

}
