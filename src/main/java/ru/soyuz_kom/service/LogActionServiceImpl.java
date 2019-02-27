package ru.soyuz_kom.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.soyuz_kom.entity.LogAction;
import ru.soyuz_kom.repository.LogActionRepository;

@Service
public class LogActionServiceImpl {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    LogActionRepository logActionRepository;

    public void push(String typeAction, Integer userId, boolean isSuccess, Object request, Object response) {

        LogAction log = new LogAction();
        log.setTypeAction(typeAction);
        log.setUserId(userId);
        log.setIsSuccess(isSuccess);
        log.setRequest(request);
        log.setResponse(response);

        rabbitTemplate.convertAndSend("logAction", log);
    }
}
