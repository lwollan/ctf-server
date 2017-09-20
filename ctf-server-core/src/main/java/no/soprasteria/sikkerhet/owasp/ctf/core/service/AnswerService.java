package no.soprasteria.sikkerhet.owasp.ctf.core.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class AnswerService {

    private FlagService flagService;

    public AnswerService(FlagService flagService) {
        this.flagService = flagService;
    }

    private Map<String, Set<String>> svar = new HashMap<>();

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

    public Long getTeamScore(String teamKey) {
        if (svar.containsKey(teamKey)) {
            return svar.get(teamKey).stream().map(flagService::getPoints).mapToLong(l -> l).sum();
        } else {
            return 0L;
        }
    }

    public void resetTeamScore(String teamKey) {
        svar.put(teamKey, new HashSet<>());
    }

    public void deleteTeamScore(String teamKey) {
        svar.remove(teamKey);
    }

}
