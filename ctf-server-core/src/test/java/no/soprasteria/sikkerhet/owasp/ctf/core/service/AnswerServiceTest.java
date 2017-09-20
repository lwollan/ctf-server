package no.soprasteria.sikkerhet.owasp.ctf.core.service;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AnswerServiceTest {

    private AnswerService service;
    private FlagService flagService;

    @Before
    public void oppsett() {
        flagService = new FlagService();
        service = new AnswerService(flagService);
    }

    @Test
    public void skal_ikke_legge_til_poeng_hvis_team_ikke_finnes() {
        assertThat(service.getTeamScore("finnes_ikke")).isEqualTo(0);
    }

    @Test
    public void hvis_team_finnes_skal_score_vaere_0_ved_start() {
        assertThat(service.getTeamScore("finnes")).isEqualTo(0);
    }

    @Test
    public void skal_legge_til_poeng_hvis_team_finnes() {
        String flagId01 = flagService.addFlag("01 Flag 1", "svar-a", 10l, "tips", "beskrivelse");
        String flagId02 = flagService.addFlag("02 Flag 2", "svar-b", 10l, "tips", "beskrivelse");

        service.answerFlag("finnes", flagId01);
        service.answerFlag("finnes", flagId02);

        assertThat(service.getTeamScore("finnes")).isEqualTo(20l);
    }

    @Test
    public void skal_nullstille_score_hvis_team_finnes() {
        service.answerFlag("finnes", "01 Flag 1");

        assertThat(service.getTeamScore("finnes")).isNotNull();

        service.resetTeamScore("finnes");

        assertThat(service.getTeamScore("finnes")).isEqualTo(0l);
    }

}