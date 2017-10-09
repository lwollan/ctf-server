package no.soprasteria.sikkerhet.owasp.ctf;

import javax.ws.rs.core.Application;

public class ApplicationContext {

    private ApplicationContext() {

    }

    @SuppressWarnings("unchecked")
    public static <T> T get(Application application, Class<T> klasse) {
        return (T)application.getProperties().get(key(klasse));
    }

    @SuppressWarnings("unchecked")
    public static <T> T put(Application application, Object klasse) {
        return (T)application.getProperties().put(key(klasse.getClass()), klasse);
    }

    static String key(Class<?> klasse) {
        return klasse.getName();
    }

}
