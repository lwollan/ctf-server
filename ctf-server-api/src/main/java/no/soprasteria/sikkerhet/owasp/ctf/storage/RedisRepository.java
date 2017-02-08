package no.soprasteria.sikkerhet.owasp.ctf.storage;

import redis.clients.jedis.Jedis;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static java.util.stream.Collectors.toMap;

public class RedisRepository implements Repository {

    private String prefix;
    private Jedis jedis;

    public RedisRepository(String prefix, Jedis client) {
        this.prefix = prefix;
        this.jedis = client;
    }

    public void put(String key, String value) {
        jedis.connect();
        jedis.set(makeKey(key), value);
        jedis.disconnect();

    }

    public Optional<String> get(String key) {
        jedis.connect();
        Optional<String> value = Optional.ofNullable(jedis.get(makeKey(key)));
        jedis.disconnect();
        return value;
    }

    public Map<String, String> list() {
        jedis.connect();
        Set<String> list = jedis.keys(makeKey("*"));
        Map<String, String> result = list.stream().
                collect(toMap(key -> key.substring(prefix.length()), key -> jedis.get(key)));
        jedis.disconnect();
        return result;
    }

    private String makeKey(String key) {
        return prefix + key;
    }

}
