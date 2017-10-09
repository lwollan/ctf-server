package no.soprasteria.sikkerhet.owasp.ctf.core.service;

import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;

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
        String flagId01 = lagNyttFlagForTest();
        String flagId02 = lagNyttFlagForTest();

        service.giveAnswer("finnes", flagId01, "riktig");
        service.giveAnswer("finnes", flagId02, "riktig");

        assertThat(service.getTeamScore("finnes")).isEqualTo(Long.valueOf(20));
    }

    @Test
    public void skal_nullstille_score_hvis_team_finnes() {
        service.giveAnswer("finnes", "01 Flag 1", "svar-a");

        assertThat(service.getTeamScore("finnes")).isNotNull();

        service.resetTeamScore("finnes");

        assertThat(service.getTeamScore("finnes")).isEqualTo(Long.valueOf(0));
    }

    @Test
    public void skal_vise_svar_for_ett_lag() throws Exception {
        String flagId01 = lagNyttFlagForTest();

        service.giveAnswer("finnes", flagId01, "riktig");

        Map<String, List<Svar>> answersForTeam = service.getAnswersForTeam("finnes");

        assertThat(answersForTeam).hasSize(1);
    }

    @Test
    public void skal_vise_svar_for_alle_flagg_for_ett_lag() throws Exception {
        String flagId01 = lagNyttFlagForTest();
        String flagId02 = lagNyttFlagForTest();

        service.giveAnswer("team", flagId01, "riktig");
        service.giveAnswer("team", flagId02, "riktig");

        Map<String, List<Svar>> answersForTeam = service.getAnswersForTeam("team");

        assertThat(answersForTeam).hasSize(2);
    }

    @Test
    public void skal_vise_alle_svar_for_alle_flagg_for_ett_lag() throws Exception {
        String flagId01 = lagNyttFlagForTest();
        String flagId02 = lagNyttFlagForTest();

        service.giveAnswer("team", flagId01, "feil1");
        service.giveAnswer("team", flagId01, "feil2");
        service.giveAnswer("team", flagId01, "riktig");

        service.giveAnswer("team", flagId02, "feil1");
        service.giveAnswer("team", flagId02, "riktig");

        Map<String, List<Svar>> answersForTeam = service.getAnswersForTeam("team");

        assertThat(answersForTeam.get(flagId01)).hasSize(3);
        assertThat(answersForTeam.get(flagId02)).hasSize(2);
    }

    @Test
    public void skal_bare_viser_svar_til_riktig_svar_er_gittfor_ett_lag() throws Exception {
        String flagId01 = lagNyttFlagForTest();

        service.giveAnswer("team", flagId01, "feil1");
        service.giveAnswer("team", flagId01, "feil2");
        service.giveAnswer("team", flagId01, "riktig");
        service.giveAnswer("team", flagId01, "feil2");
        service.giveAnswer("team", flagId01, "rikitg");

        Map<String, List<Svar>> answersForTeam = service.getAnswersForTeam("team");

        assertThat(answersForTeam.get(flagId01)).hasSize(3);
    }

    @Test
    public void skal_vise_score_for_alle_lag() throws Exception {
        String flagId01 = lagNyttFlagForTest();
        String flagId02 = lagNyttFlagForTest();

        service.giveAnswer("teamA", flagId01, "feil1");
        service.giveAnswer("teamA", flagId01, "rikitg");
        service.giveAnswer("teamA", flagId02, "rikitg");
        service.giveAnswer("teamB", flagId02, "feil");
        service.giveAnswer("teamB", flagId02, "rikitg");

        Map<String, Map<String, List<Svar>>> answers = service.getAnswersForTeams();

        assertThat(answers).isNotEmpty();

        assertThat(answers).containsOnlyKeys("teamA", "teamB");

        assertThat(answers.get("teamA")).containsOnlyKeys(flagId01, flagId02);
        assertThat(answers.get("teamB")).containsOnlyKeys(flagId02);
    }

    private String lagNyttFlagForTest() {
        return flagService.addFlag("Flagg 1", "riktig", Long.valueOf(10), "tips", "beskrivelse");
    }
}