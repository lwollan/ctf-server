package no.soprasteria.sikkerhet.owasp.ctf.api;

import no.soprasteria.sikkerhet.owasp.ctf.ApplicationContext;
import no.soprasteria.sikkerhet.owasp.ctf.service.BoardService;
import no.soprasteria.sikkerhet.owasp.ctf.service.GameService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("public/board")
public class BoardResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> get(@Context Application application) {
        BoardService boardService = ApplicationContext.get(application, BoardService.class);
        GameService gameService = ApplicationContext.get(application, GameService.class);

        Map<String, Object> response = new HashMap<>();

        List<Map<String, String>> score = boardService.getScore();

        response.put("score", score);
        response.put("title", "Sopra Steria CtF 2017");
        response.put("gameOn", gameService.isGameOn());

        return response;
    }
}
