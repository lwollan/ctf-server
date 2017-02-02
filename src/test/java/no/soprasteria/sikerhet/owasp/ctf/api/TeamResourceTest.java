package no.soprasteria.sikerhet.owasp.ctf.api;

import no.soprasteria.sikerhet.owasp.ctf.CtFApplication;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TeamResourceTest {

    @Test
    public void team_listen_skal_vare_tom_ved_oppstart() throws Exception {
        TeamResource resource = new TeamResource();
        List<String> teamListe = resource.list(new CtFApplication());
        assertThat(teamListe).isEmpty();
    }

    @Test
    public void skal_vise_alle_registrerte_team() throws Exception {
        TeamResource resource = new TeamResource();
        CtFApplication application = new CtFApplication();
        resource.add(application, "0xDEADBEEF");
        List<String> teamListe = resource.list(application);
        assertThat(teamListe).hasSize(1);
    }
}