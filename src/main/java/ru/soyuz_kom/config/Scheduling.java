package ru.soyuz_kom.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import ru.soyuz_kom.entity.Task;
import ru.soyuz_kom.repository.TaskRepository;

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
    private TaskRepository taskRepository;

    @Scheduled(fixedRate = 1000)
    public void scheduler() throws InterruptedException {

        List<Task> tasks = taskRepository.findAll();

        Date currentDate = new Date();
        SimpleDateFormat formatDatetime = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm");
        SimpleDateFormat formatDayOfWeek = new SimpleDateFormat("dd");

        for(Task task:  tasks) {
            switch (task.getTypeWriteOff()) {
                case onetime:
                    if(formatDatetime.format(currentDate).equals(formatDatetime.format(task.getDatetime()))){
                        System.out.println("onetime OK");
                    }
                    break;
                case daily:
                    if(formatTime.format(currentDate).equals(formatTime.format(task.getDatetime()))){
                        System.out.println("daily");
                    }
                    break;
                case monthly:
                    System.out.println("cur " + formatDayOfWeek.format(currentDate));
                    System.out.println("day " + task.getDayInMonth());
                    //System.out.println("day eq " + formatDayOfWeek.format(currentDate) == (task.getDayInMonth());
                    if(formatTime.format(currentDate).equals(formatTime.format(task.getDatetime())) && (Integer.parseInt(formatDayOfWeek.format(currentDate)) == task.getDayInMonth())){
                        System.out.println("monthly");
                    }
                    break;
            }
        }
    }
}
