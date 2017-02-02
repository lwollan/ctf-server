package no.soprasteria.sikerhet.owasp.ctf.service;

import java.util.HashMap;
import java.util.Map;

public class TeamRepository {

    private Map<String, String> map;

    public TeamRepository() {
        map = new HashMap<>();
    }

    public void add(String teamKey, String teamname) {
        map.put(teamKey, teamname);
    }

    public Map<String, String> list() {
        HashMap<String, String> map = new HashMap<>();
        map.putAll(this.map);
        return map;
    }

    public String get(String teamKey) {
        if (map.containsKey(teamKey)) {
            return map.get(teamKey);
        } else {
            return null;
        }
    }
}
