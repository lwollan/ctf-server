package no.soprasteria.sikkerhet.owasp.ctf.api;

import no.soprasteria.sikkerhet.owasp.ctf.filter.Beskyttet;
import no.soprasteria.sikkerhet.owasp.ctf.filter.TeamKeyFilter;
import no.soprasteria.sikkerhet.owasp.ctf.service.ScoreService;
import no.soprasteria.sikkerhet.owasp.ctf.service.TeamService;
import no.soprasteria.sikkerhet.owasp.ctf.ApplicationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
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

    private static Logger logger = LoggerFactory.getLogger(ScoreService.class);

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
    @Path("{teamname}")
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    public Response del(@Context Application application, @PathParam("teamname") String teamname) {
        TeamService teamService = ApplicationContext.get(application, TeamService.class);
        ScoreService scoreService = ApplicationContext.get(application, ScoreService.class);

        Optional<String> teamKeyByTameName = teamService.findTeamKeyByTeameName(teamname);
        if (teamKeyByTameName.isPresent()) {
            boolean deletedTeam = teamService.deleteTeam(teamKeyByTameName.get());
            scoreService.deleteTeamScore(teamKeyByTameName.get());

            if (deletedTeam) {
                logger.info("Team key {} with score deleted.", teamname);
                return Response.ok().build();
            } else {
                logger.warn("Feil ved sletting av team. deletedTeam={} deletedTeamScore={}.");
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
        } else {
            logger.info("Team {} ikke funnet.", teamname);
            return Response.status(Response.Status.NOT_FOUND).build();
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
            map.put("teamName", teamName);
            map.put("teamKey", teamService.findTeamKeyByTeameName(teamName).orElse("FEIL"));
            return map;
        }).collect(Collectors.toList());
    }

    @Beskyttet
    @Path("/{teamName}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@Context Application application, @PathParam("teamName") String teamName) {
        TeamService teamService = ApplicationContext.get(application, TeamService.class);
        Optional<String> teamKey = teamService.findTeamKeyByTeameName(teamName);

        if (teamKey.isPresent()) {
            ScoreService scoreService = ApplicationContext.get(application, ScoreService.class);

            Long teamScore = scoreService.getTeamScore(teamKey.get());

            Map<String, String> response = new HashMap<>();

            response.put("teamName", teamName);
            response.put("teamKey", teamKey.get());
            response.put("score", String.valueOf(teamScore));

            return Response.ok(response).build();
        } else {
            logger.info("Team {} not found.", teamName);
            return Response.ok(new HashMap<String, String>()).build();
        }
    }
}
