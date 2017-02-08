package no.soprasteria.sikkerhet.owasp.ctf.api;

import no.soprasteria.sikkerhet.owasp.ctf.filter.Beskyttet;
import no.soprasteria.sikkerhet.owasp.ctf.filter.TeamKeyFilter;
import no.soprasteria.sikkerhet.owasp.ctf.service.TeamService;
import no.soprasteria.sikkerhet.owasp.ctf.ApplicationContext;

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
import java.util.stream.Collectors;

@Path("team")
public class TeamResource {

    @Path("add/{teamname}")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response add(@Context Application application, @PathParam("teamname") String teamname) {
        TeamService teamService = ApplicationContext.get(application, TeamService.class);
        Optional<String> teamKey = teamService.addNewTeam(teamname);

        if (teamKey.isPresent()) {
            Map<String, String> response = new HashMap<>();
            response.put(TeamKeyFilter.X_TEAM_KEY, teamKey.get());
            return Response.ok(response).build();
        } else {
            return Response.status(Response.Status.CONFLICT).build();
        }
    }

    @Beskyttet
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Map<String, String>> list(@Context Application application) {
        TeamService teamService = ApplicationContext.get(application, TeamService.class);
        List<String> teamList = teamService.getTeamList();
        return teamList.stream().map(teamName -> {
            Map<String, String> map = new HashMap<>();
            map.put(teamName, teamService.findTeamKeyByTameName(teamName).orElse("FEIL"));
            return map;
        }).collect(Collectors.toList());
    }

}
