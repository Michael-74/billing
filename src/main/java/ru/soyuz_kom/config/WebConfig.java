package ru.soyuz_kom.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.*;
import ru.soyuz_kom.repository.TaskRepository;

@Configuration
@EnableWebMvc
@ComponentScan({"ru.soyuz_kom.controller", "ru.soyuz_kom.config", "ru.soyuz_kom.service"})
public class WebConfig implements WebMvcConfigurer {

    private final static long MAX_AGE_SECS = 3600;



    @Autowired
    private TaskRepository taskRepository;

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
}
