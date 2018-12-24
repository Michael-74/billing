package ru.soyuz_kom.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import ru.soyuz_kom.entity.Task;
import ru.soyuz_kom.repository.TaskRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Configuration
@EnableScheduling
public class Scheduling {

    @Autowired
    private TaskRepository taskRepository;

    @Scheduled(fixedRate = 1000)
    public void scheduler() throws InterruptedException {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formatDateTime = now.format(formatter);

        //System.out.println("Time : " + formatDateTime);


        List<Task> list = taskRepository.findAll();

        //LocalDateTime dateTime = LocalDateTime.parse(list.get(0).getDatetime());
        //System.out.println("COUNT: " + list.size());
        //DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        //String formatDateTime2 = dateTime.format(formatter2);

        /**
         * TODO:: проверка дат из задачи с текущем временем
         */
        /*
        if(formatDateTime.equals(formatDateTime2)) {
            //System.out.println("WINNNNNNNNN");
        }
        */

        //System.out.println("Task: " + formatDateTime2);
    }
}
