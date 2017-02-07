package no.soprasteria.sikkerhet.owasp.ctf.service;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BoardService {

    private TeamRepository teamRepository;
    private ScoreRepository scoreRepository;

    public BoardService(TeamRepository teamRepository, ScoreRepository scoreRepository) {
        this.teamRepository = teamRepository;
        this.scoreRepository = scoreRepository;
    }

    public List<Map<String, String>> getScore() {
        Map<String, String> teams = teamRepository.list();

        return teams.entrySet().stream().map(e -> {
            Long score = scoreRepository.get(e.getKey());
            Map<String, String> teamScore = new HashMap<>();
            teamScore.put("team", e.getValue());
            teamScore.put("score", Long.toString(score));
            return teamScore;
        }).collect(Collectors.toList());
    }
}
