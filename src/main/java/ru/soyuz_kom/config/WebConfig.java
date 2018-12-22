package ru.soyuz_kom.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.servlet.config.annotation.*;
import ru.soyuz_kom.entity.Schedule;
import ru.soyuz_kom.repository.ScheduleRepository;
import sun.util.resources.LocaleData;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Configuration
@EnableScheduling
@EnableWebMvc
@ComponentScan({"ru.soyuz_kom.controller", "ru.soyuz_kom.config", "ru.soyuz_kom.service"})
public class WebConfig implements WebMvcConfigurer {

    private final static long MAX_AGE_SECS = 3600;



    @Autowired
    private ScheduleRepository scheduleRepository;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("HEAD", "OPTIONS", "GET", "POST", "PUT", "PATCH", "DELETE")
                .maxAge(MAX_AGE_SECS);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){

        registry.addResourceHandler("/view/**").addResourceLocations("classpath:/templates/");
        //registry.addResourceHandler("/js/**").addResourceLocations("classpath:/static/js/");
        //registry.addResourceHandler("/css/**").addResourceLocations("classpath:/static/css/");
        registry.addResourceHandler("/fonts/**").addResourceLocations("classpath:/static/assets/fonts/");
        registry.addResourceHandler("/assets/**").addResourceLocations("classpath:/static/assets/");
        registry.addResourceHandler("/images/**").addResourceLocations("classpath:/static/assets/images/");
    }


    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.setOrder(Ordered.LOWEST_PRECEDENCE);
        registry.addViewController("/**").setViewName("forward:/");
    }


    /*
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/**").setViewName("forward:/");
    }

    @Bean
    public WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> containerCustomizer() {
        return container -> {
            container.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/"));
        };
    }
    */
    @Scheduled(fixedRate = 1000)
    public void test() throws InterruptedException {
        LocalDateTime now = LocalDateTime.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formatDateTime = now.format(formatter);

        System.out.println("Time : " + formatDateTime);


        List<Schedule> list= scheduleRepository.findAll();

        LocalDateTime dateTime = LocalDateTime.parse(list.get(0).getDateformat());

        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formatDateTime2 = dateTime.format(formatter2);


        if(formatDateTime.equals(formatDateTime2)) {
            System.out.println("WINNNNNNNNN");
        }

        System.out.println("Schedule: " + formatDateTime2);
    }
}
