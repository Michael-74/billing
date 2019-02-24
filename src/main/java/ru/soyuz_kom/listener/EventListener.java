package ru.soyuz_kom.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Component
public class EventListener {

    private CountDownLatch latch = new CountDownLatch(1);

    @RabbitListener(queues = "task")
    public void processTask(String message) throws InterruptedException {
        Thread.sleep(1000);
        System.out.println("task: " + message);
        latch.countDown();
    }

    @RabbitListener(queues = "log")
    public void processLog(String message) throws InterruptedException {
        Thread.sleep(1000);
        System.out.println("log: " + message);
        latch.countDown();
    }
}
