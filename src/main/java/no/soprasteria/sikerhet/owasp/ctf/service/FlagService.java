package no.soprasteria.sikerhet.owasp.ctf.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FlagService {

    private Map<String, Map<String, String>> map = new HashMap<>();

    public void addFlag(String id, String name, String svar, Long poeng) {
        Map<String, String> nyttFlagg = nyttFlagg(id, name, svar, poeng.toString());
        map.put(id, nyttFlagg);
    }

    public void delFlag(String flagId) {
        map.remove(flagId);
    }

    public boolean isCorrect(String flagId, String flag) {
        if (map.containsKey(flagId)) {
            Map<String, String> map = this.map.get(flagId);
            String svar = map.get("svar");
            return svar.equals(flag);
        } else {
            return false;
        }
    }

    public Long getPoints(String flagId) {
        if (map.containsKey(flagId)) {
            Map<String, String> map = this.map.get(flagId);
            String poeng = map.get("poeng");
            return Long.parseLong(poeng);
        } else {
            return 0l;
        }
    }

    public List<Map<String, String>> listFlag() {
        return new ArrayList<>(map.values());
    }

    static Map<String, String> nyttFlagg(String id, String name, String svar, String poeng) {
        Map<String, String> flag = new HashMap<>();
        flag.put("flag-id", id);
        flag.put("flag-name", name);
        flag.put("svar", svar);
        flag.put("poeng", poeng);
        return flag;
    }

}
