package no.soprasteria.sikkerhet.owasp.ctf.api;

import no.soprasteria.sikkerhet.owasp.ctf.ApplicationContext;
import no.soprasteria.sikkerhet.owasp.ctf.service.BoardService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Path("public/board")
public class BoardResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> get(@Context Application application) {
        BoardService boardService = ApplicationContext.get(application, BoardService.class);

        Map<String, Object> response = new HashMap();

        List<Map<String, String>> score = boardService.getScore();

        response.put("score", score);
        response.put("title", "Sopra Steria CtF 2017");

        return response;
    }
}
