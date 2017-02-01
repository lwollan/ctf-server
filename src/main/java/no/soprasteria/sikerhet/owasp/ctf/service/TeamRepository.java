package no.soprasteria.sikerhet.owasp.ctf.service;

import java.util.HashMap;
import java.util.Map;

public class TeamRepository {

    private Map<String, String> map;

    public TeamRepository() {
        map = new HashMap<>();
    }

    public boolean add(String teamname, String teamSecret) {
        if (map.containsKey(teamname)) {
            return false;
        } else {
            map.put(teamname, teamSecret);
            return true;
        }
    }

    public Map<String, String> list() {
        HashMap<String, String> map = new HashMap<>();
        map.putAll(this.map);
        return map;
    }

    public String get(String teamname) {
        if (map.containsKey(teamname)) {
            return teamname;
        } else {
            return null;
        }
    }
}
