package no.soprasteria.sikkerhet.owasp.ctf.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@Provider
@Beskyttet
public class BeskyttetFilter implements ContainerRequestFilter {

    private static Logger logger = LoggerFactory.getLogger(BeskyttetFilter.class);

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        MultivaluedMap<String, String> headers = containerRequestContext.getHeaders();
        logger.info("TODO, sjekk auth. headers: {}", headers);
    }
}