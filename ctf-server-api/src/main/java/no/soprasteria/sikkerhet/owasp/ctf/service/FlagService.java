package no.soprasteria.sikkerhet.owasp.ctf.service;

import java.util.*;

public class FlagService {

    private Map<String, Map<String, String>> flags = new HashMap<>();
    private Map<String, Set<String>> svar = new HashMap<>();


    public String addFlag(String name, String svar, Long poeng, String tips) {
        String id = UUID.randomUUID().toString();
        Map<String, String> nyttFlagg = nyttFlagg(id, name, svar, poeng.toString(), tips);
        flags.put(id, nyttFlagg);
        return id;
    }

    public void delFlag(String flagId) {
        flags.remove(flagId);
    }

    public boolean isCorrect(String flagId, String flag) {
        if (flags.containsKey(flagId)) {
            Map<String, String> map = this.flags.get(flagId);
            String svar = map.get("svar");
            return svar.equals(flag);
        } else {
            return false;
        }
    }

    public Long getPoints(String flagId) {
        if (flags.containsKey(flagId)) {
            Map<String, String> map = this.flags.get(flagId);
            String poeng = map.get("poeng");
            return Long.parseLong(poeng);
        } else {
            return 0l;
        }
    }

    public String getTip(String flagId) {
        if (flags.containsKey(flagId)) {
            Map<String, String> map = this.flags.get(flagId);
            return  map.get("tip");
        } else {
            return null;
        }
    }

    public List<Map<String, String>> listFlag() {
        return new ArrayList<>(flags.values());
    }


    public boolean isFlagUnanswered(String teamKey, String flagId) {
        if (!svar.containsKey(teamKey)) {
            svar.put(teamKey, new HashSet<>());
        }

        return !svar.get(teamKey).contains(flagId);

    }

    public void answerFlag(String teamKey, String flagId) {
        if (!svar.containsKey(teamKey)) {
            svar.put(teamKey, new HashSet<>());
        }

        svar.get(teamKey).add(flagId);
    }


    static Map<String, String> nyttFlagg(String id, String name, String svar, String poeng, String tips) {
        Map<String, String> flag = new HashMap<>();
        flag.put("flag-id", id);
        flag.put("flag-name", name);
        flag.put("tips", tips);
        flag.put("svar", svar);
        flag.put("poeng", poeng);
        return flag;
    }

}
