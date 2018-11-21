package com.example.lv.controller;

import com.example.lv.common.Validator;
import com.example.lv.controller.dto.ShortenRequest;
import com.example.lv.exception.CustomIdIsInvalidException;
import com.example.lv.exception.InputIdIsInvalidException;
import com.example.lv.exception.UrlToShortenIsInvalidException;
import com.example.lv.service.UrlService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Collections;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ApiController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApiController.class);
    private final UrlService urlService;

    public ApiController(UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping(value = "shorten", consumes = {"application/json"})
    public Map<String, String> shortenUrl(@RequestBody @Valid final ShortenRequest shortenRequest, HttpServletRequest request) {
        String longUrl = shortenRequest.getUrl();
        String customId = shortenRequest.getCustomId();

        if (longUrl == null) {
            throw new UrlToShortenIsInvalidException("URL to shorten is NULL");
        }

        if (Validator.INSTANCE.isUrlValid(longUrl)) {
            if (customId != null && !Validator.INSTANCE.isIdValid(customId)) {
                throw new CustomIdIsInvalidException("Invalid custom Id: " + customId);
            }

            String localUrl = request.getRequestURL().toString();
            String shortenedUrl = urlService.shortenUrl(localUrl, shortenRequest.getUrl(), customId);

            LOGGER.info("Shortened url through API to: " + shortenedUrl);
            return Collections.singletonMap("shortUrl", shortenedUrl);
        }

        throw new UrlToShortenIsInvalidException("Invalid URL to shorten: " + longUrl);
    }

    @GetMapping("get/{id}")
    public Map<String, String> getLongUrl(@PathVariable String id) {

        if (Validator.INSTANCE.isIdValid(id)) {
            return Collections.singletonMap("longUrl", urlService.getLongUrlFromId(id));
        }

        throw new InputIdIsInvalidException("Invalid input Id: " + id);
    }

    @GetMapping("count")
    public Map<String, String> getUrlCount() {
        return Collections.singletonMap("urlCount", urlService.getUrlCount().toString());
    }
}
