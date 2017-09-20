package no.soprasteria.sikkerhet.owasp.ctf.core.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class AnswerService {

    private FlagService flagService;

    public AnswerService(FlagService flagService) {
        this.flagService = flagService;
    }

    private class Svar {
        LocalDateTime tidspunkt;
        String flagId;
        String svar;
        boolean correct;
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
        s.tidspunkt = LocalDateTime.now();
        s.svar = svar;
        s.correct = correct;
        return s;
    }

    private Map<String, Set<Svar>> svar = new HashMap<>();

    public boolean isFlagUnanswered(String teamKey, String flagId) {
        if (!svar.containsKey(teamKey)) {
            svar.put(teamKey, new HashSet<>());
        }

        return !svar.get(teamKey).contains(flagId);

    }

    public boolean answerFlag(String teamKey, String flagId, String answer) {
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

}
