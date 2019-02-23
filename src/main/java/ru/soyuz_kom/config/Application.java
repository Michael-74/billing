package ru.soyuz_kom.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import ru.soyuz_kom.service.Impl.SmotreshkaService;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.TimeZone;

@SpringBootApplication
@EnableCaching
@EnableScheduling
@EnableJpaRepositories(basePackages = {"ru.soyuz_kom.repository"})
@EntityScan("ru.soyuz_kom.entity")
public class Application {

    @Autowired
    SmotreshkaService smotreshkaService;

    @PostConstruct
    public void init(){
        smotreshkaService.load();

        TimeZone.setDefault(TimeZone.getDefault());   // It will set UTC timezone
        //System.out.println("Spring boot application running in UTC timezone :" + new Date());   // It will print UTC timezone
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);


    }

}
