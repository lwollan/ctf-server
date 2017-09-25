package no.soprasteria.sikkerhet.owasp.ctf.core.service;

import no.soprasteria.sikkerhet.owasp.ctf.core.games.GameConfig;
import no.soprasteria.sikkerhet.owasp.ctf.core.games.structure.FlagStructure;
import no.soprasteria.sikkerhet.owasp.ctf.core.games.structure.GameStructure;

import java.time.LocalDateTime;
import java.time.temporal.TemporalUnit;
import java.util.List;

import static java.time.temporal.ChronoUnit.HOURS;

public class GameService {

    private GameConfig game;
    private FlagService flagService;

    public GameService(FlagService flagService) {
        this.flagService = flagService;
    }

    public void setGame(GameStructure gameStructure) {
        game = new GameConfig();
        game.name = gameStructure.name;
        game.description = gameStructure.beskrivelse;
        game.start = LocalDateTime.now();
        game.end = LocalDateTime.now().plus(6, HOURS);

        List<FlagStructure> flags = gameStructure.flags;

        flagService.clear();

        flags.forEach(f -> flagService.addFlag(f.flagName, f.flag, 1L, f.tips, f.beskrivelse));

    }

    public boolean isGameAvailable() {
        return game != null;
    }

    public String getName() {
        if (isGameAvailable()) {
            return game.name;
        } else {
            return null;
        }
    }

    public boolean isGameOn() {
        return isGameAvailable() && game.gameOn;
    }

    public void startGame() {
        game.gameOn = true;
    }

    public void pauseGame() {
        if (isGameOn()) {
            game.gameOn = false;
        }
    }

    public String getGameDescription() {
        if (isGameAvailable()) {
            return this.game.description;
        } else {
            return null;
        }
    }

    public String getStartTime() {
        if (isGameAvailable()) {
            return game.start.toString();
        } else {
            return LocalDateTime.now().toString();
        }
    }

    public String getEndTime() {
        if (isGameAvailable()) {
            return game.end.toString();
        } else {
            return LocalDateTime.now().toString();
        }
    }
}
