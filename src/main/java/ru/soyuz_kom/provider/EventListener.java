package ru.soyuz_kom.provider;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Component
public class EventListener {

    private CountDownLatch latch = new CountDownLatch(1);

    @RabbitListener(queues = "task")
    public void processTask(String message) {
        System.out.println("spring: " + message);
        latch.countDown();
    }

    @RabbitListener(queues = "log")
    public void processLog(String message) {
        System.out.println("log: " + message);
        latch.countDown();
    }
}
