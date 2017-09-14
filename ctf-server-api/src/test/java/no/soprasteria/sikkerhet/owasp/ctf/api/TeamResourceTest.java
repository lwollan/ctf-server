package no.soprasteria.sikkerhet.owasp.ctf.api;

import no.soprasteria.sikkerhet.owasp.ctf.CtFApplication;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class TeamResourceTest {

    private TeamResource resource;
    private CtFApplication application;

    @Before
    public void oppsett() throws Exception {
        resource = new TeamResource();
        application = new CtFApplication();
        TestSetup.setupTestGame(application);
    }

    @Test
    public void team_listen_skal_vare_tom_ved_oppstart() {
        List<Map<TeamResource.TeamResourceResponseKeys, String>> teamListe = resource.list(application);
        assertThat(teamListe).isEmpty();
    }

    @Test
    public void skal_vise_alle_registrerte_team() {
        resource.add(application, "0xDEADBEEF");
        resource.add(application, "SYS64764");
        List<Map<TeamResource.TeamResourceResponseKeys, String>> teamListe = resource.list(application);

        assertThat(teamListe).hasSize(2);
    }

    @Test
    public void det_skal_ikke_vaere_mulig_aa_legge_til_to_like_team() throws Exception {
        resource.add(application, "0xDEADBEEF");
        List<Map<TeamResource.TeamResourceResponseKeys, String>> teamListe = resource.list(application);

        assertThat(teamListe).hasSize(1);
    }

    @Test
    public void skal_slette_baade_team_og_poengsummer() {
        resource.add(application, "0xDEADBEEF");
        Response response = resource.del(application, "0xDEADBEEF");
        assertThat(response.getStatus()).isEqualTo(200);

    }

}