package no.soprasteria.sikkerhet.owasp.ctf;

import no.soprasteria.sikkerhet.owasp.ctf.api.BoardResource;
import no.soprasteria.sikkerhet.owasp.ctf.api.FlagResource;
import no.soprasteria.sikkerhet.owasp.ctf.api.GameResource;
import no.soprasteria.sikkerhet.owasp.ctf.api.TeamResource;
import no.soprasteria.sikkerhet.owasp.ctf.filter.BeskyttetFilter;
import no.soprasteria.sikkerhet.owasp.ctf.filter.CORSFilter;
import no.soprasteria.sikkerhet.owasp.ctf.filter.TeamKeyFilter;
import no.soprasteria.sikkerhet.owasp.ctf.games.MrRobotGame;
import no.soprasteria.sikkerhet.owasp.ctf.service.*;
import no.soprasteria.sikkerhet.owasp.ctf.storage.HashMapRepository;
import no.soprasteria.sikkerhet.owasp.ctf.storage.RedisRepository;
import no.soprasteria.sikkerhet.owasp.ctf.storage.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.net.URI;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@ApplicationPath("api")
public class CtFApplication extends Application {

    private Map<String, Object> properties = new HashMap<>();

    private static Logger logger = LoggerFactory.getLogger(CtFApplication.class);

    public CtFApplication() throws Exception {
        logger.info("Starting up.");
        setupServices();
        logger.info("Application started.");

    }

    private static Jedis getConnection() {
        try {
            URI redisURI = new URI(System.getenv("REDIS_URL"));
            return new Jedis(redisURI);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new HashSet<>();
        resources.add(TeamResource.class);
        // resources.add(ScoreResource.class);
        resources.add(BoardResource.class);
        resources.add(FlagResource.class);
        resources.add(GameResource.class);

        resources.add(BeskyttetFilter.class);
        resources.add(TeamKeyFilter.class);
        resources.add(CORSFilter.class);

        return resources;
    }

    @Override
    public Map<String, Object> getProperties() {
        return properties;
    }

    private void setupServices() throws Exception {
        Jedis jedis = getConnection();

        Repository teamRepository;
        Repository scoreRepository;

        if (jedis != null) {
            logger.info("Found jedis: {}.", jedis.getClient().getHost());
            teamRepository = new RedisRepository("team", jedis);
            scoreRepository = new RedisRepository("score", jedis);
        } else {
            logger.info("No jedis found, all in memory.");
            teamRepository = new HashMapRepository();
            scoreRepository = new HashMapRepository();
        }

        ScoreService scoreService = new ScoreService(teamRepository, scoreRepository);
        TeamService teamService = new TeamService(teamRepository);
        BoardService boardService = new BoardService(teamRepository, scoreRepository);
        FlagService flagService = new FlagService();
        GameService gameService = new GameService();

        ApplicationContext.put(this, scoreService);
        ApplicationContext.put(this, teamService);
        ApplicationContext.put(this, boardService);
        ApplicationContext.put(this, flagService);
        ApplicationContext.put(this, gameService);

        MrRobotGame.setupGame(flagService);
    }

}