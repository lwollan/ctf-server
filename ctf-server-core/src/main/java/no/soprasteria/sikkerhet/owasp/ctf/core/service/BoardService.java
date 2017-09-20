package no.soprasteria.sikkerhet.owasp.ctf.core.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BoardService {

    private TeamService teamService;
    private AnswerService answerService;

    public BoardService(TeamService teamRepository, AnswerService scoreRepository) {
        this.teamService = teamRepository;
        this.answerService = scoreRepository;
    }

    public List<Map<String, String>> getScore() {
        return teamService.getTeamList().stream().map(teamName -> {
            Map<String, String> map = new HashMap<>();
            map.put("team", teamName);
            String teamKey = teamService.findTeamKeyByTeameName(teamName).orElse("");
            map.put("score", String.valueOf(answerService.getTeamScore(teamKey)));
            return map;
        } ).collect(Collectors.toList());
    }
}
