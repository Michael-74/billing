package ru.soyuz_kom.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.soyuz_kom.entity.LogSmotreshka;
import ru.soyuz_kom.entity.User;
import ru.soyuz_kom.repository.LogSmotreshkaRepository;
import ru.soyuz_kom.repository.UserRepository;

@Component
public class RestTemplateHelper {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    LogSmotreshkaRepository logSmotreshkaRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RabbitTemplate rabbitTemplate;

    public Object exchange(String url, HttpMethod method, HttpEntity httpEntity, ParameterizedTypeReference object) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(auth.getName());

        LogSmotreshka logSmotreshka = new LogSmotreshka();
        logSmotreshka.setUrl(url);
        logSmotreshka.setTypeAction(String.valueOf(method));
        logSmotreshka.setUserId(user.getId());
        logSmotreshka.setRequest(httpEntity);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();


        try {
            ResponseEntity response = restTemplate.exchange(
                    url,
                    method,
                    httpEntity,
                    object
            );

            if(response.getStatusCode().value() == 200) {
                System.out.println("exchange body: " + response.getBody());
                logSmotreshka.setIsSuccess(true);
                logSmotreshka.setResponse(response.getBody());
                //logActionRepository.save(logSmotreshka);
                String json = ow.writeValueAsString(logSmotreshka);
                rabbitTemplate.convertAndSend("log", json);
                return response.getBody();
            }
            System.out.println("exchange: " + url);
            logSmotreshka.setIsSuccess(false);
            logSmotreshka.setResponse(null);
            String json = ow.writeValueAsString(logSmotreshka);
            rabbitTemplate.convertAndSend("log", json);
            return null;
        } catch(Exception ex) {
            System.out.println("exchange-error: " + ex);
            logSmotreshka.setIsSuccess(false);
            logSmotreshka.setResponse(ex);
            //logActionRepository.save(logSmotreshka);
            String json = null;
            try {
                json = ow.writeValueAsString(logSmotreshka);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            rabbitTemplate.convertAndSend("log", json);
            return null;
        }
    }
}
