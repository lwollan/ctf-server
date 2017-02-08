package no.soprasteria.sikkerhet.owasp.ctf.service;

import no.soprasteria.sikkerhet.owasp.ctf.storage.TeamRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class TeamServiceTest {

    private TeamService service;

    @Before
    public void oppsett() throws Exception {
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

    @Test
    public void skal_lage_ulike_teamkey_for_to_team() {
        assertThat(TeamService.newTeamKey("teama", 0l)).isNotEqualTo(TeamService.newTeamKey("teamb", 0L));
    }

    @Test
    public void skal_lage_samme_teamkey_for_samme_team_og_samme_salt() {
        assertThat(TeamService.newTeamKey("teama", 0l)).isEqualTo(TeamService.newTeamKey("teama", 0L));
    }

    @Test
    public void skal_lage_samme_forskjellig_teamkey_for_samme_team_og_ulike_salt() {
        assertThat(TeamService.newTeamKey("teama", 0l)).isNotEqualTo(TeamService.newTeamKey("teama", 1L));
    }
}