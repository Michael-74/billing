package ru.soyuz_kom.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.soyuz_kom.entity.LogAction;
import ru.soyuz_kom.repository.LogActionRepository;
import ru.soyuz_kom.service.LogActionServiceImpl;

import java.util.concurrent.CountDownLatch;

@Component
public class EventListener {

    @Autowired
    LogActionRepository logActionRepository;

    private CountDownLatch latch = new CountDownLatch(1);

    @RabbitListener(queues = "task")
    public void processTask(String message) throws InterruptedException {
        Thread.sleep(1000);
        System.out.println("task: " + message);
        latch.countDown();
    }

    @RabbitListener(queues = "logAction")
    public void processLogAction(LogAction log) {
        logActionRepository.save(log);

        latch.countDown();
    }
}
