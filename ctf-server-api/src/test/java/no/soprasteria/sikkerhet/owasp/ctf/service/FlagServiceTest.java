package no.soprasteria.sikkerhet.owasp.ctf.service;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class FlagServiceTest {

    private FlagService service;

    @Before
    public void oppsett() {
        service = new FlagService();

    }

    @Test
    public void skal_kunne_legge_til_et_flag() {
        assertThat(service.addFlag("flag-name", "svar", 10l, "tips", "beskrivelse")).isNotNull();
    }

    @Test
    public void skal_finne_poeng_for_et_flag_som_er_lagt_til() {
        String flagId = service.addFlag("flag-name", "svar", 10l, "tips", "beskrivelse");

        assertThat(service.getPoints(flagId)).isEqualTo(10l);
    }

    @Test
    public void skal_ikke_finne_poeng_for_et_flag_som_ikke_er_lagt_til() {
        assertThat(service.getPoints("flag-id")).isEqualTo(0l);
    }

    @Test
    public void skal_vise_alle_flag_som_er_lagt_til() {
        service.addFlag("01 Flag 1", "svar-a", 10l, "tips", "beskrivelse");
        service.addFlag("02 Flag 2", "svar-b", 10l, "tips", "beskrivelse");

        assertThat(service.listFlag()).hasSize(2);
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
        String flagId01 = service.addFlag("01 Flag 1", "svar-a", 10l, "tips", "beskrivelse");
        String flagId02 = service.addFlag("02 Flag 2", "svar-b", 10l, "tips", "beskrivelse");

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