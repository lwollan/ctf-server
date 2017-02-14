package no.soprasteria.sikkerhet.owasp.ctf.filter;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Stream.of;

@Provider
public class CORSFilter implements ContainerResponseFilter {

    private static final String ALLOWED_HEADERS = of("origin", "content-type", "accept", "authorization", "x-team-key").collect(joining(", "));
    private static final String ALLOWED_METHODS = of("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD").collect(joining(", "));

    @Override
    public void filter(ContainerRequestContext containerRequestContext, ContainerResponseContext containerResponseContext) throws IOException {
        containerResponseContext.getHeaders().add("Access-Control-Allow-Origin", "*");
        containerResponseContext.getHeaders().add("Access-Control-Allow-Headers", ALLOWED_HEADERS);
        containerResponseContext.getHeaders().add("Access-Control-Allow-Credentials", "true");
        containerResponseContext.getHeaders().add("Access-Control-Allow-Methods", ALLOWED_METHODS);
    }
}
