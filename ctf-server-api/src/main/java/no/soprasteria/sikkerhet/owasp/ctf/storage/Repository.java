package no.soprasteria.sikkerhet.owasp.ctf.storage;

import java.util.Map;
import java.util.Optional;

public interface Repository {

    void put(String key, String value);

    Optional<String> get(String key);

    Map<String, String> list();

    void remove(String key);

    void deleteAll();
}
