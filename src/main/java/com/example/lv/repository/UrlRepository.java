package com.example.lv.repository;

import com.example.lv.common.IdConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.ScanResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static redis.clients.jedis.ScanParams.SCAN_POINTER_START;

@Repository
public class UrlRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(UrlRepository.class);
    private final Jedis jedis;
    private final String idKey;
    private final String urlKey;
    private final String blacklistKey;

    public UrlRepository() {
        this.jedis = new Jedis();
        this.idKey = "id";
        this.urlKey = "url:";
        this.blacklistKey = "blacklist";
    }

    public UrlRepository(Jedis jedis, String idKey, String urlKey, String blackListKey) {
        this.jedis = jedis;
        this.idKey = idKey;
        this.urlKey = urlKey;
        this.blacklistKey = blackListKey;
    }

    public Long incrementId() {
        Long id = jedis.incr(idKey);
        LOGGER.info("Incrementing ID: {}", id - 1);
        return id - 1;
    }

    public void save(String key, String url) {
        LOGGER.info("Saving: {} at {}", url, key);
        jedis.hset(urlKey, key, url);
    }

    public void delete(String id) {
        LOGGER.info("Deleting: {}", id);
        jedis.hdel(urlKey, "url:" + id, "url_custom:" + id);
    }

    public String getUrl(String id) {
        String url = jedis.hget(urlKey, "url_custom:" + id);
        LOGGER.info("Retrieved custom {} at {}", url, id);
        if (url == null) {
            LOGGER.info("Custom URL at key {} does not exist. Searching in ordinary url", id);
            Long dictionaryKey = IdConverter.getDictionaryKeyFromUniqueId(id);
            url = jedis.hget(urlKey, "url:" + dictionaryKey);
        }
        return url;
    }

    public boolean isIdInBlackList(String id) {
        Set<String> blacklist = jedis.smembers(blacklistKey);
        return blacklist.stream().anyMatch(id::equals);
    }

    public void addIdInBlacklist(String id) {
        LOGGER.info("Adding to blacklist ID: {}", id);
        jedis.sadd("blacklist", id);
    }

    public void removeIdFromBlacklist(String id) {
        LOGGER.info("Removing from blacklist ID: {}", id);
        jedis.srem("blacklist", id);
    }

    public Long getUrlCount() {
        return jedis.hlen(urlKey);
    }
}
