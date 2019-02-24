package ru.soyuz_kom.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import ru.soyuz_kom.provider.Receiver;
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
