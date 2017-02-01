package no.soprasteria.sikerhet.owasp.ctf;

import no.soprasteria.sikerhet.owasp.ctf.api.*;
import no.soprasteria.sikerhet.owasp.ctf.service.ScoreRepository;
import no.soprasteria.sikerhet.owasp.ctf.service.ScoreService;
import no.soprasteria.sikerhet.owasp.ctf.service.TeamRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.*;

@ApplicationPath("api")
public class CtFApplication extends Application {

    private Map<String, Object> properties = new HashMap<>();

    private static Logger logger = LoggerFactory.getLogger(CtFApplication.class);

    public CtFApplication() {
        logger.info("Starting up.");
        setupServices();
        logger.info("done.");
    }

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new HashSet<>();
        resources.add(TeamResource.class);
        resources.add(ScoreResource.class);
        return resources;
    }

    @Override
    public Map<String, Object> getProperties() {
        return properties;
    }

    private void setupServices() {
        TeamRepository teamRepository = new TeamRepository();
        ScoreRepository scoreRepository = new ScoreRepository();
        ScoreService scoreService = new ScoreService(teamRepository, scoreRepository);

        put(this, scoreService);
        put(this, teamRepository);
        put(this, scoreRepository);
    }

    public static <T> T get(Application application, Class<T> klasse) {
        return (T)application.getProperties().get(key(klasse));
    }

    static <T> T put(Application application, Object klasse) {
        return (T)application.getProperties().get(key(klasse.getClass()));
    }

    static String key(Class<?> klasse) {
        return klasse.getName();
    }


}