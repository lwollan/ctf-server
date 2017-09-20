package no.soprasteria.sikkerhet.owasp.ctf.api;

import no.soprasteria.sikkerhet.owasp.ctf.core.service.AnswerService;
import no.soprasteria.sikkerhet.owasp.ctf.filter.Beskyttet;
import no.soprasteria.sikkerhet.owasp.ctf.filter.TeamKeyFilter;
import no.soprasteria.sikkerhet.owasp.ctf.core.service.TeamService;
import no.soprasteria.sikkerhet.owasp.ctf.ApplicationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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

    private static Logger logger = LoggerFactory.getLogger(TeamResource.class);

    enum TeamResourceResponseKeys {
        teamName, teamKey, score
    }

    @Path("add/{teamname}")
    @POST
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
        AnswerService answerService = ApplicationContext.get(application, AnswerService.class);

        Optional<String> teamKeyByTameName = teamService.findTeamKeyByTeameName(teamname);
        if (teamKeyByTameName.isPresent()) {
            boolean deletedTeam = teamService.deleteTeam(teamKeyByTameName.get());
            answerService.deleteTeamScore(teamKeyByTameName.get());

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
    public List<Map<TeamResourceResponseKeys, String>> list(@Context Application application) {
        TeamService teamService = ApplicationContext.get(application, TeamService.class);
        List<String> teamList = teamService.getTeamList();
        return teamList.stream().map(teamName -> {
            Map<TeamResourceResponseKeys, String> response = new HashMap<>();
            response.put(TeamResourceResponseKeys.teamName, teamName);
            response.put(TeamResourceResponseKeys.teamKey, teamService.findTeamKeyByTeameName(teamName).orElse("FEIL"));
            return response;
        }).collect(Collectors.toList());
    }

    @Beskyttet
    @Path("/{teamName}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map<TeamResourceResponseKeys, String> get(@Context Application application, @PathParam("teamName") String teamName) {
        TeamService teamService = ApplicationContext.get(application, TeamService.class);
        AnswerService answerService = ApplicationContext.get(application, AnswerService.class);
        Optional<String> teamKey = teamService.findTeamKeyByTeameName(teamName);

        if (teamKey.isPresent()) {
            Long teamScore = answerService.getTeamScore(teamKey.get());

            Map<TeamResourceResponseKeys, String> response = new HashMap<>();

            response.put(TeamResourceResponseKeys.teamName, teamName);
            response.put(TeamResourceResponseKeys.teamKey, teamKey.get());
            response.put(TeamResourceResponseKeys.score, String.valueOf(teamScore));

            return response;
        } else {
            logger.info("Team {} not found.", teamName);
            return new HashMap<>();
        }
    }
}
