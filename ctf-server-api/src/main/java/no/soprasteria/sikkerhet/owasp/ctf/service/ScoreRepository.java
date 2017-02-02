package no.soprasteria.sikkerhet.owasp.ctf.service;

import java.util.HashMap;
import java.util.Map;

public class ScoreRepository {

    private Map<String, Long> map;

    public ScoreRepository() {
        map = new HashMap<>();
    }

    public void put(String teamKey, long score) {
        map.put(teamKey, score);
    }

    public Long get(String teamKey) {
        return map.getOrDefault(teamKey, 0l);
    }

    public Map<String, Long> list() {
        HashMap<String, Long> map = new HashMap<>();
        map.putAll(this.map);
        return map;
    }

}
