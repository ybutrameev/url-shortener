package com.example.lv.service;

import com.example.lv.common.IdConverter;
import com.example.lv.exception.CustomIdIsAlreadyUsedException;
import com.example.lv.exception.CustomIdIsInBlacklistException;
import com.example.lv.exception.LongUrlNotFoundException;
import com.example.lv.repository.UrlRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UrlService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UrlService.class);
    private final UrlRepository urlRepository;

    public UrlService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    public String shortenUrl(String localUrl, String longUrl, String customId) {
        LOGGER.info("Shortening {}", longUrl);
        // example.com/api/shorten -> example.com/
        if (localUrl.contains("api/shorten")) {
            localUrl = localUrl.replace("api/shorten", "r/");
        }

        if (!longUrl.matches("^(http|https)://.*$")) {
            longUrl = "http://" + longUrl;
        }

        String uniqueId;
        if (customId != null) {
            LOGGER.info("Received custom id: " + customId);
            if(urlRepository.isIdInBlackList(customId)) {
                throw new CustomIdIsInBlacklistException("This customId is in blacklist: " + customId);
            }
            if (urlRepository.getUrl(customId) == null) {
                urlRepository.save("url_custom:" + customId, longUrl);
                return localUrl + customId;
            }
            throw new CustomIdIsAlreadyUsedException("This customId is already used: " + customId);
        } else {
            Long id;
            while (true) {
                id = urlRepository.incrementId();
                uniqueId = IdConverter.createUniqueId(id);
                if (!urlRepository.isIdInBlackList(uniqueId) && urlRepository.getUrl(uniqueId) == null) {
                    LOGGER.info("Saving ordinary url {}", longUrl);
                    urlRepository.save("url:" + id, longUrl);
                    break;
                }
            }
            return localUrl + uniqueId;
        }
    }

    public String getLongUrlFromId(String uniqueId) {
        String longUrl;
        longUrl = urlRepository.getUrl(uniqueId);
        if (longUrl == null) {
            throw new LongUrlNotFoundException("Long url with ID: " + uniqueId + " is not found");
        }
        LOGGER.info("Converting shortened URL back to {}", longUrl);
        return longUrl;
    }

    public Long getUrlCount() {
        return urlRepository.getUrlCount();
    }

}
