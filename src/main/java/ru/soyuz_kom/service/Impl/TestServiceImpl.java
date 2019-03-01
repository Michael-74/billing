package ru.soyuz_kom.service.Impl;

import org.springframework.stereotype.Service;
import ru.soyuz_kom.repository.SmsRepository;

@Service
public class TestServiceImpl {

    SmsRepository smsRepository;

    public TestServiceImpl(SmsRepository smsRepository) {
        this.smsRepository = smsRepository;
    }

    public void getSys() {
        System.out.println("all Sms: " + smsRepository.count());
    }
}
