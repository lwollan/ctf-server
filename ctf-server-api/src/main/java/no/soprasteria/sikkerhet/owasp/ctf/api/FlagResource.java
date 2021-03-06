package no.soprasteria.sikkerhet.owasp.ctf.api;

import no.soprasteria.sikkerhet.owasp.ctf.ApplicationContext;
import no.soprasteria.sikkerhet.owasp.ctf.api.requestparameters.FlagIdParameter;
import no.soprasteria.sikkerhet.owasp.ctf.api.requestparameters.TeamKeyParameter;
import no.soprasteria.sikkerhet.owasp.ctf.core.service.AnswerService;
import no.soprasteria.sikkerhet.owasp.ctf.core.service.GameService;
import no.soprasteria.sikkerhet.owasp.ctf.filter.TeamKey;
import no.soprasteria.sikkerhet.owasp.ctf.core.service.FlagService;
import no.soprasteria.sikkerhet.owasp.ctf.core.service.TeamService;
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

@Path("flag")
public class FlagResource {

    private static Logger logger = LoggerFactory.getLogger(FlagResource.class);

    enum FlagResourceResponseKeys {
        flagId, flag, flagDescription, flagAnswered, flagName, tips
    }

    enum FlagResourceRequestKeys {
        flagId, flag
    }

    @TeamKey
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response answer(@Context Application application, Map<String, String> body, @HeaderParam("X-TEAM-KEY") TeamKeyHeaderParameter teamKeyParam) {
        if (body != null && teamKeyParam != null) {
            if (ApplicationContext.get(application, GameService.class).isGameOn()) {

                String flagId = body.getOrDefault(FlagResourceRequestKeys.flagId.toString(), null);
                String flag = body.getOrDefault(FlagResourceRequestKeys.flag.toString(), null);

                if (flagId != null && flag != null) {
                    return handleAnswerAndGetResponse(application, teamKeyParam.value, flagId, flag);
                }
            } else {
                logger.warn("Game not enabled.");
            }
        }
        logger.info("Bad flag request.");
        return Response.status(BAD_REQUEST).build();
    }

    @TeamKey
    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Map<FlagResourceResponseKeys, String>> list(@Context ContainerRequestContext request, @Context Application application, @HeaderParam("X-TEAM-KEY") TeamKeyHeaderParameter teamKey) {
        FlagService flagService = ApplicationContext.get(application, FlagService.class);
        AnswerService answerService = ApplicationContext.get(application, AnswerService.class);
        List<Map<String, String>> flags = flagService.listFlag();

        return flags.stream()
                .map(flagMap -> newFlagResponseMap(teamKey.value, flagMap, answerService))
                .collect(Collectors.toList());

    }

    @TeamKey
    @GET
    @Path("tip/{flagId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, String> tip(@Context Application application, @PathParam("flagId") FlagIdParameter flagId) {
        FlagService flagService = ApplicationContext.get(application, FlagService.class);

        String tip = flagService.getTip(flagId.value);

        if (tip != null) {
            Map<String, String> reponse = new HashMap<>();
            reponse.put(FlagResourceResponseKeys.tips.toString(), tip);
            return reponse;
        } else {
            return new HashMap<>();
        }
    }

    private static Map<FlagResourceResponseKeys, String> newFlagResponseMap(String teamKey, Map<String, String> flagMap, AnswerService answerService) {
        Map<FlagResourceResponseKeys, String> response = new HashMap<>();
        response.put(FlagResourceResponseKeys.flagId, flagMap.get(FlagService.Keys.flagId.toString()));
        response.put(FlagResourceResponseKeys.flagName, flagMap.get(FlagService.Keys.flagName.toString()));
        response.put(FlagResourceResponseKeys.tips, flagMap.get(FlagService.Keys.tips.toString()));
        response.put(FlagResourceResponseKeys.flagDescription, flagMap.get(FlagService.Keys.beskrivelse.toString()));
        response.put(FlagResourceResponseKeys.flagAnswered, String.valueOf(!answerService.isFlagUnanswered(teamKey, flagMap.get(FlagService.Keys.flagId.toString()))));
        return response;
    }

    private static Response handleAnswerAndGetResponse(@Context Application application, String teamKey, String flagId, String answer) {
        FlagService flagService = ApplicationContext.get(application, FlagService.class);
        if (flagService.isFlag(flagId)) {
            AnswerService answerService = ApplicationContext.get(application, AnswerService.class);
            boolean correctAnswer = answerService.giveAnswer(teamKey, flagId, answer);
            if (correctAnswer) {
                return Response.accepted().build();
            } else {
                incorrectAnswer(application, teamKey, flagId, answer);
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
        } else {
            logger.info("Invalid flagId {}", flagId);
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    private static void incorrectAnswer(Application application, String teamKey, String flagId, String answer) {
        String teamName = ApplicationContext.get(application, TeamService.class).getTeamName(teamKey).get();
        logger.info("Incorrect answer '{}' for flag '{}' valueOf team '{}'.", answer, flagId, teamName);
    }

}
