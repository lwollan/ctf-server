package no.soprasteria.sikerhet.owasp.ctf.api;

import no.soprasteria.sikerhet.owasp.ctf.ApplicationContext;
import no.soprasteria.sikerhet.owasp.ctf.filter.Beskyttet;
import no.soprasteria.sikerhet.owasp.ctf.service.ScoreService;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;

@Path("score")
@Beskyttet
public class ScoreResource {

    @Path("add/{team}/{points}")
    @POST
    public void add(@Context Application application, @PathParam("team") String teamname, @PathParam("points") Long points) {
        ScoreService scoreService = ApplicationContext.get(application, ScoreService.class);
        scoreService.addPointsToTeam(teamname, points);
    }

    @Path("del/{team}/{points}")
    @POST
    public void del(@Context Application application, @PathParam("team") String teamname, @PathParam("points") Long points) {
        ScoreService scoreService = ApplicationContext.get(application, ScoreService.class);
        scoreService.delPointsFromTeam(teamname, points);
    }

    @Path("reset/{team}")
    @POST
    public void reset(@Context Application application, @PathParam("team") String teamname) {
        ScoreService scoreService = ApplicationContext.get(application, ScoreService.class);
        scoreService.resetTeamScore(teamname);
    }
}
