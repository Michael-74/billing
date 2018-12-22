package ru.soyuz_kom.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableCaching
@EnableJpaRepositories(basePackages = {"ru.soyuz_kom.repository"})
@EntityScan("ru.soyuz_kom.entity")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
