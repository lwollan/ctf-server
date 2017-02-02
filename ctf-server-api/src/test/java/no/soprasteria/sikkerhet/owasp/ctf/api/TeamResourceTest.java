package no.soprasteria.sikkerhet.owasp.ctf.api;

import no.soprasteria.sikkerhet.owasp.ctf.CtFApplication;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TeamResourceTest {

    private TeamResource resource;
    private CtFApplication application;

    @Before
    public void oppsett() throws Exception {
        resource = new TeamResource();
        application = new CtFApplication();
    }

    @Test
    public void team_listen_skal_vare_tom_ved_oppstart() {
        List<String> teamListe = resource.list(application);
        assertThat(teamListe).isEmpty();
    }

    @Test
    public void skal_vise_alle_registrerte_team() {
        resource.add(application, "0xDEADBEEF");
        resource.add(application, "SYS64764");
        List<String> teamListe = resource.list(application);

        assertThat(teamListe).hasSize(2);
    }

    @Test
    public void det_skal_ikke_vaere_mulig_aa_legge_til_to_like_team() throws Exception {
        resource.add(application, "0xDEADBEEF");
        List<String> teamListe = resource.list(application);

        assertThat(teamListe).hasSize(1);
    }
}