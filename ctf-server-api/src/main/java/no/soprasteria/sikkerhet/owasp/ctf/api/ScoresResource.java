package no.soprasteria.sikkerhet.owasp.ctf.api;

import no.soprasteria.sikkerhet.owasp.ctf.core.service.AnswerService;
import no.soprasteria.sikkerhet.owasp.ctf.core.service.Svar;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;

import static no.soprasteria.sikkerhet.owasp.ctf.ApplicationContext.get;

@Path("public/scores")
public class ScoresResource {

    @Path("list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response list(@Context Application application) {
        AnswerService answerService = get(application, AnswerService.class);

        Map<String, Map<String, List<Svar>>> answers = answerService.getAnswersForTeams();
        return Response.ok(answers).build();
    }

}
