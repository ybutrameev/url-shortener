package com.example.lv;

import com.example.lv.repository.UrlRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.stream.Stream;

@SpringBootApplication
public class Application {
    private final UrlRepository urlRepository;

    public Application(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner runner() {
        return args -> Stream.of("error", "info", "help").forEach(urlRepository::addIdInBlacklist);
    }
}
