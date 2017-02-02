package no.soprasteria.sikkerhet.owasp.ctf.service;

import org.junit.Test;

import java.util.AbstractMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class BoardServiceTest {

    @Test
    public void skal_vise_tom_board_ved_oppstart() {
        BoardService boardService = new BoardService(new TeamRepository(), new ScoreRepository());
        assertThat(boardService.getScore()).isEmpty();
    }

    @Test
    public void skal_vise_alle_lag() {
        TeamRepository teamRepository = new TeamRepository();

        teamRepository.add("team 1 key", "team 1");
        teamRepository.add("team 2 key", "team 2");
        teamRepository.add("team 3 key", "team 3");
        BoardService boardService = new BoardService(teamRepository, new ScoreRepository());

        assertThat(boardService.getScore()).containsOnlyKeys("team 1" , "team 2", "team 3");
    }

    @Test
    public void skal_vise_poeng_for_lag() {
        TeamRepository teamRepository = new TeamRepository();
        ScoreRepository scoreRepository = new ScoreRepository();
        BoardService boardService = new BoardService(teamRepository, scoreRepository);

        teamRepository.add("team 1 key", "team 1");
        teamRepository.add("team 2 key", "team 2");
        teamRepository.add("team 3 key", "team 3");

        scoreRepository.put("team 1 key", 42l);
        scoreRepository.put("team 2 key", 1l);
        scoreRepository.put("team 3 key", 33);

        Map.Entry<String, Long> team1 = new AbstractMap.SimpleEntry<>("team 1", 42l);
        Map.Entry<String, Long> team2 = new AbstractMap.SimpleEntry<>("team 2", 1l);
        Map.Entry<String, Long> team3 = new AbstractMap.SimpleEntry<>("team 3", 33l);

        assertThat(boardService.getScore()).contains(team1, team2, team3);
    }
}