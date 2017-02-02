package no.soprasteria.sikerhet.owasp.ctf.service;

import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class TeamServiceTest {

    private TeamService service;

    @Before
    public void oppsett() {
        TeamRepository repo = new TeamRepository();
        service = new TeamService(repo);
    }

    @Test
    public void hvis_repoet_er_tomt___saa_er_lista_tom() {
        assertThat(service.getTeamList()).isEmpty();
    }

    @Test
    public void skal_lage_en_ny_teamkey__ved_registrering() {
        Optional<String> teamKey = service.addNewTeam("nytt-team");

        assertThat(teamKey).isPresent();
    }

    @Test
    public void team_som_er_lagt_til__skal_vaere_i_lista() {
        service.addNewTeam("nytt-team");

        assertThat(service.getTeamList()).contains("nytt-team");

    }
}