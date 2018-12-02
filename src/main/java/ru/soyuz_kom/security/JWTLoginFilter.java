package ru.soyuz_kom.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    static final String ORIGIN = "http://localhost:8000";

    public JWTLoginFilter(String url, AuthenticationManager authManager) {
        super(new AntPathRequestMatcher(url));
        setAuthenticationManager(authManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException, IOException {

        // CORS - should be deleted!
        String origin = req.getHeader(ORIGIN);
        res.setHeader("Access-Control-Allow-Origin", "*");//* or origin as u prefer
        res.setHeader("Access-Control-Allow-Credentials", "true");
        res.setHeader("Access-Control-Allow-Headers", req.getHeader("Access-Control-Request-Headers"));
        // end of CORS

        AccountCredentials creds = new ObjectMapper().readValue(req.getInputStream(), AccountCredentials.class);
        System.out.println("User logged in1: " + creds.getUsername());
        log.info("User logged in2: " + creds.getUsername());

        return getAuthenticationManager().authenticate(
                new UsernamePasswordAuthenticationToken(
                        creds.getUsername(),
                        creds.getPassword(),
                        Collections.emptyList()
                )
        );
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication auth) throws IOException {
        TokenAuthenticationHelper.addAuthentication(req, res, auth);
    }

    static class AccountCredentials {
        private String username;
        private String password;

        String getUsername() {
            return username;
        }

        void setUsername(String username) {
            this.username = username;
        }

        String getPassword() {
            return password;
        }

        void setPassword(String password) {
            this.password = password;
        }
    }
}