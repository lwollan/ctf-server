package no.soprasteria.sikkerhet.owasp.ctf.service;

import no.soprasteria.sikkerhet.owasp.ctf.storage.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ScoreService {

    private static Logger logger = LoggerFactory.getLogger(ScoreService.class);

    private Repository teamRepository;
    private Repository scoreRepository;

    public ScoreService(Repository teamRepository, Repository scoreRepository) {
        this.teamRepository = teamRepository;
        this.scoreRepository = scoreRepository;
    }

    public void addPointsToTeam(String teamKey, Long points) {
        if(teamExists(teamKey)) {
            String teamName = teamRepository.get(teamKey).get();
            Long currentScore = Long.parseLong(scoreRepository.get(teamKey).orElse(String.valueOf(0l)));
            Long newScore = currentScore + points;
            scoreRepository.put(teamKey, String.valueOf(newScore));
            logger.info("Team {} scored points. Score was {}, now {}", teamName, currentScore, newScore);
        } else {
            logger.warn("Unknown team {}", teamKey);
        }
    }

    public Long getTeamScore(String teamKey) {
        if (teamExists(teamKey)) {
            return Long.parseLong(scoreRepository.get(teamKey).orElse(String.valueOf(0)));
        } else {
            return null;
        }
    }

    public void resetTeamScore(String teamKey) {
        if (teamExists(teamKey)) {
            scoreRepository.put(teamKey, String.valueOf(0l));
            logger.info("Team {} score was reset.", teamKey);
        }
    }

    private boolean teamExists(String teamname) {
        return teamRepository.get(teamname).isPresent();
    }
}
