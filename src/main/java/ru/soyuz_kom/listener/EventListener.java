package ru.soyuz_kom.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.soyuz_kom.entity.LogActionUser;
import ru.soyuz_kom.repository.LogActionUserRepository;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

@Component
public class EventListener {

    @Autowired
    LogActionUserRepository logActionUserRepository;

    private CountDownLatch latch = new CountDownLatch(1);

    @RabbitListener(queues = "task")
    public void processTask(String message) {
        System.out.println("task: " + message);
        latch.countDown();
    }

    @RabbitListener(queues = "log")
    public void processLogAction(String log) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            LogActionUser logActionUser = objectMapper.readValue(log, LogActionUser.class);
            System.out.println("processLogAction pre-add");
            logActionUserRepository.save(logActionUser);
            System.out.println("processLogAction add");
        } catch (IOException e) {
            // TODO: поставить logger
            System.out.println("processLogAction error");
            e.printStackTrace();
        }

        latch.countDown();
    }
}
