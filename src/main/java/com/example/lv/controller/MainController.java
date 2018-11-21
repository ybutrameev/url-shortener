package com.example.lv.controller;

import com.example.lv.common.Validator;
import com.example.lv.service.UrlService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class MainController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);
    private final UrlService urlService;

    public MainController(UrlService urlService) {
        this.urlService = urlService;
    }

    @RequestMapping("/r/{id}")
    public RedirectView redirectUrl(@PathVariable String id, HttpServletRequest request) {
        LOGGER.info("Received shortened url to redirect: " + id);

        RedirectView redirectView = new RedirectView();
        if (Validator.INSTANCE.isIdValid(id)) {
            String redirectUrlString = urlService.getLongUrlFromId(id);

            redirectUrlString = (redirectUrlString == null) ? request.getRequestURL().toString() + "/error" : redirectUrlString;
            redirectView.setUrl(redirectUrlString);
        } else {
            redirectView.setUrl(request.getRequestURL().toString() + "/error");
        }

        return redirectView;
    }

}