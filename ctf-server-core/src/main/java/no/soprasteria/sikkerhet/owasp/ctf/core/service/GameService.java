package no.soprasteria.sikkerhet.owasp.ctf.core.service;

import no.soprasteria.sikkerhet.owasp.ctf.core.games.GameConfig;
import no.soprasteria.sikkerhet.owasp.ctf.core.games.structure.FlagStructure;
import no.soprasteria.sikkerhet.owasp.ctf.core.games.structure.GameStructure;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class GameService {

    private Map<UUID, GameConfig> games;
    private FlagService flagService;

    public GameService(FlagService flagService) {
        this.flagService = flagService;
        games = new HashMap<>();
    }

    public UUID addGame(GameStructure game) {
        UUID key = UUID.randomUUID();
        synchronized (games) {
            GameConfig gameConfig = new GameConfig();
            games.put(key, gameConfig);

        }

        List<FlagStructure> flags = game.flags;
        flags.stream().forEach(f -> flagService.addFlag(f.flagName, f.flag, 1l, f.tips, f.beskrivelse));

        return key;
    }

    public String getName(UUID key) {
        return games.get(key).name;
    }

    public boolean isGameOn(UUID key) {
        return games.get(key).gameOn;
    }

    public void startGame(UUID key) {
        synchronized (games) {
            GameConfig gameConfig = games.get(key);
            gameConfig.gameOn = true;
        }
    }

    public void pauseGame(UUID key) {
        synchronized (games) {
            GameConfig gameConfig = games.get(key);
            gameConfig.gameOn = false;
        }
    }

}
