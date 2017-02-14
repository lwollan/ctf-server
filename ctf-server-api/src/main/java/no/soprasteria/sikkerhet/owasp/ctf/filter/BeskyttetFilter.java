package no.soprasteria.sikkerhet.owasp.ctf.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.Base64;

import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;

@Provider
@Beskyttet
public class BeskyttetFilter implements ContainerRequestFilter {

    private static Logger logger = LoggerFactory.getLogger(BeskyttetFilter.class);

    private static final String REALM = "ss-ctf-server";
    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        String authorization = containerRequestContext.getHeaderString("Authorization");

        if (authorization == null || authorization.isEmpty()) {
            containerRequestContext.abortWith(Response.status(UNAUTHORIZED).header("WWW-Authenticate",  "Basic realm=\"" + REALM + "\"").build());
        } else {
            logger.info("Got some basic auth: {} password: {}.", authorization, new String(Base64.getDecoder().decode(authorization.substring("Basic ".length()))));
        }
    }
}
