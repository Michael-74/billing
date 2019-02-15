package ru.soyuz_kom.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.soyuz_kom.provider.IProvider;
import ru.soyuz_kom.provider.SmotreshkaProvider;
import ru.soyuz_kom.repository.SmotreshkaRepository;

@Configuration
@ComponentScan(basePackages = {"ru.soyuz_kom.provider"})
public class WebProvider {

    @Bean
    public SmotreshkaProvider smt() {
        System.out.println("test");
        SmotreshkaProvider smotreshkaProvider = new SmotreshkaProvider();
        //smotreshkaProvider.makeInstance();

        return smotreshkaProvider;
    }
}
