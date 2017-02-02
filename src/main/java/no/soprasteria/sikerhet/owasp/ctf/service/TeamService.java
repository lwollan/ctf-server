package no.soprasteria.sikerhet.owasp.ctf.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TeamService {

    private static Logger logger = LoggerFactory.getLogger(TeamService.class);

    private TeamRepository teamRepository;

    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public Optional<String> addNewTeam(String teamname) {
        if (teamRepository.get(teamname) == null) {
            logger.info("Added new team {}", teamname);
            String teamKey = Base64.getEncoder().encodeToString(teamname.getBytes());
            teamRepository.add(teamKey, teamname);
            return Optional.ofNullable(teamKey);
        } else {
            logger.info("Team {} exisits.", teamname);
            return Optional.empty();
        }
    }

    public List<String> getTeamList() {
        return teamRepository.list().values().stream().collect(Collectors.toList());
    }

    public Optional<String> getTeamName(String teamKey) {
        String teamName = teamRepository.get(teamKey);
        return Optional.ofNullable(teamName);
    }
}
