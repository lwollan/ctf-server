package no.soprasteria.sikkerhet.owasp.ctf.storage;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

class HashMapRepository<V> implements Repository<V> {

    protected Map<String, V> map;

    public HashMapRepository() {
        map = new HashMap<>();
    }

    @Override
    public void put(String key, V value) {
        map.put(key, value);
    }

    @Override
    public Optional<V> get(String key) {
        return Optional.ofNullable(map.get(key));
    }

    @Override
    public Map<String, V> list() {
        HashMap<String, V> map = new HashMap<>();
        map.putAll(this.map);
        return map;
    }
}
