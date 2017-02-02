package no.soprasteria.sikerhet.owasp.ctf.api;

import no.soprasteria.sikerhet.owasp.ctf.ApplicationContext;
import no.soprasteria.sikerhet.owasp.ctf.CtFApplication;
import no.soprasteria.sikerhet.owasp.ctf.filter.TeamKeyFilter;
import no.soprasteria.sikerhet.owasp.ctf.service.FlagService;
import no.soprasteria.sikerhet.owasp.ctf.service.TeamService;
import org.glassfish.jersey.server.ContainerRequest;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FlagResourceTest {

    private FlagResource resource;
    private Application application;
    private FlagService flagService;
    private TeamService teamService;
    private String newTeamKey;
    private ContainerRequest request;

    @Before
    public void oppsett() {
        resource = new FlagResource();
        application = new CtFApplication();
        flagService = ApplicationContext.get(application, FlagService.class);
        teamService = ApplicationContext.get(application, TeamService.class);

        flagService.addFlag("flag-01", "flag 1" ,"svar 1", 10l);
        flagService.addFlag("flag-02", "flag 2" ,"svar 2", 10l);

        String newTeamKey = teamService.addNewTeam("team-a").get();

        request = mock(ContainerRequest.class);
        when(request.getHeaderString(TeamKeyFilter.X_TEAM_KEY)).thenReturn(newTeamKey);
    }

    @Test
    public void skal_returnere_alle_registrerte_flagg() {
        List<Map<String, String>> list = resource.list(application);

        assertThat(list).hasSize(2);
    }

    @Test
    public void skal_gi_riktig_response___hvis_svar_er_riktig() {
        Map<String, String> svar = nyttFlagSvar("flag-01", "svar 1");

        Response response = resource.answer(application, request, svar);

        assertThat(response.getStatus()).isEqualTo(202);
    }

    @Test
    public void skal_gi_bad_request___hvis_svar_er_feil() {
        Map<String, String> answer = nyttFlagSvar("flag-01", "svar 2");

        Response response = resource.answer(application, request, answer);

        assertThat(response.getStatus()).isEqualTo(400);
    }

    @Test
    public void skal_gi_bad_request___hvis_flag_id_er_ukjent() {
        Map<String, String> answer = nyttFlagSvar("flag-ukjent", "riktig svar");

        Response response = resource.answer(application, request, answer);

        assertThat(response.getStatus()).isEqualTo(400);
    }

    private static Map<String, String> nyttFlagSvar(String value, String value2) {
        Map<String, String> answer = new HashMap<>();
        answer.put(FlagResource.Answer.flagId.toString(), value);
        answer.put(FlagResource.Answer.answer.toString(), value2);
        return answer;
    }


}