package no.soprasteria.sikkerhet.owasp.ctf.filter;


import no.soprasteria.sikkerhet.owasp.ctf.ApplicationContext;
import no.soprasteria.sikkerhet.owasp.ctf.CtFApplication;
import no.soprasteria.sikkerhet.owasp.ctf.service.TeamService;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.Optional;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class TeamKeyFilterTest {

    private TeamKeyFilter filter;
    private ContainerRequestContext request;
    private Optional<String> teamKey;

    @Before
    public void oppsett() throws Exception {
        filter = new TeamKeyFilter();
        request = mock(ContainerRequestContext.class);
        filter.application = new CtFApplication();

        TeamService teamService = ApplicationContext.get(filter.application, TeamService.class);
        teamKey = teamService.addNewTeam("team-a");

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

    @Test
    public void skal_godta_teamkey_som_finnes_i_teamservice() throws IOException {
        when(request.getHeaderString(anyString())).thenReturn(teamKey.get());

        filter.filter(request);

        verify(request, never()).abortWith(any(Response.class));
    }

    @Test
    public void skal_ikke_godta_teamkey_som_ikke_finnes_i_teamservice() throws IOException {
        when(request.getHeaderString(anyString())).thenReturn("some-other-key");

        filter.filter(request);

        verify(request).abortWith(any(Response.class));
    }
}