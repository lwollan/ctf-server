package no.soprasteria.sikkerhet.owasp.ctf.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BoardService {

    private TeamService teamService;
    private FlagService flagService;

    public BoardService(TeamService teamRepository, FlagService scoreRepository) {
        this.teamService = teamRepository;
        this.flagService = scoreRepository;
    }

    public List<Map<String, String>> getScore() {
        return teamService.getTeamList().stream().map(teamName -> {
            Map<String, String> map = new HashMap<>();
            map.put("team", teamName);
            String teamKey = teamService.findTeamKeyByTeameName(teamName).orElse("");
            map.put("score", String.valueOf(flagService.getTeamScore(teamKey)));
            return map;
        } ).collect(Collectors.toList());
    }
}
