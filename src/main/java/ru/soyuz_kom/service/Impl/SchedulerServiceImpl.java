package ru.soyuz_kom.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.soyuz_kom.entity.*;
import ru.soyuz_kom.repository.TaskRepository;
import ru.soyuz_kom.service.SchedulerService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.util.Calendar.MONTH;

@Service
@Transactional
public class SchedulerServiceImpl implements SchedulerService {

    public static final SimpleDateFormat FORMAT_DATETIME = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    public static final SimpleDateFormat FORMAT_TIME = new SimpleDateFormat("HH:mm");
    public static final SimpleDateFormat FORMAT_DAY_OF_MONTH = new SimpleDateFormat("dd");

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ClientServiceImpl clientService;


    public void checkTasks() throws ParseException {
        List<Task> tasks = taskRepository.findAll();
        Date curDate = new Date();
        System.out.println("curDate: " + curDate);

        for(Task task: tasks) {

            switch (task.getTypeWriteOff()) {
                case onetime:
                    if(FORMAT_DATETIME.format(curDate).equals(FORMAT_DATETIME.format(task.getDatetime()))){
                        buildServices(task);
                    }
                    break;
                case daily:
                    if(FORMAT_TIME.format(curDate).equals(FORMAT_TIME.format(task.getDatetime()))){
                        buildServices(task);
                    }
                    break;
                case monthly:
                    //System.out.println("monthly");
                    if(FORMAT_TIME.format(curDate).equals(FORMAT_TIME.format(task.getDatetime())) && isCheckDate(curDate, task.getDayInMonth())){
                        //System.out.println("monthly task");
                        buildServices(task);
                    }
                    break;
            }
        }
    }

    public void buildServices(Task task) {
        this.checkInternetTasks(task);
        this.checkTvTasks(task);
        this.checkRentTasks(task);
    }

    public void checkInternetTasks(Task task) {
        System.out.println("checkInternetTasks: " + task);
        if(task.getInternets().size() != 0) {
            for(Internet internet: task.getInternets()) {
                makeServices(task, internet.getClients());
            }
        }
    }

    public void checkTvTasks(Task task) {
        if(task.getTvs().size() != 0) {
            for(Tv tv: task.getTvs()) {
                makeServices(task, tv.getClients());
            }
        }
    }

    public void checkRentTasks(Task task) {
        if(task.getRents().size() != 0) {
            for(Rent rent: task.getRents()) {
                makeServices(task, rent.getClients());
            }
        }
    }

    private void makeServices(Task task, Set<Client> clients) {
        Date curDate = new Date();
        System.out.println("makeServices curDate: " + curDate);
        System.out.println("makeServices task: " + task);
        System.out.println("makeServices clients: " + clients);
        if(clients.size() != 0) {
            for(Client client: clients) {
                boolean isActiveTime = isCheckActiveTime(curDate, client, task);
                System.out.println("Date: " + curDate);
                System.out.println("isActiveTime: " + isActiveTime);
                if(isActiveTime){
                    clientService.addCash(client, task.getPrice());
                }
            }
        }
    }

    public boolean isCheckActiveTime(Date currentDate, Client client, Task task) {
        Integer diffMonth = diffMonth(currentDate, client.getCreatedAt());
        if(checkOrConvertToIntMin(task.getDayStart()) <= currentDate.getDate() && currentDate.getDate() <= checkOrConvertToIntMax(task.getDayEnd()) &&
                checkOrConvertToIntMin(task.getMonthStart()) <= diffMonth && diffMonth <= checkOrConvertToIntMax(task.getMonthEnd())
        ) {
            return true;
        } else {
            return false;
        }
    }

    public static Integer checkOrConvertToIntMin(Integer data) {
        return data == null ? 1 : data;
    }

    public static Integer checkOrConvertToIntMax(Integer data) {
        return data == null ? 99 : data;
    }

    /**
     * Разница дат в месяцах
     * @param currentDate
     * @param clientCreatedDate
     * @return
     */
    public Integer diffMonth(Date currentDate, Date clientCreatedDate) {
        Calendar curDate = getCalendar(currentDate);
        Calendar clientCreated = getCalendar(clientCreatedDate);

        return (curDate.get(MONTH) - clientCreated.get(MONTH))+1;
    }

    public static Calendar getCalendar(Date date) {
        Calendar cal = Calendar.getInstance(Locale.getDefault());
        cal.setTime(date);
        return cal;
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
        //Calendar c = Calendar.getInstance();
        //c.setTime(convertedDate);
        Calendar c = getCalendar(convertedDate);

        return c.getMaximum(Calendar.DAY_OF_MONTH);
    }
}
