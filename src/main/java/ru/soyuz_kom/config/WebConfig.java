package ru.soyuz_kom.config;

import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
@EnableWebMvc
@ComponentScan({"ru.soyuz_kom.controller", "ru.soyuz_kom.config"})
public class WebConfig implements WebMvcConfigurer {

    private final static long MAX_AGE_SECS = 3600;

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
    @Bean
    public WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> containerCustomizer() {
        return container -> {
            container.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/"));
        };
    }
    */
}
