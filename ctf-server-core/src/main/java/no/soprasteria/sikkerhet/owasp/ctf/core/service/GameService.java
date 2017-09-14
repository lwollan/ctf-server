package no.soprasteria.sikkerhet.owasp.ctf.core.service;

import no.soprasteria.sikkerhet.owasp.ctf.core.games.GameConfig;
import no.soprasteria.sikkerhet.owasp.ctf.core.games.structure.FlagStructure;
import no.soprasteria.sikkerhet.owasp.ctf.core.games.structure.GameStructure;

import java.util.List;

public class GameService {

    private GameConfig game;
    private FlagService flagService;

    public GameService(FlagService flagService) {
        this.flagService = flagService;
    }

    public void setGame(GameStructure gameStructure) {
        game = new GameConfig();
        game.name = gameStructure.name;

        List<FlagStructure> flags = gameStructure.flags;
        flags.forEach(f -> flagService.addFlag(f.flagName, f.flag, 1L, f.tips, f.beskrivelse));

    }

    public boolean isGameAvailable() {
        return game != null;
    }

    public String getName() {
        return game.name;
    }

    public boolean isGameOn() {
        return isGameAvailable() && game.gameOn;
    }

    public void startGame() {
        game.gameOn = true;
    }

    public void pauseGame() {
        game.gameOn = false;
    }

}
