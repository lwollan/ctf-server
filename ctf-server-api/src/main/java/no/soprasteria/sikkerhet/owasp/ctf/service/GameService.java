package no.soprasteria.sikkerhet.owasp.ctf.service;

import java.util.Base64;

public class GameService {

    private boolean gameOn = false;

    public boolean isGameOn() {
        return gameOn;
    }

    public void startGame() {
        gameOn = true;
    }

    public void pauseGame() {
        gameOn = false;
    }

    public String newGame(String gameName) {
        return Base64.getEncoder().encodeToString(gameName.getBytes());
    }

    public String getName() {
        return "Sopra Steria CtF 4. mars 2017";
    }
}
