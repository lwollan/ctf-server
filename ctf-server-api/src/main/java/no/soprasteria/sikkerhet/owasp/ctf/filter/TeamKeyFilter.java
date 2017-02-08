package no.soprasteria.sikkerhet.owasp.ctf.filter;

import no.soprasteria.sikkerhet.owasp.ctf.ApplicationContext;
import no.soprasteria.sikkerhet.owasp.ctf.service.TeamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.Optional;

@Provider
@TeamKey
public class TeamKeyFilter implements ContainerRequestFilter {

    public static final String X_TEAM_KEY = "X-TEAM-KEY";

    private static final Logger logger = LoggerFactory.getLogger(BeskyttetFilter.class);

    @Context
    Application application;

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        String teamKey = containerRequestContext.getHeaderString(X_TEAM_KEY);

        TeamService teamService = ApplicationContext.get(application, TeamService.class);
        Optional<String> teamName = teamService.getTeamName(teamKey);
        if (!teamName.isPresent()) {
            logger.info("Ugyldig team key.");
            logger.info(" -- headere: {}.", containerRequestContext.getHeaders());
            containerRequestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).header("WWW-Authenticate", X_TEAM_KEY).build());
        }
    }

}
