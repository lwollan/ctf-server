package no.soprasteria.sikkerhet.owasp.ctf.filter;


import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Response;
import java.io.IOException;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class TeamKeyFilterTest {

    private TeamKeyFilter filter;
    private ContainerRequestContext request;

    @Before
    public void oppsett() {
        filter = new TeamKeyFilter();
        request = mock(ContainerRequestContext.class);

    }

    @Test
    public void skal_gi_bad_request_hvis_team_key_mangler() throws IOException {
        when(request.getHeaderString(anyString())).thenReturn(null);

        filter.filter(request);

        verify(request).abortWith(any(Response.class));
    }

    @Test
    public void skal_lese_team_key_fra_header() throws IOException {
        when(request.getHeaderString(anyString())).thenReturn(null);

        filter.filter(request);

        verify(request).getHeaderString("X-TEAM-KEY");
    }
}