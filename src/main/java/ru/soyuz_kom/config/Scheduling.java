package ru.soyuz_kom.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.soyuz_kom.entity.Task;
import ru.soyuz_kom.repository.TaskRepository;
import ru.soyuz_kom.service.Impl.SchedulerServiceImpl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static java.util.Calendar.MONTH;

@Configuration
@Component
public class Scheduling {

    @Autowired
    SchedulerServiceImpl schedulerService;

    @Scheduled(cron = "0 */1 * * * *")
    public void scheduler() throws ParseException {
        schedulerService.checkTasks();
    }

    public static Calendar getCalendar(Date date) {
        Calendar cal = Calendar.getInstance(Locale.getDefault());
        cal.setTime(date);
        return cal;
    }
}
