package no.soprasteria.sikkerhet.owasp.ctf;

import javax.ws.rs.core.Application;

public class ApplicationContext {

    private ApplicationContext() {

    }

    public static <T> T get(Application application, Class<T> klasse) {
        return (T)application.getProperties().get(key(klasse));
    }

    public static <T> T put(Application application, Object klasse) {
        return (T)application.getProperties().put(key(klasse.getClass()), klasse);
    }

    static String key(Class<?> klasse) {
        return klasse.getName();
    }

}
