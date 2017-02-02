package no.soprasteria.sikerhet.owasp.ctf.api;


import no.soprasteria.sikerhet.owasp.ctf.ApplicationContext;
import no.soprasteria.sikerhet.owasp.ctf.filter.TeamKey;
import no.soprasteria.sikerhet.owasp.ctf.service.FlagService;
import no.soprasteria.sikerhet.owasp.ctf.service.ScoreService;
import no.soprasteria.sikerhet.owasp.ctf.service.TeamService;
import org.glassfish.jersey.server.ContainerRequest;
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

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static no.soprasteria.sikerhet.owasp.ctf.filter.TeamKeyFilter.X_TEAM_KEY;

@Path("flag")
public class FlagResource {

    private static Logger logger = LoggerFactory.getLogger(FlagResource.class);

    enum Answer {
        flagId, answer
    }

    @TeamKey
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response answer(@Context Application application, @Context ContainerRequest request, Map<String, String> body) {
        if (body != null && request.getHeaderString(X_TEAM_KEY) != null) {
            String teamKey = request.getHeaderString(X_TEAM_KEY);

            String flagId = body.getOrDefault(Answer.flagId.toString(), null);
            String answer = body.getOrDefault(Answer.answer.toString(), null);

            if (flagId != null && answer != null) {
                return handleAnswerAndGetResponse(application, teamKey, flagId, answer);
            }
        }

        logger.info("Bad flag request.");
        return Response.status(BAD_REQUEST).build();
    }

    private static Response handleAnswerAndGetResponse(@Context Application application, String teamKey, String flagId, String answer) {
        FlagService flagService = ApplicationContext.get(application, FlagService.class);
        if (flagService.isFlagUnanswered(teamKey, flagId) && flagService.isCorrect(flagId, answer)) {
            flagService.answerFlag(teamKey, flagId);
            correctAnswer(application, teamKey, flagService.getPoints(flagId), flagId);
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

    private static void correctAnswer(@Context Application application, String teamKey, Long points, String flagId) {
        Optional<String> teamName = ApplicationContext.get(application, TeamService.class).getTeamName(teamKey);
        logger.info("Correct answer from team '{}'.", teamName.get());
        ScoreService scoreService = ApplicationContext.get(application, ScoreService.class);
        scoreService.addPointsToTeam(teamKey, points);
    }

    @TeamKey
    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Map<String, String>> list(@Context Application application) {
        FlagService flagService = ApplicationContext.get(application, FlagService.class);

        List<Map<String, String>> flags = flagService.listFlag();

        return flags.stream().map(m -> {
            Map<String, String> filteredMap = new HashMap<>();
            filteredMap.put("flag-id", m.get("flag-id"));
            filteredMap.put("flag-name", m.get("flag-name"));
            return filteredMap;
        }).collect(Collectors.toList());

    }


}
