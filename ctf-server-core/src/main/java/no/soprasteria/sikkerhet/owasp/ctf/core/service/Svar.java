package no.soprasteria.sikkerhet.owasp.ctf.core.service;

import java.time.LocalDateTime;

public class Svar {

    Long tidspunkt;
    String flagId;
    String svar;
    boolean correct;

    public Svar() {
    }

    public String getFlagId() {
        return flagId;
    }

    public Long getTidspunkt() {
        return tidspunkt;
    }

    public String getSvar() {
        return svar;
    }

    public boolean isCorrect() {
        return correct;
    }
}
