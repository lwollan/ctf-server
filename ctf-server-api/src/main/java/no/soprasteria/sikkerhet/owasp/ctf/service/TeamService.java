package no.soprasteria.sikkerhet.owasp.ctf.service;

import no.soprasteria.sikkerhet.owasp.ctf.storage.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.stream.Collectors;

public class TeamService {

    private static Logger logger = LoggerFactory.getLogger(TeamService.class);

    private Repository teamRepository;
    final long salt;


    public TeamService(Repository teamRepository) throws NoSuchAlgorithmException {
        this.teamRepository = teamRepository;
        MessageDigest.getInstance("SHA-256");
        salt = new Random().nextLong();
    }

    public Optional<String> addNewTeam(String teamname) {
        String teamKey = newTeamKey(teamname, salt);
        if (!teamRepository.get(teamKey).isPresent()) {
            logger.info("Added new team {} with key {}", teamname, teamKey);
            teamRepository.put(teamKey, teamname);
            return Optional.ofNullable(teamKey);
        } else {
            logger.info("Team {} exisits.", teamname);
            return Optional.empty();
        }
    }

    public boolean deleteTeam(String teamKey) {
        if (teamRepository.get(teamKey).isPresent()) {
            teamRepository.remove(teamKey);
            return true;
        } else {
            return false;
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
        return teamRepository.get(teamKey);
    }

    public Optional<String> findTeamKeyByTeameName(String teamName) {
        return teamRepository.list().entrySet().stream()
                .filter(e -> e.getValue().equals(teamName))
                .map(Map.Entry::getKey).findFirst();
    }
}
