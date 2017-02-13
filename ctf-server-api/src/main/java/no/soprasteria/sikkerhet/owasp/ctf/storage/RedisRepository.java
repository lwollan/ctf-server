package no.soprasteria.sikkerhet.owasp.ctf.storage;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static java.util.stream.Collectors.toMap;

public class RedisRepository implements Repository {

    private String prefix;
    private JedisPool jedisPool = null;

    public RedisRepository(String prefix, JedisPool jedisPool) {
        this.prefix = prefix;
        this.jedisPool = jedisPool;
    }

    public void put(String key, String value) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.set(makeKey(key), value);
        }
    }

    public Optional<String> get(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            return Optional.ofNullable(jedis.get(makeKey(key)));
        }
    }

    public Map<String, String> list() {
        try (Jedis jedis = jedisPool.getResource()) {
            Set<String> list = jedis.keys(makeKey("*"));
            Map<String, String> result = list.stream().
                    collect(toMap(key -> key.substring(prefix.length()), key -> jedis.get(key)));
            return result;
        }
    }

    @Override
    public void remove(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.del(makeKey(key));
        }
    }

    @Override
    public void deleteAll() {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.flushDB();
        }
    }

    private String makeKey(String key) {
        return prefix + key;
    }

}
