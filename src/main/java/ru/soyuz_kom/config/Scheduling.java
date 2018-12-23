package ru.soyuz_kom.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.servlet.config.annotation.*;
import ru.soyuz_kom.entity.Schedule;
import ru.soyuz_kom.repository.ScheduleRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Configuration
@EnableScheduling
public class Scheduling {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Scheduled(fixedRate = 1000)
    public void scheduler() throws InterruptedException {
        LocalDateTime now = LocalDateTime.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formatDateTime = now.format(formatter);

        System.out.println("Time : " + formatDateTime);


        List<Schedule> list= scheduleRepository.findAll();

        LocalDateTime dateTime = LocalDateTime.parse(list.get(0).getDateformat());
        System.out.println("COUNT: " + list.size());
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formatDateTime2 = dateTime.format(formatter2);


        if(formatDateTime.equals(formatDateTime2)) {
            System.out.println("WINNNNNNNNN");
        }

        System.out.println("Schedule: " + formatDateTime2);
    }
}
