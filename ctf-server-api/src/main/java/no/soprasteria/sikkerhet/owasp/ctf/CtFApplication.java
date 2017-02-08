package no.soprasteria.sikkerhet.owasp.ctf;

import no.soprasteria.sikkerhet.owasp.ctf.api.BoardResource;
import no.soprasteria.sikkerhet.owasp.ctf.api.FlagResource;
import no.soprasteria.sikkerhet.owasp.ctf.api.TeamResource;
import no.soprasteria.sikkerhet.owasp.ctf.filter.BeskyttetFilter;
import no.soprasteria.sikkerhet.owasp.ctf.filter.CORSFilter;
import no.soprasteria.sikkerhet.owasp.ctf.filter.TeamKeyFilter;
import no.soprasteria.sikkerhet.owasp.ctf.games.MrRobotGame;
import no.soprasteria.sikkerhet.owasp.ctf.service.BoardService;
import no.soprasteria.sikkerhet.owasp.ctf.service.FlagService;
import no.soprasteria.sikkerhet.owasp.ctf.service.ScoreService;
import no.soprasteria.sikkerhet.owasp.ctf.service.TeamService;
import no.soprasteria.sikkerhet.owasp.ctf.storage.ScoreRepository;
import no.soprasteria.sikkerhet.owasp.ctf.storage.TeamRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
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

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new HashSet<>();
        resources.add(TeamResource.class);
        // resources.add(ScoreResource.class);
        resources.add(BoardResource.class);
        resources.add(FlagResource.class);

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
        TeamRepository teamRepository = new TeamRepository();
        ScoreRepository scoreRepository = new ScoreRepository();
        ScoreService scoreService = new ScoreService(teamRepository, scoreRepository);
        TeamService teamService = new TeamService(teamRepository);
        BoardService boardService = new BoardService(teamRepository, scoreRepository);
        FlagService flagService = new FlagService();

        ApplicationContext.put(this, teamRepository);
        ApplicationContext.put(this, scoreRepository);
        ApplicationContext.put(this, scoreService);
        ApplicationContext.put(this, teamService);
        ApplicationContext.put(this, boardService);
        ApplicationContext.put(this, flagService);

        MrRobotGame.setupGame(flagService);
    }

}