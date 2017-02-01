package no.soprasteria.sikerhet.owasp.ctf.api;

import no.soprasteria.sikerhet.owasp.ctf.CtFApplication;
import no.soprasteria.sikerhet.owasp.ctf.service.TeamRepository;

import javax.ws.rs.*;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Path("ctf/team")
public class TeamResource {

    @Path("add/{teamname}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    public Response add(@Context Application application, @PathParam("teamname") String teamname) {
        TeamRepository teamRepository = CtFApplication.get(application, TeamRepository.class);
        String teamSecret = Base64.getEncoder().encodeToString(teamname.getBytes());
        if (teamRepository.add(teamname, teamSecret)) {
            return Response.ok(teamSecret).build();
        } else {
            return Response.status(Response.Status.CONFLICT).build();
        }
    }

    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public List<String> list(@Context Application application) {
        TeamRepository teamRepository = CtFApplication.get(application, TeamRepository.class);
        Map<String, String> teams = teamRepository.list();
        return teams.keySet().stream().collect(Collectors.toList());
    }
}
