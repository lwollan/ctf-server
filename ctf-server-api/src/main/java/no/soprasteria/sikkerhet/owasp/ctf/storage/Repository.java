package no.soprasteria.sikkerhet.owasp.ctf.storage;

import java.util.Map;
import java.util.Optional;

public interface Repository<V> {

    void put(String key, V value);

    Optional<V> get(String key);

    Map<String, V> list();
}
