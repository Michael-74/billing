package ru.soyuz_kom.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import ru.soyuz_kom.entity.Task;
import ru.soyuz_kom.repository.TaskRepository;
import ru.soyuz_kom.service.Impl.SchedulerServiceImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Configuration
@EnableScheduling
public class Scheduling {

    @Autowired
    SchedulerServiceImpl schedulerService;

    @Scheduled(fixedRate = 1000)
    public void scheduler() throws InterruptedException, ParseException {

        schedulerService.checkTasks();
    }
}
