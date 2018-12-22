package ru.soyuz_kom.service;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;


public class TestService {

    @Scheduled(fixedRate = 1000)
    public void test() {
        System.out.println("Test");
    }
}
