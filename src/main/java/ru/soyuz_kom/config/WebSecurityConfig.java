package ru.soyuz_kom.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import ru.soyuz_kom.repository.UserDetailsRepo;
import ru.soyuz_kom.security.JWTAuthenticationFilter;
import ru.soyuz_kom.security.JWTLoginFilter;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@ComponentScan("ru.soyuz_kom")
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;

    public WebSecurityConfig(@Qualifier("userDetailsServiceImpl") UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }


    @Override
    public void configure(WebSecurity web) throws Exception {
        //web.ignoring().antMatchers("/assets/js/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
            .and()
                .csrf().disable()
                /*
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            */
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/").permitAll()
                .antMatchers(HttpMethod.GET, "/assets/**", "/images/**", "/fonts/**", "favicon.ico").permitAll()
                .antMatchers(HttpMethod.GET, "/login").permitAll()
                .antMatchers(HttpMethod.GET, "/404").permitAll()
                .anyRequest().authenticated()
            .and()
                //.addFilterBefore(new JWTLoginFilter("/auth/v1/login", authenticationManager()), UsernamePasswordAuthenticationFilter.class)

                // Проверяет запросы на наличие токена JWT
                .addFilterBefore(new JWTAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(encoder());
    }

    // Указываем Spring контейнеру, что надо инициализировать ShaPasswordEncoder
    // Это можно вынести в WebAppConfig, но для понимаемости оставил тут
    @Bean
    public BCryptPasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }
}
