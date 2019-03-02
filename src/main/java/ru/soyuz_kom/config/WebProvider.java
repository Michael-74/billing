package ru.soyuz_kom.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.soyuz_kom.service.Impl.ClientServiceImpl;
import ru.soyuz_kom.service.Impl.MikrotikService;
import ru.soyuz_kom.service.Impl.SmotreshkaService;

@Configuration
public class WebProvider {

    @Bean
    public SmotreshkaService smotreshkaService() {

        return new SmotreshkaService();
    }

    @Bean
    public ClientServiceImpl clientService() {

        return new ClientServiceImpl();
    }
}
