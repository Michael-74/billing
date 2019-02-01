package ru.soyuz_kom.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.soyuz_kom.service.Impl.SchedulerServiceImpl;

import java.text.ParseException;

@Configuration
@Component
public class Scheduling {

    @Autowired
    SchedulerServiceImpl schedulerService;

    @Scheduled(cron = "0 */1 * * * *")
    public void scheduler() throws ParseException {
        schedulerService.checkTasks();
    }
}
