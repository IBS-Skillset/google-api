package com.google.api.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.api.model.autoComplete.AutoCompleteResponse;
import com.google.api.model.autoComplete.Place;
import com.google.api.model.placeId.PlaceResponse;
import org.springframework.beans.factory.annotation.Value;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@ApplicationScoped
public class CacheService {

    private static JedisPool jedisPool = null;

    public CacheService(@Value("${redis.hostName}") String redisHost,
                        @Value("${redis.port") Integer redisPort) {
        jedisPool = new JedisPool(redisHost,redisPort);
    }

    private JedisPoolConfig buildPoolConfig() {
        final JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(128);
        poolConfig.setMaxIdle(128);
        poolConfig.setMinIdle(16);
        poolConfig.setTestOnBorrow(true);
        poolConfig.setTestOnReturn(true);
        poolConfig.setTestWhileIdle(true);
        poolConfig.setNumTestsPerEvictionRun(3);
        poolConfig.setBlockWhenExhausted(true);
        return poolConfig;
    }

    public void cacheAutoCompleteResponse(AutoCompleteResponse response) {
        try (Jedis jedis = jedisPool.getResource()) {
            response.getPlace().stream()
                    .map(place -> jedis.set(place.getDescription(), place.getPlaceId()))
                    .collect(Collectors.toList());
        }

    }

    public AutoCompleteResponse autoCompleteResponseFromCache(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            Set<String> keySet = jedis.keys(key+"*");
            if (keySet.size() >= 3) {
                AutoCompleteResponse response = new AutoCompleteResponse();
                List<Place> placeList = keySet.stream()
                        .map(keys -> mapPlace(keys))
                        .collect(Collectors.toList());
                response.setPlace(placeList);
                return response;
            } else {
                return null;
            }
        }
    }

    private Place mapPlace(String key) {
        Place place = new Place();
        place.setDescription(key);
        try (Jedis jedis = jedisPool.getResource()) {
           place.setPlaceId(jedis.get(key));
        }
        return place;
    }

    public void cachePlaceResponse(String placeId, PlaceResponse placeResponse) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.set("*"+placeId, mapper.writeValueAsString(placeResponse));
        }
    }

    public PlaceResponse placeResponseFromCache(String key) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        try (Jedis jedis = jedisPool.getResource()) {
            String cacheRes = jedis.get("*"+key);
            if (Objects.nonNull(cacheRes)) {
               return mapper.readValue(cacheRes,PlaceResponse.class);
            } else {
                return null;
            }
        }
    }
}
