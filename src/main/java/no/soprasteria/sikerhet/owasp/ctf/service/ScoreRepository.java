package no.soprasteria.sikerhet.owasp.ctf.service;

import java.util.HashMap;
import java.util.Map;

public class ScoreRepository {

    private Map<String, Long> map;

    public ScoreRepository() {
        map = new HashMap<>();
    }

    public long add(String teamname, long points) {
        Long currentScore = map.getOrDefault(teamname, 0l);
        long newScore = currentScore + points;
        map.put(teamname, newScore);
        return newScore;
    }

    public void reset(String teamname) {
        map.put(teamname, 0l);
    }

    public Map<String, Long> list() {
        HashMap<String, Long> map = new HashMap<>();
        map.putAll(this.map);
        return map;
    }
}
