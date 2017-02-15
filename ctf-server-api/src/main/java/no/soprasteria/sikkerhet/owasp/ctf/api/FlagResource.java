package no.soprasteria.sikkerhet.owasp.ctf.api;


import no.soprasteria.sikkerhet.owasp.ctf.ApplicationContext;
import no.soprasteria.sikkerhet.owasp.ctf.filter.TeamKey;
import no.soprasteria.sikkerhet.owasp.ctf.service.FlagService;
import no.soprasteria.sikkerhet.owasp.ctf.service.TeamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static no.soprasteria.sikkerhet.owasp.ctf.filter.TeamKeyFilter.X_TEAM_KEY;

@Path("flag")
public class FlagResource {

    private static Logger logger = LoggerFactory.getLogger(FlagResource.class);

    enum Keys {
        flagId, flag, flagAnswered, flagName
    }

    @TeamKey
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response answer(@Context Application application, @Context ContainerRequestContext request, Map<String, String> body) {
        if (body != null && request.getHeaderString(X_TEAM_KEY) != null) {
            String teamKey = request.getHeaderString(X_TEAM_KEY);

            String flagId = body.getOrDefault(Keys.flagId.toString(), null);
            String flag = body.getOrDefault(Keys.flag.toString(), null);

            if (flagId != null && flag != null) {
                return handleAnswerAndGetResponse(application, teamKey, flagId, flag);
            }
        }

        logger.info("Bad flag request.");
        return Response.status(BAD_REQUEST).build();
    }

    @TeamKey
    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Map<Keys, String>> list(@Context Application application, @HeaderParam("X-TEAM-KEY") String teamKey) {
        FlagService flagService = ApplicationContext.get(application, FlagService.class);
        List<Map<String, String>> flags = flagService.listFlag();

        return flags.stream()
                .map(flagMap -> newFlagResponseMap(teamKey, flagMap, flagService))
                .collect(Collectors.toList());

    }

    @TeamKey
    @GET
    @Path("tip/{flagId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, String> tip(@Context Application application, @PathParam("flagId") String flagId) {
        FlagService flagService = ApplicationContext.get(application, FlagService.class);

        String tip = flagService.getTip(flagId);

        if (tip != null) {
            Map<String, String> reponse = new HashMap<>();
            reponse.put(FlagService.Keys.tips.toString(), tip);
            return reponse;
        } else {
            return new HashMap<>();
        }
    }

    private static Map<Keys, String> newFlagResponseMap(String teamKey, Map<String, String> flagMap, FlagService flagService) {
        Map<Keys, String> map = new HashMap<>();
        map.put(Keys.flagId, flagMap.get(FlagService.Keys.flagId.toString()));
        map.put(Keys.flagName, flagMap.get(FlagService.Keys.flagName.toString()));
        map.put(Keys.flagAnswered, String.valueOf(!flagService.isFlagUnanswered(teamKey, flagMap.get(FlagService.Keys.flagId.toString()))));
        return map;
    }

    private static Response handleAnswerAndGetResponse(@Context Application application, String teamKey, String flagId, String answer) {
        FlagService flagService = ApplicationContext.get(application, FlagService.class);
        if (flagService.isFlagUnanswered(teamKey, flagId) && flagService.isCorrect(flagId, answer)) {
            flagService.answerFlag(teamKey, flagId);
            return Response.accepted().build();
        } else {
            incorrectAnswer(application, teamKey, flagId, answer);
            return Response.status(BAD_REQUEST).build();
        }
    }

    private static void incorrectAnswer(Application application, String teamKey, String flagId, String answer) {
        String teamName = ApplicationContext.get(application, TeamService.class).getTeamName(teamKey).get();
        logger.info("Incorrect answer '{}' for flag '{}' from team '{}'.", flagId, answer, teamName);
    }

}
