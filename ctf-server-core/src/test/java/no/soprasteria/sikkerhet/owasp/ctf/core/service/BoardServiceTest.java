package no.soprasteria.sikkerhet.owasp.ctf.core.service;

import no.soprasteria.sikkerhet.owasp.ctf.core.storage.HashMapRepository;
import org.junit.Test;
import org.mockito.stubbing.Answer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class BoardServiceTest {

    @Test
    public void skal_vise_tom_board_ved_oppstart() throws Exception {
        BoardService boardService = new BoardService(new TeamService(new HashMapRepository()), new AnswerService(new FlagService()));
        assertThat(boardService.getScore()).isEmpty();
    }

    @Test
    public void skal_vise_alle_lag() throws Exception {
        TeamService teamRepository = new TeamService(new HashMapRepository());

        teamRepository.addNewTeam("team 1");
        teamRepository.addNewTeam("team 2");
        teamRepository.addNewTeam("team 3");
        BoardService boardService = new BoardService(teamRepository, new AnswerService(new FlagService()));

        assertThat(boardService.getScore().get(0)).containsOnlyKeys("team" , "score");
        assertThat(boardService.getScore().get(1)).containsOnlyKeys("team" , "score");
        assertThat(boardService.getScore().get(2)).containsOnlyKeys("team" , "score");
    }

    @Test
    public void skal_vise_poeng_for_lag() throws Exception {
        TeamService teamRepository = new TeamService(new HashMapRepository());
        FlagService flagService = new FlagService();
        AnswerService answerService = new AnswerService(flagService);

        Optional<String> team01 = teamRepository.addNewTeam("team 1");
        Optional<String> team02 = teamRepository.addNewTeam("team 2");
        Optional<String> team03 = teamRepository.addNewTeam("team 3");

        BoardService boardService = new BoardService(teamRepository, answerService);

        String flag01 = flagService.addFlag("", "", 42L, "", "beskrivelse");
        String flag02 = flagService.addFlag("", "", 1L, "", "beskrivelse");
        String flag03 = flagService.addFlag("", "", 33L, "", "beskrivelse");

        answerService.answerFlag(team01.get(), flag01);
        answerService.answerFlag(team02.get(), flag02);
        answerService.answerFlag(team03.get(), flag03);

        Map<String, String> team1score = lagScore("team 1", "42");
        Map<String, String> team2score = lagScore("team 2", "1");
        Map<String, String> team3score = lagScore("team 3", "33");

        List<Map<String, String>> score = boardService.getScore();
        assertThat(score).contains(team1score);
        assertThat(score).contains(team2score);
        assertThat(score).contains(team3score);
    }

    private static Map<String, String> lagScore(String team, String score) {
        Map<String, String> team1score = new HashMap<>();
        team1score.put("team", team);
        team1score.put("score", score);
        return team1score;
    }
}