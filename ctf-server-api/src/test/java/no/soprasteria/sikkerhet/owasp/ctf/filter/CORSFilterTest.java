package no.soprasteria.sikkerhet.owasp.ctf.filter;

import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.core.MultivaluedHashMap;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CORSFilterTest {

    private CORSFilter filter;
    private ContainerRequestContext request;
    private ContainerResponseContext response;
    private MultivaluedHashMap<String, Object> headers;

    @Before
    public void oppsett() {
        request = mock(ContainerRequestContext.class);
        response = mock(ContainerResponseContext.class);
        headers = new MultivaluedHashMap<>();

        when(response.getHeaders()).thenReturn(headers);

        filter = new CORSFilter();
    }

    @Test
    public void skal_legge_paa_cors_headere() throws Exception {
        filter.filter(request, response);

        assertThat(headers).containsKeys("Access-Control-Allow-Credentials", "Access-Control-Allow-Headers" , "Access-Control-Allow-Methods", "Access-Control-Allow-Origin");
    }

    @Test
    public void skal_settes_access_control_allow_credentials() throws Exception {
        filter.filter(request, response);

        assertThat(headers).containsEntry("Access-Control-Allow-Credentials", Arrays.asList("true"));
    }

    @Test
    public void skal_settes_access_control_allow_headers() throws Exception {
        filter.filter(request, response);

        assertThat(headers).containsEntry("Access-Control-Allow-Headers", Arrays.asList("origin, content-type, accept, authorization, x-team-key"));
    }

    @Test
    public void skal_settes_access_control_allow_methods() throws Exception {
        filter.filter(request, response);

        assertThat(headers).containsEntry("Access-Control-Allow-Methods", Arrays.asList("GET, POST, PUT, DELETE, OPTIONS, HEAD"));
    }

    @Test
    public void skal_settes_access_control_allow_origin() throws Exception {
        filter.filter(request, response);

        assertThat(headers).containsEntry("Access-Control-Allow-Origin", Arrays.asList("*"));
    }
}