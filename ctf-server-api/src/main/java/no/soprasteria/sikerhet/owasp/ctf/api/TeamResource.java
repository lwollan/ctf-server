package no.soprasteria.sikerhet.owasp.ctf.api;

import no.soprasteria.sikerhet.owasp.ctf.filter.Beskyttet;
import no.soprasteria.sikerhet.owasp.ctf.service.TeamService;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static no.soprasteria.sikerhet.owasp.ctf.ApplicationContext.get;

@Path("team")
public class TeamResource {

    @Path("add/{teamname}")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response add(@Context Application application, @PathParam("teamname") String teamname) {
        TeamService teamService = get(application, TeamService.class);
        Optional<String> teamKey = teamService.addNewTeam(teamname);

        if (teamKey.isPresent()) {
            Map<String, String> response = new HashMap<>();
            response.put("team-key", teamKey.get());
            return Response.ok(response).build();
        } else {
            return Response.status(Response.Status.CONFLICT).build();
        }
    }

    @Beskyttet
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> list(@Context Application application) {
        TeamService teamService = get(application, TeamService.class);
        return teamService.getTeamList();
    }
}
