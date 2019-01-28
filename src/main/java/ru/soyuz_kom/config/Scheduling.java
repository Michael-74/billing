package ru.soyuz_kom.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
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
@EnableScheduling
public class Scheduling {

    @Autowired
    SchedulerServiceImpl schedulerService;

    @Scheduled(fixedRate = 1000)
    public void scheduler() throws InterruptedException, ParseException {

        schedulerService.checkTasks();

        /*
        Date curDate = new Date();

        String target = "2019-01-27";
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date date = df.parse(target);



        Calendar cur = getCalendar(curDate);
        Calendar clientCreated = getCalendar(date);

        //Integer diffMonth = (cur.get(MONTH) - clientCreated.get(MONTH))+1;
        Integer diffMonth = (curDate.getMonth() - date.getMonth())+1;

        System.out.println("diffMonth: " + diffMonth);

        //System.out.println("date a: " + a.get(MONTH));
        //System.out.println("date b: " + b.get(MONTH));
        */
    }

    public static Calendar getCalendar(Date date) {
        Calendar cal = Calendar.getInstance(Locale.getDefault());
        cal.setTime(date);
        return cal;
    }
}
