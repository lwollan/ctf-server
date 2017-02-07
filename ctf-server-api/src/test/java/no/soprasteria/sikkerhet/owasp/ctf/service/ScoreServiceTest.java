package no.soprasteria.sikkerhet.owasp.ctf.service;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ScoreServiceTest {

    private TeamRepository teamRepository;
    private ScoreRepository scoreRepository;
    private ScoreService service;

    @Before
    public void oppsett() {
        teamRepository = mock(TeamRepository.class);
        scoreRepository = new ScoreRepository();
        service = new ScoreService(teamRepository, scoreRepository);

        when(teamRepository.get("finnes_ikke")).thenReturn(null);
        when(teamRepository.get("finnes")).thenReturn("finnes");

    }

    @Test
    public void skal_ikke_legge_til_poeng_hvis_team_ikke_finnes() {
        service.addPointsToTeam("finnes_ikke", 1000l);

        assertThat(service.getTeamScore("finnes_ikke")).isNull();
    }

    @Test
    public void hvis_team_finnes_skal_score_vaere_0_ved_start() {
        assertThat(service.getTeamScore("finnes")).isEqualTo(0);
    }

    @Test
    public void skal_legge_til_poeng_hvis_team_finnes() {
        service.addPointsToTeam("finnes", 49151l);
        service.addPointsToTeam("finnes", 1l);

        assertThat(service.getTeamScore("finnes")).isEqualTo(49152l);
    }

    @Test
    public void skal_nullstille_score_hvis_team_finnes() {
        service.addPointsToTeam("finnes", 1l);

        assertThat(service.getTeamScore("finnes")).isNotNull();

        service.resetTeamScore("finnes");

        assertThat(service.getTeamScore("finnes")).isEqualTo(0l);
    }

    @Test
    public void skal_ikke_gjoere_saa_mye_hvis_team_ikke_finnes() {
        service.resetTeamScore("finnes_ikke");

        assertThat(service.getTeamScore("finnes_ikke")).isNull();
    }
}