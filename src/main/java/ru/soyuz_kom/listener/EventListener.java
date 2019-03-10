package ru.soyuz_kom.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.soyuz_kom.entity.LogSmotreshka;
import ru.soyuz_kom.repository.LogSmotreshkaRepository;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

@Component
public class EventListener {

    @Autowired
    LogSmotreshkaRepository logSmotreshkaRepository;

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
            LogSmotreshka logSmotreshka = objectMapper.readValue(log, LogSmotreshka.class);
            logSmotreshkaRepository.save(logSmotreshka);
        } catch (IOException e) {
            // TODO: поставить logger
            System.out.println("processLogAction error");
            e.printStackTrace();
        }

        latch.countDown();
    }
}
