package ru.soyuz_kom.service;

import java.text.ParseException;
import java.util.Map;

public interface SchedulerService {

    void checkTasks() throws ParseException;
}
