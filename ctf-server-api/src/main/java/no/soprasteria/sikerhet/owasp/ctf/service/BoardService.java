package no.soprasteria.sikerhet.owasp.ctf.service;

import java.util.AbstractMap;
import java.util.Map;
import java.util.stream.Collectors;

public class BoardService {

    private TeamRepository teamRepository;
    private ScoreRepository scoreRepository;

    public BoardService(TeamRepository teamRepository, ScoreRepository scoreRepository) {
        this.teamRepository = teamRepository;
        this.scoreRepository = scoreRepository;
    }

    public Map<String, Long> getScore() {
        Map<String, String> teams = teamRepository.list();

        return teams.entrySet().stream().map(e -> {
            Long score = scoreRepository.get(e.getKey());
            return new AbstractMap.SimpleEntry<>(e.getValue(), score);
        }).collect(Collectors.toMap(AbstractMap.SimpleEntry::getKey, AbstractMap.SimpleEntry::getValue));

    }
}
