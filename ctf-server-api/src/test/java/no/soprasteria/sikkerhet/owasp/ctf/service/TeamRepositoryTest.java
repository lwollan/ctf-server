package no.soprasteria.sikkerhet.owasp.ctf.service;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TeamRepositoryTest {

    @Test
    public void skal_kunne_hente_et_teamnavn_basert_paa_teamkey() {
        TeamRepository repo = new TeamRepository();
        repo.add("team-key", "team");

        assertThat(repo.get("team-key")).isEqualTo("team");
    }

    @Test
    public void skal_kunne_liste_alle_team_som_er_lagt_til() {
        TeamRepository repo = new TeamRepository();
        repo.add("team-key-1", "team-a");
        repo.add("team-key-2", "team-b");
        repo.add("team-key-3", "team-c");

        assertThat(repo.list()).containsKeys("team-key-1", "team-key-2", "team-key-3");
    }

}