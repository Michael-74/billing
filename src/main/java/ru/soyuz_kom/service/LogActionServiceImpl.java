package ru.soyuz_kom.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.soyuz_kom.entity.LogSmotreshka;
import ru.soyuz_kom.repository.LogSmotreshkaRepository;

@Service
public class LogActionServiceImpl {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    LogSmotreshkaRepository logSmotreshkaRepository;

    public void push(String typeAction, Integer userId, boolean isSuccess, Object request, Object response) {

        LogSmotreshka log = new LogSmotreshka();
        log.setTypeAction(typeAction);
        log.setUserId(userId);
        log.setIsSuccess(isSuccess);
        log.setRequest(request);
        log.setResponse(response);

        rabbitTemplate.convertAndSend("logAction", log);
    }
}
