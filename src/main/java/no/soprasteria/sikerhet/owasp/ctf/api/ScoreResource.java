package no.soprasteria.sikerhet.owasp.ctf.api;

import no.soprasteria.sikerhet.owasp.ctf.CtFApplication;
import no.soprasteria.sikerhet.owasp.ctf.service.ScoreRepository;
import no.soprasteria.sikerhet.owasp.ctf.service.ScoreService;

import javax.ws.rs.*;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.Map;

@Path("ctf/score")
public class ScoreResource {

    @Path("get")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Long> get(@Context Application application) {
        ScoreRepository scoreRepo = CtFApplication.get(application, ScoreRepository.class);
        return scoreRepo.list();
    }

    @Path("add/{team}/{points}")
    @POST
    public void add(@Context Application application, @PathParam("team") String teamname, @PathParam("points") Long points) {
        ScoreService teamRepository = CtFApplication.get(application, ScoreService.class);
        teamRepository.addPointsToTeam(teamname, points);
    }
}
