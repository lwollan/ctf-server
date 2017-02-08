package no.soprasteria.sikkerhet.owasp.ctf.service;

import no.soprasteria.sikkerhet.owasp.ctf.storage.ScoreRepository;
import no.soprasteria.sikkerhet.owasp.ctf.storage.TeamRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ScoreService {

    private static Logger logger = LoggerFactory.getLogger(ScoreService.class);

    private TeamRepository teamRepository;
    private ScoreRepository scoreRepository;

    public ScoreService(TeamRepository teamRepository, ScoreRepository scoreRepository) {
        this.teamRepository = teamRepository;
        this.scoreRepository = scoreRepository;
    }

    public void addPointsToTeam(String teamKey, Long points) {
        if(teamExists(teamKey)) {
            String teamName = teamRepository.get(teamKey).get();
            Long currentScore = scoreRepository.get(teamKey).orElse(0l);
            Long newScore = currentScore + points;
            scoreRepository.put(teamKey, newScore);
            logger.info("Team {} scored points. Score was {}, now {}", teamName, currentScore, newScore);
        } else {
            logger.warn("Unknown team {}", teamKey);
        }
    }

    public Long getTeamScore(String teamKey) {
        if (teamExists(teamKey)) {
            return scoreRepository.get(teamKey).orElse(0l);
        } else {
            return null;
        }
    }

    public void resetTeamScore(String teamKey) {
        if (teamExists(teamKey)) {
            scoreRepository.put(teamKey, 0L);
            logger.info("Team {} score was reset.", teamKey);
        }
    }

    public void delPointsFromTeam(String teamKey, Long points) {
        if(teamExists(teamKey)) {
            Long currentScore = scoreRepository.get(teamKey).orElse(0l);
            Long newScore = currentScore + points;
            scoreRepository.put(teamKey, newScore);
            logger.info("Team {} lost points. Score was {}, now {}", teamKey, currentScore, newScore);
        }
    }

    private boolean teamExists(String teamname) {
        return teamRepository.get(teamname).isPresent();
    }
}
