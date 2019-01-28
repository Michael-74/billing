package ru.soyuz_kom.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.soyuz_kom.entity.*;
import ru.soyuz_kom.repository.TaskRepository;
import ru.soyuz_kom.service.SchedulerService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class SchedulerServiceImpl implements SchedulerService {

    public static final SimpleDateFormat FORMAT_DATETIME = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    public static final SimpleDateFormat FORMAT_TIME = new SimpleDateFormat("HH:mm");
    public static final SimpleDateFormat FORMAT_DAY_OF_MONTH = new SimpleDateFormat("dd");

    @Autowired
    private TaskRepository taskRepository;


    public void checkTasks() throws ParseException {
        List<Task> tasks = taskRepository.findAll();

        Date currentDate = new Date();

        for(Task task:  tasks) {



            switch (task.getTypeWriteOff()) {
                case onetime:
                    if(FORMAT_DATETIME.format(currentDate).equals(FORMAT_DATETIME.format(task.getDatetime()))){
                        System.out.println("onetime");
                    }
                    break;
                case daily:
                    if(FORMAT_TIME.format(currentDate).equals(FORMAT_TIME.format(task.getDatetime()))){
                        System.out.println("daily");
                    }
                    break;
                case monthly:
                    if(FORMAT_TIME.format(currentDate).equals(FORMAT_TIME.format(task.getDatetime())) && isCheckDate(currentDate, task.getDayInMonth())){
                        System.out.println("monthly");

                        this.checkInternetTasks(task);
                        this.checkTvTasks(task);
                        this.checkRentTasks(task);
                    }
                    break;
            }
        }
    }

    public void checkInternetTasks(Task task) {
        if(task.getInternets().size() != 0) {
            System.out.println("getInternets");
            for(Internet internet: task.getInternets()) {
                System.out.println("Internet: " + internet);
                if(internet.getClients().size() != 0) {
                    System.out.println("getClients");
                    for(Client client: internet.getClients()) {
                        System.out.println("client = " + client.getLogin());
                    }
                }
            }
        }
    }

    public void checkTvTasks(Task task) {
        if(task.getTvs().size() != 0) {
            System.out.println("getTvs");
            for(Tv tv: task.getTvs()) {
                System.out.println("TV: " + tv);
                if(tv.getClients().size() != 0) {
                    System.out.println("getClients");
                    for(Client client: tv.getClients()) {
                        System.out.println("client = " + client.getLogin());
                    }
                }
            }
        }
    }

    public void checkRentTasks(Task task) {
        if(task.getRents().size() != 0) {
            System.out.println("getRents");
            for(Rent rent: task.getRents()) {
                System.out.println("Rent: " + rent);
                if(rent.getClients().size() != 0) {
                    System.out.println("getClients");
                    for(Client client: rent.getClients()) {
                        System.out.println("client = " + client.getLogin());
                    }
                }
            }
        }
    }

    /**
     * Сравниваем даты
     * @param currentDate - дата и время
     * @param day - день месяца, без учета календаря (к примеру 31 может выпасть на февраль)
     * @return boolean
     * @throws ParseException
     */
    private boolean isCheckDate(Date currentDate, Integer day) throws ParseException {

        Integer lastDayOfMonth = lastDayOfMonth(currentDate); // Последний день месяца
        Integer dayOfMonth = Integer.parseInt(FORMAT_DAY_OF_MONTH.format(currentDate)); // День в месяце по календарю

        return (lastDayOfMonth.equals(dayOfMonth) && (lastDayOfMonth < day)) || dayOfMonth.equals(day);
    }

    /**
     * Получаем последний день месяца по календарю
     * @param currentDate - текущая дата
     * @return Integer
     * @throws ParseException
     */
    private Integer lastDayOfMonth(Date currentDate) throws ParseException {

        String date = FORMAT_DATETIME.format(currentDate);
        Date convertedDate = FORMAT_DATETIME.parse(date);
        Calendar c = Calendar.getInstance();
        c.setTime(convertedDate);

        return c.getMaximum(Calendar.DAY_OF_MONTH);
    }
}
