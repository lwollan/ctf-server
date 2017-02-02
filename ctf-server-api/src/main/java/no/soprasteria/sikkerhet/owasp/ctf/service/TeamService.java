package no.soprasteria.sikkerhet.owasp.ctf.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

public class TeamService {

    private static Logger logger = LoggerFactory.getLogger(TeamService.class);

    private TeamRepository teamRepository;
    private final long salt;


    public TeamService(TeamRepository teamRepository) throws NoSuchAlgorithmException {
        this.teamRepository = teamRepository;
        MessageDigest.getInstance("SHA-256");
        salt = new Random().nextLong();
    }

    public Optional<String> addNewTeam(String teamname) {
        String teamKey = newTeamKey(teamname, salt);
        if (teamRepository.get(teamKey) == null) {
            logger.info("Added new team {} with key {}", teamname, teamKey);
            teamRepository.add(teamKey, teamname);
            return Optional.ofNullable(teamKey);
        } else {
            logger.info("Team {} exisits.", teamname);
            return Optional.empty();
        }
    }

    static String newTeamKey(String teamname, Long salt) {
        try {
            byte[] digest = MessageDigest.getInstance("SHA-256").digest((teamname + salt).getBytes());
            return Base64.getEncoder().encodeToString(digest);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
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
