package no.soprasteria.sikkerhet.owasp.ctf.core.service;

import java.util.*;
import java.util.stream.Collectors;

public class AnswerService {

    private FlagService flagService;
    private Map<String, Set<Svar>> svar = new HashMap<>();


    public AnswerService(FlagService flagService) {
        this.flagService = flagService;
    }


    private Svar riktigSvar(String flagId, String svar) {
        return nyttSvar(flagId, svar, true);
    }

    private Svar feilSvar(String flagId, String svar) {
        return nyttSvar(flagId, svar, false);
    }

    private Svar nyttSvar(String flagId, String svar, boolean correct) {
        Svar s = new Svar();
        s.flagId = flagId;
        s.tidspunkt = System.currentTimeMillis();
        s.svar = svar;
        s.correct = correct;
        return s;
    }

    public boolean isFlagUnanswered(String teamKey, String flagId) {
        if (!svar.containsKey(teamKey)) {
            svar.put(teamKey, new HashSet<>());
        }

        return !svar.get(teamKey).contains(flagId);

    }

    public boolean giveAnswer(String teamKey, String flagId, String answer) {
        if (!svar.containsKey(teamKey)) {
            svar.put(teamKey, new HashSet<>());
        }

        if (svar.get(teamKey).stream()
                .filter(s -> s.flagId.equals(flagId) && s.correct)
                .collect(Collectors.toList()).isEmpty()) {
            boolean isCorrect = flagService.isCorrect(flagId, answer);
            svar.get(teamKey).add(nyttSvar(flagId, answer, isCorrect));
            return isCorrect;
        } else {
            return false;
        }
    }

    public Long getTeamScore(String teamKey) {
        if (svar.containsKey(teamKey)) {
            return svar.get(teamKey).stream()
                    .filter(svar -> svar.correct)
                    .map(svar -> flagService.getPoints(svar.flagId))
                    .mapToLong(l -> l).sum();
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

    public  Map<String, List<Svar>> getAnswersForTeam(String teamKey) {
        Map<String, List<Svar>> collect = svar.get(teamKey).stream()
                .collect(Collectors.groupingBy(Svar::getFlagId));
        return collect;
    }

    public Map<String, Map<String, List<Svar>>> getAnswersForTeams() {
        Map<String, Map<String, List<Svar>>> collect = svar.keySet().stream().map(teamKey -> {
            Map<String, List<Svar>> e = getAnswersForTeam(teamKey);
            return new AbstractMap.SimpleEntry<>(teamKey, e);
        }).collect(Collectors.toMap(key -> key.getKey(), value -> value.getValue()));
        return collect;
    }
}
