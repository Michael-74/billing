package ru.soyuz_kom.service.Impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.soyuz_kom.entity.Client;
import ru.soyuz_kom.entity.LogActionUser;
import ru.soyuz_kom.entity.User;
import ru.soyuz_kom.repository.LogActionUserRepository;
import ru.soyuz_kom.repository.UserRepository;

@Service
public class LogActionUserServiceImpl {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    LogActionUserRepository logActionUserRepository;

    @Autowired
    UserRepository userRepository;

    public void push(String url, String typeAction, String methodName, Client client, boolean isSuccess, Object request, Object response) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(auth.getName());

        LogActionUser log = new LogActionUser();
        log.setUrl(url);
        log.setTypeAction(typeAction);
        log.setUserId(user.getId());
        log.setClientId(client.getId());
        log.setMethod(methodName);
        log.setIsSuccess(isSuccess);
        log.setRequest(request);
        log.setResponse(response);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = null;
        try {
            json = ow.writeValueAsString(log);
            rabbitTemplate.convertAndSend("log", json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
