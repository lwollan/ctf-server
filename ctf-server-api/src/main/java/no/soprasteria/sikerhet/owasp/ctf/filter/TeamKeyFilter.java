package no.soprasteria.sikerhet.owasp.ctf.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@Provider
@TeamKey
public class TeamKeyFilter implements ContainerRequestFilter {

    public static final String X_TEAM_KEY = "X-TEAM-KEY";

    private static final Logger logger = LoggerFactory.getLogger(BeskyttetFilter.class);

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        String teamKey = containerRequestContext.getHeaderString(X_TEAM_KEY);

        if (teamKeyIsInvalid(teamKey)) {
            logger.info("Ugyldig team key.");
            logger.info(" -- headere: {}.", containerRequestContext.getHeaders());
            containerRequestContext.abortWith(Response.status(Response.Status.BAD_REQUEST).build());
        } else {
            // sikkert lurt å legge noe på request ..
        }
    }

    private static boolean teamKeyIsInvalid(String teamKey) {
        if (teamKey == null) {
            return true;
        } else {
            return false;
        }
    }
}