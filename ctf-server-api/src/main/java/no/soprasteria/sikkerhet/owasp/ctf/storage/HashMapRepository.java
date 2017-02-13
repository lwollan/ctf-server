package no.soprasteria.sikkerhet.owasp.ctf.storage;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class HashMapRepository implements Repository {

    protected Map<String, String> map;

    public HashMapRepository() {
        map = new HashMap<>();
    }

    @Override
    public void put(String key, String value) {
        map.put(key, value);
    }

    @Override
    public Optional<String> get(String key) {
        return Optional.ofNullable(map.get(key));
    }

    @Override
    public Map<String, String> list() {
        HashMap<String, String> map = new HashMap<>();
        map.putAll(this.map);
        return map;
    }

    @Override
    public void remove(String key) {
        if (map.containsKey(key)) {
            map.remove(key);
        }
    }

    @Override
    public void deleteAll() {
        map.clear();
    }
}
