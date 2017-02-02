package no.soprasteria.sikerhet.owasp.ctf.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@Provider
@Beskyttet
public class BeskyttetFilter implements ContainerRequestFilter {

    private static Logger logger = LoggerFactory.getLogger(BeskyttetFilter.class);

    @Context
    private HttpServletRequest request;

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        MultivaluedMap<String, String> headers = containerRequestContext.getHeaders();
        logger.info("TODO, sjekk auth. headers: {}", headers);
    }
}
