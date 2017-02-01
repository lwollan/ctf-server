package no.soprasteria.sikerhet.owasp.ctf.service;

public class ScoreService {

    private TeamRepository teamRepository;
    private ScoreRepository scoreRepository;

    public ScoreService(TeamRepository teamRepository, ScoreRepository scoreRepository) {
        this.teamRepository = teamRepository;
        this.scoreRepository = scoreRepository;
    }

    public void addPointsToTeam(String teamname, Long points) {
        if(teamRepository.get(teamname) != null) {
            scoreRepository.add(teamname, points);
        }
    }
}
