package no.soprasteria.sikkerhet.owasp.ctf.service;

import no.soprasteria.sikkerhet.owasp.ctf.storage.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BoardService {

    private Repository teamRepository;
    private Repository scoreRepository;

    public BoardService(Repository teamRepository, Repository scoreRepository) {
        this.teamRepository = teamRepository;
        this.scoreRepository = scoreRepository;
    }

    public List<Map<String, String>> getScore() {
        Map<String, String> teams = teamRepository.list();

        return teams.entrySet().stream().map(e -> {
            String score = scoreRepository.get(e.getKey()).orElse("0");
            Map<String, String> teamScore = new HashMap<>();
            teamScore.put("team", e.getValue());
            teamScore.put("score", score);
            return teamScore;
        }).collect(Collectors.toList());
    }
}
