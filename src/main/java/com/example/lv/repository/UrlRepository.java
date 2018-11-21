package com.example.lv.repository;

import com.example.lv.common.IdConverter;
import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.concurrent.ExecutionException;

@Repository
public class UrlRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(UrlRepository.class);
    private final String idKey = "id";
    private final String urlKey = "url:";
    private final String blacklistKey = "blacklist";
    private final RedisClient redisClient = RedisClient.create("redis://localhost:6379/");
    private final StatefulRedisConnection<String, String> connection = redisClient.connect();
    private final RedisAsyncCommands<String, String> asyncCommands = connection.async();

    public Long incrementId() {
        Long id;
        try {
            id = asyncCommands.incr(idKey).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return null;
        }
        LOGGER.info("Incrementing ID: {}", id - 1);
        return id - 1;
    }

    public void save(String key, String url) {
        LOGGER.info("Saving: {} at {}", url, key);
        asyncCommands.hset(urlKey, key, url);
    }

    public void delete(String id) {
        LOGGER.info("Deleting: {}", id);
        asyncCommands.hdel(urlKey, "url:" + id, "url_custom:" + id);
    }

    public String getUrl(String id) throws Exception {
        String url;
        url = asyncCommands.hget(urlKey, "url_custom:" + id).get();
        LOGGER.info("Retrieved custom {} at {}", url, id);
        if (url == null) {
            LOGGER.info("Custom URL at key {} does not exist. Searching in ordinary url", id);
            Long dictionaryKey = IdConverter.getDictionaryKeyFromUniqueId(id);
            url = asyncCommands.hget(urlKey, "url:" + dictionaryKey).get();
        }
        return url;
    }

    public boolean isIdInBlackList(String id) throws Exception {
        return asyncCommands.sismember(blacklistKey, id).get();
    }

    public void addIdInBlacklist(String id) {
        LOGGER.info("Adding to blacklist ID: {}", id);
        asyncCommands.sadd(blacklistKey, id);
    }

    public void removeIdFromBlacklist(String id) {
        LOGGER.info("Removing from blacklist ID: {}", id);
        asyncCommands.srem("blacklist", id);
    }

    public Long getUrlCount() {
        Long urlCount;
        try {
            urlCount = asyncCommands.hlen(urlKey).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return null;
        }
        return urlCount;
    }
}
