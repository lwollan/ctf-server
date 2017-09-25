package no.soprasteria.sikkerhet.owasp.ctf.api;

import no.soprasteria.sikkerhet.owasp.ctf.ApplicationContext;
import no.soprasteria.sikkerhet.owasp.ctf.CtFApplication;
import no.soprasteria.sikkerhet.owasp.ctf.api.requestparameters.TeamKeyParameter;
import no.soprasteria.sikkerhet.owasp.ctf.core.service.GameService;
import no.soprasteria.sikkerhet.owasp.ctf.filter.TeamKeyFilter;
import no.soprasteria.sikkerhet.owasp.ctf.core.service.FlagService;
import no.soprasteria.sikkerhet.owasp.ctf.core.service.TeamService;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.container.ContainerRequestContext;
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
    private ContainerRequestContext request;
    private String flag1Id;
    private String flag2Id;
    private String newTeamKey;

    private Map<String, String> svarMedRiktigFlag, svarMedFeilFlag, svarMedUkjentFlagId;

    @Before
    public void oppsett() throws Exception {
        resource = new FlagResource();
        application = new CtFApplication();

        TestSetup.setupTestGame(application);

        flagService = ApplicationContext.get(application, FlagService.class);
        teamService = ApplicationContext.get(application, TeamService.class);


        flag1Id = flagService.addFlag("flag 1", "svar 1", 10L, "tips", "beskrivelse");
        flag2Id = flagService.addFlag("flag 2", "svar 2", 10L, "tips", "beskrivelse");

        newTeamKey = teamService.addNewTeam("team-a").get();

        svarMedRiktigFlag = nyttFlagSvar(flag1Id, "svar 1");
        svarMedFeilFlag = nyttFlagSvar(flag1Id, "svar 2");
        svarMedUkjentFlagId = nyttFlagSvar("flag-ukjent", "riktig svar");

        request = mock(ContainerRequestContext.class);
        when(request.getHeaderString(TeamKeyFilter.X_TEAM_KEY)).thenReturn(newTeamKey);

        GameService gameService = ApplicationContext.get(application, GameService.class);
        gameService.startGame();

    }

    @Test
    public void skal_returnere_alle_registrerte_flagg() {
        List<Map<FlagResource.FlagResourceResponseKeys, String>> list = resource.list(application, TeamKeyHeaderParameter.from(newTeamKey));
        assertThat(list).isNotEmpty();
    }

    @Test
    public void skal_gi_riktig_response___hvis_svar_er_riktig() {
        Response response = resource.answer(application, request, svarMedRiktigFlag);

        assertThat(response.getStatus()).isEqualTo(202);
    }

    @Test
    public void skal_gi_bad_request___hvis_svar_er_feil() {
        Response response = resource.answer(application, request, svarMedFeilFlag);

        assertThat(response.getStatus()).isEqualTo(400);
    }

    @Test
    public void skal_gi_bad_request___hvis_flag_id_er_ukjent() {
        Response response = resource.answer(application, request, svarMedUkjentFlagId);

        assertThat(response.getStatus()).isEqualTo(400);
    }

    @Test
    public void skal_ikke_tillate_to_riktige_svar_paa_samme_flag_fra_samme_lag() {
        Response response1 = resource.answer(application, request, svarMedRiktigFlag);
        assertThat(response1.getStatus()).isEqualTo(202);
        Response response2 = resource.answer(application, request, svarMedRiktigFlag);
        assertThat(response2.getStatus()).isNotEqualTo(202);
    }

    @Test
    public void skal_si_om_et_flag_er_besvart_eller_ikke() {
        resource.answer(application, request, svarMedRiktigFlag);
        List<Map<FlagResource.FlagResourceResponseKeys, String>> list = resource.list(application, TeamKeyHeaderParameter.from(newTeamKey));

        assertThat(list.stream().filter(m -> m.get(FlagResource.FlagResourceResponseKeys.flagId).equals(svarMedRiktigFlag.get("flagId")))).isNotEmpty();
    }

    private static Map<String, String> nyttFlagSvar(String value, String value2) {
        Map<String, String> answer = new HashMap<>();
        answer.put(FlagResource.FlagResourceResponseKeys.flagId.toString(), value);
        answer.put(FlagResource.FlagResourceResponseKeys.flag.toString(), value2);
        return answer;
    }

}