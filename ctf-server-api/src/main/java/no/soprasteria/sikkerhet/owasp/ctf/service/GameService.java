package no.soprasteria.sikkerhet.owasp.ctf.service;

import no.soprasteria.sikkerhet.owasp.ctf.games.GameConfig;

public class GameService {

    private boolean gameOn = false;
    private String gameName = null;

    public GameService(GameConfig gameConfig) {
        gameName = gameConfig.getName();
    }

    public String getName() {
        return gameName;
    }

    public boolean isGameOn() {
        return gameOn;
    }

    public void startGame() {
        gameOn = true;
    }

    public void pauseGame() {
        gameOn = false;
    }

}
