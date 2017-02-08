package no.soprasteria.sikkerhet.owasp.ctf.service;

import no.soprasteria.sikkerhet.owasp.ctf.storage.HashMapRepository;
import no.soprasteria.sikkerhet.owasp.ctf.storage.Repository;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.String.valueOf;
import static org.assertj.core.api.Assertions.assertThat;

public class BoardServiceTest {

    @Test
    public void skal_vise_tom_board_ved_oppstart() {
        BoardService boardService = new BoardService(new HashMapRepository(), new HashMapRepository());
        assertThat(boardService.getScore()).isEmpty();
    }

    @Test
    public void skal_vise_alle_lag() {
        Repository teamRepository = new HashMapRepository();

        teamRepository.put("team 1 key", "team 1");
        teamRepository.put("team 2 key", "team 2");
        teamRepository.put("team 3 key", "team 3");
        BoardService boardService = new BoardService(teamRepository, new HashMapRepository());

        assertThat(boardService.getScore().get(0)).containsOnlyKeys("team" , "score");
        assertThat(boardService.getScore().get(1)).containsOnlyKeys("team" , "score");
        assertThat(boardService.getScore().get(2)).containsOnlyKeys("team" , "score");
    }

    @Test
    public void skal_vise_poeng_for_lag() {
        Repository teamRepository = new HashMapRepository();
        Repository scoreRepository = new HashMapRepository();
        BoardService boardService = new BoardService(teamRepository, scoreRepository);

        teamRepository.put("team 1 key", "team 1");
        teamRepository.put("team 2 key", "team 2");
        teamRepository.put("team 3 key", "team 3");

        scoreRepository.put("team 1 key", valueOf(42L));
        scoreRepository.put("team 2 key", valueOf(1L));
        scoreRepository.put("team 3 key", valueOf(33L));

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